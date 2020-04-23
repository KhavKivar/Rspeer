package com.khavkivar.impl;

import com.khavkivar.Etherum;
import com.khavkivar.utils.items;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.GrandExchangeSetup;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class OpenAndBuyOnGe extends Task {

    @Override
    public boolean validate() {
        return itemZero() && Bank.isClosed();
    }

    @Override
    public int execute() {
        if(GrandExchange.isOpen()) {

            int buyBraceletEtherum = items.BRACELETETHERUM.getPutItem() - VarUtils.getBracelectStacks();
            int buyNatureRune = items.NATURERUNE.getPutItem() - VarUtils.getRuneStacks();
            int buyRevenant = items.REVENANTETHER.getPutItem() - VarUtils.getRevenantStacks();

            Log.fine("buy bracelet:  " + buyBraceletEtherum);
            Log.fine("buy nature rune:   " + buyBraceletEtherum);
            Log.fine("buy revenant: " + buyBraceletEtherum);

            if (buyBraceletEtherum > 0 && !CheckOfferStatus(items.BRACELETETHERUM.getIdItem())) {
                Log.fine("Buying Bracelet Etherum");
                //Check offer

                if (GeLogic.Buy(items.BRACELETETHERUM.getIdItem(), buyBraceletEtherum)) {
                    Log.fine("OK");
                    VarUtils.setBracelectStacks(items.BRACELETETHERUM.getPutItem());
                    Time.sleep(800,900);

                }
            }
            if (buyNatureRune > 0 && !CheckOfferStatus(items.NATURERUNE.getIdItem())) {
                Log.fine("Buying Nature Rune");
                if (GeLogic.Buy(items.NATURERUNE.getIdItem(), buyNatureRune)) {
                    Log.fine("OK");
                    VarUtils.setRuneStacks(items.NATURERUNE.getIdItem());
                    Time.sleep(800,900);
                }

            }
            if (buyRevenant > 0 && !CheckOfferStatus(items.REVENANTETHER.getIdItem())) {
                Log.fine("Buying Revenant");
                if (GeLogic.Buy(items.REVENANTETHER.getIdItem(), buyRevenant)) {
                    Log.fine("OK");
                    VarUtils.setRevenantStacks(items.REVENANTETHER.getIdItem());
                    Time.sleep(800,900);
                }
            }
            if (CheckCollect() && FullStacks()) {
                Log.fine("Collecting");
                Time.sleepUntil(() -> !GrandExchangeSetup.isOpen(), 500, 1000);
                GrandExchange.collectAll(true);
                Time.sleepUntil(()->GrandExchange.getOffers().length == 0,200,2000);
                Time.sleep(Random.high(400, 600));
                BankLocation bankLocation = BankLocation.GRAND_EXCHANGE;
                Bank.open(bankLocation);
                Time.sleepUntil(Bank::isOpen, 500, 1500);
            }
        }
        else{
            GrandExchange.open();
            Time.sleepUntil(GrandExchange::isOpen,500,1300);
        }
        return Random.high(500,600);
    }
    private boolean FullStacks(){
        if( (VarUtils.getBracelectStacks() >= items.BRACELETETHERUM.getPutItem()  &&
                VarUtils.getRevenantStacks() >= items.REVENANTETHER.getPutItem()
                && VarUtils.getRuneStacks()  >= items.NATURERUNE.getPutItem()))
            return true;
        return false;
    }


    private boolean itemZero(){
        return (VarUtils.getRuneStacks() == 0 || VarUtils.getRevenantStacks() == 0 || VarUtils.getBracelectStacks() == 0) ||
                GrandExchange.isOpen();
    }

    private boolean CheckOfferStatus(int itemID){
        RSGrandExchangeOffer[] rsGrandExchangeOffer = GrandExchange.getOffers(x-> x.getItemId()==itemID);
        if(rsGrandExchangeOffer.length > 0){
            return true;
        }
        return false;
    }

    private boolean CheckCollect(){
        RSGrandExchangeOffer[] rsGrandExchangeOffers = GrandExchange.getOffers(
                x-> x.getItemId() == items.BRACELETETHERUM.getIdItem() ||
                        x.getItemId() == items.NATURERUNE.getIdItem()||
                        x.getItemId() == items.REVENANTETHER.getIdItem() );

        for (RSGrandExchangeOffer rsGrandExchangeOffer:rsGrandExchangeOffers){
            if(!rsGrandExchangeOffer.getProgress().equals(RSGrandExchangeOffer.Progress.FINISHED)){
                Log.fine(rsGrandExchangeOffer.getItemName());
                return false;
            }
        }
        return true;
    }


}
