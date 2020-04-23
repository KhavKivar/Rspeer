package com.khavkivar.impl;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import static com.khavkivar.Ranarr.*;

public class Withdraw extends Task {


    @Override
    public boolean validate() {
        return (Inventory.isEmpty()
                || Inventory.containsOnly(VIAL_ID) || Inventory.containsOnly(RANARR_ID) || Inventory.containsOnly(MONEY_ID))
                    && !GrandExchange.isOpen()
                    && checkAvailable()
                ;
    }

    @Override
    public int execute() {
        Log.fine("Trying Open Bank");
        if(Bank.isOpen()){

            if(Inventory.contains(MONEY_ID)){
                Bank.depositInventory();
                Time.sleepUntil(()->Inventory.getCount(MONEY_ID)==0,100,1500);
                return 200;

            }
            Log.fine("Open Bank Successful");
            //Count Vial && Ranarr Available
            CountVial = Bank.getCount(VIAL_ID);
            CountRanarr = Bank.getCount(RANARR_ID);

            if(CountVial == 0 || CountRanarr == 0){
                Log.fine("Close Bank && Open GE");

                moneyCount = Bank.getCount(MONEY_ID);
                Bank.withdrawAll(MONEY_ID);
                Time.sleepUntil(() -> Inventory.contains(MONEY_ID),100,2000);
                if(Bank.contains(RANARR_UNF_ID)){
                    Bank.setWithdrawMode(Bank.WithdrawMode.NOTE);
                    Time.sleepUntil(()->Bank.getWithdrawMode() == Bank.WithdrawMode.NOTE,100,2000);
                    Time.sleep(Random.high(200,400));
                    int countUnf = Bank.getCount(RANARR_UNF_ID);
                    Bank.withdrawAll(RANARR_UNF_ID);
                    if(Time.sleepUntil(()->Bank.getCount(RANARR_UNF_ID)==0,100,1500)) {
                        countRanarrUnf = countUnf;
                    }

                    Bank.setWithdrawMode(Bank.WithdrawMode.ITEM);
                    Time.sleepUntil(()->Bank.getWithdrawMode() == Bank.WithdrawMode.ITEM,100,2000);
                    Time.sleep(Random.high(200,300));

                }
                Bank.close();
                Time.sleepUntil(Bank::isClosed,100,1500);
                return Random.high(300,500);
            }
            Log.fine("Withdraw Vial && Ranarr ");


            if(!Inventory.contains(VIAL_ID)) {
                Bank.withdraw(VIAL_ID, 14);
                Time.sleepUntil(() -> Inventory.getCount(VIAL_ID) == 14, 100, 2000);
            }
            if(!Inventory.contains(RANARR_ID)) {
                Bank.withdraw(RANARR_ID, 14);
                Time.sleepUntil(() -> Inventory.getCount(RANARR_ID) == 14, 100, 2000);
            }
            Bank.close();
            Time.sleepUntil(Bank::isClosed,100,1500);
        }
        else {
            Log.fine("Open Bank");
            BankLocation bankLocation = BankLocation.GRAND_EXCHANGE;
            Bank.open(bankLocation);
            Time.sleepUntil(Bank::isOpen, 100, 1500);
        }
        return Random.high(500,600);
    }


    private boolean checkAvailable(){
        if((CountRanarr > 0 && CountVial >0) || (CountRanarr==-1 && CountVial == -1)) return true;
        return false;

    }
}
