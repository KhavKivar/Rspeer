package com.khavkivar.impl;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import static com.khavkivar.Ranarr.RANARR_ID;
import static com.khavkivar.Ranarr.VIAL_ID;

public class RepairBug extends Task {
    @Override
    public boolean validate() {
        return Bank.isOpen() && Inventory.containsOnly(x->x.getId() == VIAL_ID ||
                    x.getId() == RANARR_ID);
    }

    @Override
    public int execute() {
        Log.fine("Repair");
        Bank.close();
        Time.sleepUntil(Bank::isClosed,100,1500);
        return Random.high(400,500);
    }
}
