package com.khavkivar.impl;

import com.khavkivar.utils.items;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import static com.khavkivar.impl.WithdrawBracelet.coFinal;

public class Repair extends Task {

    @Override
    public boolean validate() {
        return Inventory.containsOnly(x->x.getId()== items.NATURERUNE.getIdItem()
                        || x.getId()==items.BRACELETETHERUM.getIdItem() ||
                x.getId() == coFinal
                 ) && Inventory.isFull()
                ;
    }

    @Override
    public int execute() {
        Log.fine("REPAIR ERROR");
        if(Bank.isOpen()) {
            //Deposit 1 Bracelet and Widrath Revenant Ether
            Bank.deposit(items.BRACELETETHERUM.getIdItem(),1);
            Time.sleepUntil(()->!Inventory.isFull(),200,3000);
            Bank.withdraw(items.REVENANTETHER.getIdItem(),1);
            Time.sleepUntil(()->Inventory.getCount(items.REVENANTETHER.getIdItem())>=1,200,3000);
            Bank.close();
            Time.sleepUntil(Bank::isClosed,200,3000);
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
                Time.sleepUntil(Bank::isOpen,200,1500);
            }

        }
        return Random.high(600,700);
    }
}
