package com.khavkivar.impl;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;

import static com.khavkivar.Ranarr.RANARR_UNF_ID;

public class Banking extends Task {


    @Override
    public boolean validate() {
        return Inventory.containsOnly(RANARR_UNF_ID) && !Inventory.isEmpty() && !GrandExchange.isOpen();
    }

    @Override
    public int execute() {
        if(Bank.isOpen()){
            Bank.depositAll(RANARR_UNF_ID);
            Time.sleepUntil(Inventory::isEmpty,100,1500);
        }
        else{
            BankLocation bankLocation= BankLocation.GRAND_EXCHANGE;
            Bank.open(bankLocation);
            Time.sleepUntil(Bank::isOpen,100,1500);
        }
        return Random.high(500,600);
    }
}
