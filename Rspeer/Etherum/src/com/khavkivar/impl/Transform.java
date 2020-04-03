package com.khavkivar.impl;

import com.khavkivar.Etherum;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class Transform extends Task {

    public static final String rune = "Nature rune";
    public static final int bracelectEtherum = 21817;
    public static final int coins = 995;
    public static final int revenentEther = 21820;
    public static final int braceletCharged = 21816;

    @Override
    public boolean validate() {
        return !Bank.isOpen() && CheckTransform();
    }

    @Override
    public int execute() {
        Log.fine("Transform bracelect");
        Item revenantEther = Inventory.getFirst(revenentEther);
        Item bracelectEther = Inventory.getFirst(bracelectEtherum);
        if(revenantEther != null && bracelectEther !=null) {
            if (!Tabs.isOpen(Tab.INVENTORY)) {
                Tabs.open(Tab.INVENTORY);
                Time.sleepUntil(() -> Tabs.isOpen(Tab.INVENTORY), Random.high(400, 500), 3000);
                return Random.high(400, 500);
            }
            Log.fine("Interact with eter");
            Boolean validEther = revenantEther.interact("Use");
            Time.sleepUntil(()->Inventory.isItemSelected(),Random.high(500,600),1000);
            if(validEther) {
                Boolean validBracelect = bracelectEther.interact("Use");
                Time.sleepUntil(() -> Inventory.isItemSelected(), Random.high(400, 500), 1000);
                if (!validBracelect) return -1;
                return Random.high(300, 400);
            }
        }
        return -1;
    }
    public boolean CheckTransform(){
        if(Inventory.contains(revenentEther) &&
                Inventory.contains(bracelectEtherum) &&
                Inventory.contains(rune)
        )return true;

        return false;
    }
}
