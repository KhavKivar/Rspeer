package com.khavkivar.impl;


import com.khavkivar.impl.VarUtils;
import com.khavkivar.utils.items;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;



public class WithdrawBracelet extends Task {

    public  static final int reFinal = 21820;
    public static final int braFinal = 21817;
    public static final int naFinal = 561;
    public static final int coFinal = 995;




    @Override
    public boolean validate() {
        return !GrandExchange.isOpen() && withoutBracelet() && itemZero();
    }

    @Override
    public int execute() {
        if(Bank.isOpen()){
            Log.fine("Open Bank To withdraw");
            //Init Values
            VarUtils.setBracelectStacks(Bank.getCount(items.BRACELETETHERUM.getIdItem()));
            VarUtils.setRuneStacks(Bank.getCount(items.NATURERUNE.getIdItem()));
            VarUtils.setRevenantStacks(Bank.getCount(items.REVENANTETHER.getIdItem()));
            VarUtils.setCoinsStacks(Bank.getCount(coFinal));

            Item[] itemsInit  = Inventory.getItems(
                    x->x.getId()== items.BRACELETETHERUM.getIdItem()||
                            x.getId()==items.REVENANTETHER.getIdItem()||
                            x.getId() == items.NATURERUNE.getIdItem());
            if(itemsInit.length>0) {
                for (Item item : itemsInit) {
                    switch (item.getId()) {
                        case reFinal:
                            VarUtils.setRevenantStacks(VarUtils.getRevenantStacks() + 1);
                            break;
                        case braFinal:
                            VarUtils.setBracelectStacks(VarUtils.getBracelectStacks() + 1);
                            break;
                        case naFinal:
                            VarUtils.setRuneStacks(VarUtils.getRuneStacks() + item.getStackSize());
                            break;
                        case coFinal:
                            VarUtils.setCoinsStacks(VarUtils.getCoinsStacks() + item.getStackSize());
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + item.getId());
                    }
                }
            }

            Log.fine("Nature Rune "+VarUtils.getRuneStacks());
            Log.fine("Bracelet  "+VarUtils.getBracelectStacks());
            Log.fine("Coins "+VarUtils.getCoinsStacks());
            Log.fine("Revenant Ether "+VarUtils.getRevenantStacks());

            //Check if items are greater than zero
            if( VarUtils.getBracelectStacks()  == 0 || VarUtils.getRevenantStacks() == 0 || VarUtils.getRuneStacks() == 0) {
                if(Bank.contains(coFinal)){
                    Bank.withdrawAll(coFinal);
                    Time.sleepUntil(()->Inventory.getCount(coFinal)>=1,100,2000);
                }
                Log.fine("Close Bank and Open GE");
                Bank.close();
                Time.sleepUntil(Bank::isClosed,400,2500);
                return Random.high(500,600);
            }

            Time.sleep(Random.high(800,900));
            if(!Inventory.contains(naFinal)){
                Log.fine("Withdraw Nature rune");
                if(Bank.contains(naFinal)){
                    Bank.withdrawAll(naFinal);
                    Time.sleepUntil(()->Inventory.getCount(naFinal)>=1,200,3000);
                }
            }
            Bank.withdraw(reFinal,1);
            Time.sleepUntil(()->Inventory.getCount(reFinal)>=1,200,3000);
            Bank.withdrawAll(braFinal);
            Time.sleepUntil(()->Inventory.getCount(braFinal)>=1,200,3000);
            Log.fine("Close Bank Bracelet");
            Bank.close();
            Time.sleepUntil(Bank::isClosed,200,3000);
            Log.fine(Time.getDefaultThreshold());
            return Random.high(700,900);
        }
        else{
            BankLocation bankLocation = BankLocation.GRAND_EXCHANGE;
            Bank.open(bankLocation);
        }
        return Random.high(700,900);
    }

    private boolean withoutBracelet(){
        return !Inventory.contains(items.BRACELETCHARGED.getIdItem()) && !Inventory.contains(items.BRACELETETHERUM.getIdItem());
    }
    private boolean itemZero(){
        return (VarUtils.getBracelectStacks() > 0 && VarUtils.getRevenantStacks() > 0 && VarUtils.getRuneStacks() > 0) ||
                (VarUtils.getRuneStacks() == -1 && VarUtils.getRevenantStacks() == -1 && VarUtils.getBracelectStacks() == -1);
    }

}
