package com.khavkivar.impl;


import com.khavkivar.model.ItemOffer;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.GrandExchangeSetup;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;
import org.rspeer.ui.Log;

import java.util.Stack;
import java.util.concurrent.SynchronousQueue;

public abstract class Gelogic{

    public static boolean BuySmart(Stack<ItemOffer> stackItems){
        while(!stackItems.empty()) {
            if (Buy(stackItems.peek())) {
                stackItems.pop();
            }
        }
        return false;
    }



    public static boolean Buy(ItemOffer itemOffer) {

        Log.fine("P:"+itemOffer.getItemId()+"  offer:");
        if (GrandExchange.createOffer(RSGrandExchangeOffer.Type.BUY)) {
            Time.sleepUntil(GrandExchangeSetup::isOpen, 500, 2500);
        }
        if(GrandExchangeSetup.isOpen()) {
            GrandExchangeSetup.setItem(itemOffer.getItemId());
            Time.sleep(600,700);
            GrandExchangeSetup.setPrice(itemOffer.getOffer());
            Time.sleepUntil(()->GrandExchangeSetup.getPricePerItem()==itemOffer.getOffer(),100,200);
            Time.sleep(600,700);
            GrandExchangeSetup.confirm();
            return true;
        }
        return false;
    }
}
