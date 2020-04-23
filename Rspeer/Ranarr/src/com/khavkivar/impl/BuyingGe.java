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
import static com.khavkivar.impl.SellingOnGE.rannarUnfOnProgess;

public class BuyingGe extends Task {
    private static final int ranarrvalue = 10000;

    @Override
    public boolean validate() {
        return Inventory.containsOnly(MONEY_ID)
                && Bank.isClosed()
                && checkAvailable()
                && !Inventory.isEmpty()
                && !rannarUnfOnProgess()
                ;
    }

    @Override
    public int execute() {
        Log.fine("Open Ge");
        if(!GrandExchange.isOpen()){
            GrandExchange.open();
            Time.sleepUntil(GrandExchange::isOpen,100,1500);
            if(!GrandExchange.isOpen()){
                return 300;
            }
        }
        ranarrToBuy();
        int totalToBuy = toBuy;
        int vialTobuy = totalToBuy - CountVial;
        int ranarrTobuy = totalToBuy - CountRanarr;
        Log.fine("Buying Vials");
        if(vialTobuy>0){
            if(Gelogic.Buy(VIAL_ID,vialTobuy,2)){
                Log.fine("OK");
                CountVial = totalToBuy;
            }
        }
        Log.fine("Buying Ranarr");
        if(ranarrTobuy>0){
            if(Gelogic.Buy(RANARR_ID,ranarrTobuy,1)){
                Log.fine("OK");
                CountRanarr = totalToBuy;
            }
        }
        if (CheckCollect() && FullStacks()) {
            Log.fine("Collecting");
            Time.sleepUntil(() -> !GrandExchangeSetup.isOpen(), 200, 1000);
            //Here
            GrandExchange.collectAll(true);
            Time.sleepUntil(()->GrandExchange.getOffers().length == 0,200,2000);
            Time.sleep(Random.high(400, 600));
            BankLocation bankLocation = BankLocation.GRAND_EXCHANGE;
            Bank.open(bankLocation);
            Time.sleepUntil(Bank::isOpen, 200, 1500);
            Bank.depositInventory();
            Time.sleepUntil(Inventory::isEmpty,200,2000);
        }
        return Random.high(400,500);
    }
    private boolean FullStacks() {
        Log.fine("Ranar on fullstack "+CountRanarr);
        if (CountRanarr >= toBuy && CountVial >= toBuy)
            return true;
        return false;
    }


    private void ranarrToBuy(){
        long moneyMax = Math.round(moneyCount*0.9);
        int aux = (Math.toIntExact(moneyMax))/ranarrvalue;
        int resto = aux % 14;
        toBuy =  (aux - resto);

    }
    private boolean CheckCollect(){
        RSGrandExchangeOffer[] rsGrandExchangeOffers = GrandExchange.getOffers(
                x-> x.getItemId() == VIAL_ID ||
                        x.getItemId() == RANARR_ID );
        for (RSGrandExchangeOffer rsGrandExchangeOffer:rsGrandExchangeOffers){
            if(!rsGrandExchangeOffer.getProgress().equals(RSGrandExchangeOffer.Progress.FINISHED)){
                Log.fine(rsGrandExchangeOffer.getItemName());
                return false;
            }
        }
        return true;
    }



    private boolean checkAvailable(){
        if((CountRanarr ==0 || CountVial ==0)||GrandExchange.isOpen()) return true;
        return false;
    }
}
