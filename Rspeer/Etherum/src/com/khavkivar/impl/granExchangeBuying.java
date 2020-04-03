package com.khavkivar.impl;

import com.khavkivar.Etherum;
import com.khavkivar.utils.items;
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

public class granExchangeBuying extends Task {
    @Override
    public boolean validate() {
        return valid() && Bank.isClosed();
    }

    @Override
    public int execute() {
        if(GrandExchange.isOpen()) {
            //Buy segun la cantidad que falta.
            int buyBraceletEtherum = items.BRACELETETHERUM.getPutItem() - Etherum.getBracelectStacks();
            int buyNatureRune = items.NATURERUNE.getPutItem() - Etherum.getRuneStacks();
            int buyRevenant = items.REVENANTETHER.getPutItem() - Etherum.getRevenantStacks();
            //primer check
            if(buyBraceletEtherum>0){
                Log.fine("Buying Bracelet Etherum");
                if(GrandExchange.createOffer(RSGrandExchangeOffer.Type.BUY)){
                    Time.sleepUntil(GrandExchangeSetup::isOpen,500,1500);
                }
                if(GrandExchangeSetup.isOpen()){
                    GrandExchangeSetup.setItem(items.BRACELETETHERUM.getIdItem());
                    Time.sleep(Random.high(600,750));
                    GrandExchangeSetup.setQuantity(buyBraceletEtherum);
                    Time.sleep(Random.high(600,750));
                    GrandExchangeSetup.increasePrice(1);
                    Time.sleep(Random.high(400,500));
                    GrandExchangeSetup.confirm();
                    Etherum.setBracelectStacks(items.BRACELETETHERUM.getPutItem());
                }
            }
            if(buyNatureRune>0){
                Log.fine("Buying Etherum" + Etherum.getRuneStacks() + "  "+ buyNatureRune);
                if (GrandExchange.createOffer(RSGrandExchangeOffer.Type.BUY))
                    Time.sleepUntil(GrandExchangeSetup::isOpen,500,1500);
                if(GrandExchangeSetup.isOpen()){
                    GrandExchangeSetup.setItem(items.NATURERUNE.getIdItem());
                    Time.sleep(Random.high(600,750));
                    GrandExchangeSetup.setQuantity(buyRevenant);
                    Time.sleep(Random.high(600,750));
                    GrandExchangeSetup.increasePrice(1);
                    Time.sleep(Random.high(400,500));
                    GrandExchangeSetup.confirm();
                    Etherum.setRuneStacks(items.NATURERUNE.getPutItem());
                }
            }
            if(buyRevenant>0){
                Log.fine("Buying Nature Rune");
                if (GrandExchange.createOffer(RSGrandExchangeOffer.Type.BUY))
                    Time.sleepUntil(GrandExchangeSetup::isOpen,500,1500);
                if(GrandExchangeSetup.isOpen()){
                    GrandExchangeSetup.setItem(items.REVENANTETHER.getIdItem());
                    Time.sleep(Random.high(600,750));
                    GrandExchangeSetup.setQuantity(buyRevenant);
                    Time.sleep(Random.high(600,750));
                    GrandExchangeSetup.increasePrice(1);
                    Time.sleep(Random.high(400,500));
                    GrandExchangeSetup.confirm();
                    Etherum.setRevenantStacks(items.REVENANTETHER.getPutItem());
                }
            }
            if(!valid()){

                Time.sleepUntil(()->!GrandExchangeSetup.isOpen(),500,1000);
                GrandExchange.collectAll();
                Time.sleep(300,400);
                BankLocation bankLocation = BankLocation.GRAND_EXCHANGE;
                    Bank.open(bankLocation);
                Time.sleepUntil(Bank::isOpen,500,1500);
                if(Bank.isOpen()){
                    Bank.depositInventory();
                    Time.sleep(Random.high(600,700));
                }
            }
            return Random.high(1000,1200);
        }
        else{
            GrandExchange.open();
            Time.sleepUntil(GrandExchange::isOpen,500,1300);
            return 200;
        }
    }


    public static boolean valid(){
        if(Etherum.getBracelectStacks() == 0 || Etherum.getRevenantStacks() == 0 || Etherum.getRuneStacks() == 0) return true;
        return false;
    }
}
