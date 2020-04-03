package com.khavkivar.impl;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class OpenBankEther extends Task {
    @Override
    public boolean validate() {
        return  CheckIfOpenBank();
    }

    @Override
    public int execute() {
        Log.fine("Trying Open  Bank");
        if(Bank.isOpen()){
            Log.fine("Open Bank");
            Bank.withdraw(Transform.revenentEther,1);
            Log.fine("Withdraw revenent Ether");
            Time.sleep(Random.high(800,1100));
            if(!Bank.close()) return Random.high(300,400);
            Log.fine("Close Bank");
            Time.sleepUntil(()->!Bank.isOpen(),Random.low(500,800),3000);
        }
        else{
            BankLocation bankLocation = BankLocation.GRAND_EXCHANGE;
            Bank.open(bankLocation);
        }
        return Random.high(600,800);
    }

    private boolean CheckIfOpenBank(){
       if(Inventory.contains(Transform.rune) &&
               Inventory.contains(Transform.bracelectEtherum)  && !Inventory.containsOnly(Transform.revenentEther)
       ) return true;

        return false;
    }
}
