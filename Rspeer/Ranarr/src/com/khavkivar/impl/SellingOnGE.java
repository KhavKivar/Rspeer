package com.khavkivar.impl;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.GrandExchangeSetup;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;



import static com.khavkivar.Ranarr.*;
import static com.khavkivar.Ranarr.RANARR_ID;

public class SellingOnGE extends Task {
    @Override
    public boolean validate() {
        boolean bol = false;
        if(Inventory.getFirst(RANARR_UNF_NOTED_ID) != null){
            if(Inventory.getFirst(RANARR_UNF_NOTED_ID).isNoted()){
                bol = true;
            }
        }
        return Inventory.containsOnly(x->x.getId()== MONEY_ID||
                        x.getId() == RANARR_UNF_NOTED_ID)
                && bol
                && Bank.isClosed()
                && !Inventory.isEmpty()
                ;
    }

    @Override
    public int execute() {
        Log.fine("Trying Selling ON GE");
        if(!GrandExchange.isOpen()){
            GrandExchange.open();
            Time.sleepUntil(GrandExchange::isOpen,100,1500);
            if(!GrandExchange.isOpen()){
                return 300;
            }
        }

        int countUndefined = Inventory.getFirst(RANARR_UNF_NOTED_ID).getStackSize();
        if(GrandExchange.createOffer(RSGrandExchangeOffer.Type.SELL)) {
            Time.sleepUntil(GrandExchangeSetup::isOpen, 100, 2500);
        }
        if(GrandExchangeSetup.isOpen() && !rannarUnfOnProgess()){
            Log.fine("Selling Ranarr UNF");
            GrandExchangeSetup.setItem(RANARR_UNF_NOTED_ID);
            Time.sleep(Random.high(700, 900));
            //GrandExchangeSetup.setQuantity(countUndefined);

           // Time.sleep(Random.high(700, 900));
            GrandExchangeSetup.decreasePrice(1);
            //  Time.sleepUntil(()->GrandExchangeSetup.getQuantity() == countUndefined,100,2000);
            GrandExchangeSetup.confirm();
            Time.sleepUntil(()->GrandExchange.getOffers(x->x.getItemId()== RANARR_UNF_ID).length>0,100,1500);
            Time.sleepUntil(()->
                            GrandExchange.getFirst(x->x.getItemId() == RANARR_UNF_ID).getProgress().equals(RSGrandExchangeOffer.Progress.FINISHED)
                    ,100,10000);
        }
        if (GrandExchange.getFirst(x->x.getItemId() == RANARR_UNF_ID).getProgress().equals(RSGrandExchangeOffer.Progress.FINISHED)) {
            Log.fine("Collecting");
            Time.sleepUntil(() -> !GrandExchangeSetup.isOpen(), 200, 1000);
            //Here
            GrandExchange.collectAll();
            Time.sleepUntil(()->Inventory.contains(MONEY_ID),150,2000);
            Time.sleep(Random.high(400, 600));
        }
        return Random.high(400,500);
    }
    public static boolean rannarUnfOnProgess(){
        if(GrandExchange.getOffers(x->x.getItemId()== RANARR_UNF_ID).length>0)return true;
        return false;
    }
}
