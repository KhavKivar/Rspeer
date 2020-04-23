package com.khavkivar.impl;

import com.khavkivar.model.ItemOffer;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;
import org.rspeer.script.task.Task;

import java.util.Stack;

import static com.khavkivar.RannarSmart.ITEM_PRICES_ID;
import static com.khavkivar.RannarSmart.VIAL_ID;

public class Buying extends Task {
    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public int execute() {
        if(!GrandExchange.isOpen()){
            GrandExchange.open();
            Time.sleepUntil(GrandExchange::isOpen,100,2000);
        }

        Stack<ItemOffer> stackItems = new Stack<>();
        ItemOffer itemOffer = new ItemOffer(
                VIAL_ID,1,RSGrandExchangeOffer.Type.BUY,(ITEM_PRICES_ID.get(String.valueOf(VIAL_ID)).getBuy_average()+1));
        stackItems.push(itemOffer);
        Gelogic.BuySmart(stackItems);


        return Random.high(1300,1500);
    }
}
