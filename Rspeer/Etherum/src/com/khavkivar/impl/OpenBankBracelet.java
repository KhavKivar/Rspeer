package com.khavkivar.impl;

import com.khavkivar.Etherum;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class OpenBankBracelet extends Task {
    @Override
    public boolean validate() {
        return CheckIfOpenBank() && !ChefIfReopen() && !GrandExchange.isOpen();
    }

    @Override
    public int execute() {
        Log.fine("Open Bank withdraw bracelet");
        if(Bank.isOpen()){


            Etherum.setRuneStacks(Bank.getCount(Transform.rune) + Inventory.getCount(Transform.rune));
            Etherum.setBracelectStacks(Bank.getCount(Transform.bracelectEtherum) + Inventory.getCount(Transform.bracelectEtherum));
            Etherum.setRevenantStacks(Bank.getCount(Transform.revenentEther)+ Inventory.getCount(Transform.revenentEther));
            Etherum.setCoinsStacks( Bank.getCount(Transform.coins) + Inventory.getCount(Transform.coins));


            Log.fine("Rune  "+Etherum.getRuneStacks());
            Log.fine("Bracelet  "+Etherum.getBracelectStacks());
            Log.fine("coins  "+Etherum.getCoinsStacks());
            Log.fine("eter  "+Etherum.getRevenantStacks());
            if(Etherum.getRuneStacks() == 0 || Etherum.getBracelectStacks() == 0 || Etherum.getRevenantStacks() == 0){
                if(!Inventory.contains(Transform.coins)){
                    Bank.withdrawAll(Transform.coins);
                }

                Log.fine("CLOSE BANK AND OPEN GE");
                Bank.close();
                Time.sleepUntil(Bank::close,200,1500);
                return Random.high(500,600);
            }


            if(Inventory.isEmpty() || Inventory.containsOnly(Transform.coins)){
                Bank.withdrawAll(Transform.rune);
                return Random.high(700,800);
            }
            Bank.withdraw(Transform.revenentEther,1);
            Time.sleep(Random.high(800,1100));
            Bank.withdrawAll(Transform.bracelectEtherum);
            Time.sleep(Random.high(700,1000));
            Bank.close();
            Time.sleepUntil(()->!Bank.isOpen(),Random.low(500,800),3000);
        }
        else{
            BankLocation bankLocation = BankLocation.GRAND_EXCHANGE;
            Bank.open(bankLocation);
        }
        return Random.high(600,800);
    }
    private boolean CheckIfOpenBank(){
        if(Inventory.containsOnly(x->x.getName().equals(Transform.rune)||
                x.getId() == Transform.coins) || Inventory.isEmpty())return true;
        return false;
    }
    private boolean ChefIfReopen(){
        if(Etherum.getRuneStacks() == 0 || Etherum.getBracelectStacks() == 0 || Etherum.getRevenantStacks() == 0)return true;
        return false;
    }

}
