package com.khavkivar.impl;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import static com.khavkivar.RannarSmart.*;
import static java.lang.Integer.min;

public class MakePotion extends Task {
    @Override
    public boolean validate() {
        return Bank.isClosed() &&
                !GrandExchange.isOpen() &&
                isStackEmpty() &&
                Inventory.contains(VIAL_ID) &&
                Inventory.contains(RANARR_ID) &&
                !Inventory.isEmpty();
    }

    @Override
    public int execute() {
        if(!Tabs.isOpen(Tab.INVENTORY) || Inventory.isItemSelected()){
            Tabs.open(Tab.INVENTORY);
            Time.sleepUntil(()-> Tabs.isOpen(Tab.INVENTORY),100,2000);
            if(!Tabs.isOpen(Tab.INVENTORY)) return Random.high(400,600);
            if(Inventory.isItemSelected()){
                Inventory.deselectItem();
                Time.sleepUntil(()->!Inventory.isItemSelected(),100,2000);
                if(Inventory.isItemSelected()) return Random.high(400,600);
            }
        }
        Item[] vialItems = Inventory.getItems(x->x.getId()==VIAL_ID);
        Item[] ranarrItems = Inventory.getItems(x->x.getId()==RANARR_ID);
        if(vialItems == null || ranarrItems == null) return Random.high(400,600);
        int minUnfPotion = min(Inventory.getCount(VIAL_ID),Inventory.getCount(RANARR_ID));
        Item vialItem = vialItems[Random.high(0,vialItems.length-1)];
        Item ranarrItem = ranarrItems[Random.low(0,ranarrItems.length-1)];
        if(vialItem == null || ranarrItem==null) return Random.high(400,600);
        vialItem.interact("Use");
        Time.sleepUntil(Inventory::isItemSelected,200,2000);
        if(!Inventory.isItemSelected()) return Random.high(400,600);
        ranarrItem.interact("Use");
        Time.sleepUntil(Production::isOpen,100,2000);
        if(Production.isOpen()){
            Log.fine("Making Ranarr unf potion");
            Production.initiate(Production.Amount.ALL);
            Time.sleepWhile(()->Inventory.getCount(RANARR_UNF_ID) != minUnfPotion,100
                    ,15000);
        }
        return Random.high(400,600);
    }
}
