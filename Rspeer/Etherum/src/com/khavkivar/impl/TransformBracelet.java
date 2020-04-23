package com.khavkivar.impl;

import com.khavkivar.utils.items;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class TransformBracelet extends Task {
    @Override
    public boolean validate() {
        return CheckTransform();
    }

    @Override
    public int execute() {
        Log.fine("Transform Bracelet");
        Item revenantEther = Inventory.getFirst(items.REVENANTETHER.getIdItem());
        Item bracelectEther = Inventory.getFirst(items.BRACELETETHERUM.getIdItem());
        if(revenantEther != null && bracelectEther !=null) {
            if (!Tabs.isOpen(Tab.INVENTORY)) {
                Tabs.open(Tab.INVENTORY);
                Time.sleepUntil(() -> Tabs.isOpen(Tab.INVENTORY), Random.high(400, 500), 3000);
                return Random.high(400, 500);
            }
            Log.fine("Interact with Revenant Ether");
            Boolean validEther = revenantEther.interact("Use");
            Time.sleepUntil(()->Inventory.isItemSelected(),Random.high(500,600),1500);
            if(validEther) {
                Boolean validBracelect = bracelectEther.interact("Use");
                Time.sleepUntil(() -> Inventory.isItemSelected(), Random.high(400, 500), 1500);
                return Random.high(300, 400);
            }
        }
        return -1;
    }

    public boolean CheckTransform(){
        return Inventory.contains(items.REVENANTETHER.getIdItem()) && Inventory.contains(items.BRACELETETHERUM.getIdItem())
                && Inventory.contains(items.NATURERUNE.getIdItem()) && !GrandExchange.isOpen() && Bank.isClosed();
    }

}
