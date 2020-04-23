package com.khavkivar.impl;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import static com.khavkivar.Ranarr.*;

public class MakePotion extends Task {

    @Override
    public boolean validate() {
        return Bank.isClosed() &&
                (Inventory.containsOnly(x->x.getId() == VIAL_ID ||
                        x.getId() == RANARR_ID) || Inventory.getCount(RANARR_UNF_ID) <= 13)
                && !Inventory.containsOnly(VIAL_ID)
                && !Inventory.containsOnly(RANARR_ID)
                && !Inventory.contains(995)
                && !Players.getLocal().isAnimating()


                ;
    }

    @Override
    public int execute() {
        if(!Tabs.isOpen(Tab.INVENTORY)){
            Tabs.open(Tab.INVENTORY);
            Time.sleepUntil(()->Tabs.isOpen(Tab.INVENTORY),100,1500);
        }

        Item[] items = Inventory.getItems(x->x.getId()==VIAL_ID);
        Item vialItem = items[Random.high(0,items.length -1)];
        if(vialItem == null) return -1;
        vialItem.interact("Use");
        Time.sleepUntil(()->Inventory.isItemSelected(), Random.high(300,400),1500);

        Item[] itemsy = Inventory.getItems(x->x.getId()==RANARR_ID);
        Item ranarrItem= itemsy[Random.low(0,itemsy.length -1)];


        if(ranarrItem == null) return -1;
        ranarrItem.interact("Use");
        Time.sleepUntil(()->Inventory.isItemSelected(), Random.high(100,200),1500);

        if(Production.isOpen()){
            Log.fine("Making Ranarr");
            Production.initiate(Production.Amount.ALL);
            if(!(Time.sleepUntil(()->Inventory.containsOnly(RANARR_UNF_ID),150,15000))){
                if(Players.getLocal().isAnimating()){
                    Time.sleepUntil(()->!Players.getLocal().isAnimating(),150,4000);
                }
            };
        }
        return Random.high(700,900);
    }
}
