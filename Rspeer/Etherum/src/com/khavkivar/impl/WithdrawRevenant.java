package com.khavkivar.impl;

import com.khavkivar.utils.items;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.GameAccount;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WithdrawRevenant extends Task {
    @Override
    public boolean validate() {
        return CheckIfOpenBank();
    }

    @Override
    public int execute() {
        Log.fine("Trying Open  Bank");
        if(Bank.isOpen() && !Inventory.isFull()){
            Log.fine("Withdraw revenent Ether");
            Bank.withdraw(items.REVENANTETHER.getIdItem(),1);
            Time.sleepUntil(()->Inventory.getCount(items.REVENANTETHER.getIdItem())>=1,150,3000 );
            if(Inventory.getCount(items.REVENANTETHER.getIdItem())<1){
                return 400;
            }
            Time.sleep(Random.high(600,800));
            Bank.close();
            Log.fine("Close Bank");
            Time.sleepUntil(Bank::isClosed,200,4000);
            Log.fine(Time.getDefaultThreshold());
            return Random.high(700,800);

        }
        else{
            Log.fine("Open Bank");
            if(Inventory.getCount(items.REVENANTETHER.getIdItem()) >=1){
                Bank.close();
                Time.sleepUntil(Bank::isClosed,200,4000);

            }
            else{
                BankLocation bankLocation = BankLocation.GRAND_EXCHANGE;
                Bank.open(bankLocation);
            }

        }
        return Random.high(700,800);
    }


    private boolean CheckIfOpenBank() {
        return Inventory.contains(items.NATURERUNE.getIdItem()) && Inventory.contains(items.BRACELETETHERUM.getIdItem())
                && !Inventory.contains(items.REVENANTETHER.getIdItem()) && !GrandExchange.isOpen()
                && !Inventory.isFull();
    }
}
