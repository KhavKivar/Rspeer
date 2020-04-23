package com.khavkivar.impl;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.GrandExchangeSetup;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;
import org.rspeer.ui.Log;

public class GeLogic {
    public static boolean Buy(int itemID,int offer) {
        Log.fine("P:"+itemID+"  offer:"+offer);
        if (GrandExchange.createOffer(RSGrandExchangeOffer.Type.BUY)) {
            Time.sleepUntil(GrandExchangeSetup::isOpen, 500, 2500);
        }
        if(GrandExchangeSetup.isOpen()) {
            GrandExchangeSetup.setItem(itemID);
            Time.sleep(Random.high(700, 900));
            GrandExchangeSetup.increasePrice(1);
            Time.sleep(Random.high(700, 900));
            GrandExchangeSetup.setQuantity(offer);
            Time.sleep(Random.high(700, 900));
            Time.sleepUntil(()->GrandExchangeSetup.getQuantity() == offer,100,2000);
            if(GrandExchangeSetup.getQuantity() != offer){
                return false;
            }
            GrandExchangeSetup.confirm();
            Time.sleepUntil(()->GrandExchange.getOffers(x->x.getItemId()==itemID).length>0,100,1500);
            if(GrandExchange.getOffers(x->x.getItemId()==itemID).length==0){
                return false;
            }
            return true;
        }
        return false;
}
}