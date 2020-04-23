package com.khavkivar.impl;

import com.khavkivar.Etherum;
import com.khavkivar.Progress.Settings;
import com.khavkivar.utils.items;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.event.listeners.AnimationListener;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class AlchBracelet extends Task {
    private static final int spellposition = 2;
    private static final Spell.Modern alchSpells = Spell.Modern.HIGH_LEVEL_ALCHEMY;
    static int base = 800;
    static int incremento = 30;

    @Override
    public boolean validate() {
        return CheckToAlch();
    }

    @Override
    public int execute() {
        if(Magic.canCast(alchSpells)){
            Log.fine("Cast Alchemy");
            Boolean spellx = Magic.interact(alchSpells,"Cast");
            Time.sleepUntil(()->spellx, Random.high(400,500),1500);
            if(spellx) {
                int braceletInventory = Inventory.getCount(items.BRACELETCHARGED.getIdItem());
                Item item = Inventory.getFirst(items.BRACELETCHARGED.getIdItem());
                int total = Math.abs(spellposition - VarUtils.getNivel(item.getIndex()));
                Time.sleep(Random.high(600, base + incremento * total));
                if(item.interact("Cast")) {
                    if (braceletInventory == Inventory.getCount(items.BRACELETCHARGED.getIdItem()))
                        Settings.setCountCastBracelet(Settings.getCountCastBracelet() + 1);
                }
                return Random.high(700, 900);
            }

        }
        else{
            if(Tabs.open(Tab.MAGIC)){
                Time.sleepUntil(()->Tabs.isOpen(Tab.MAGIC),600,1000);
                if(!Magic.canCast(alchSpells)){
                    Log.fine("Insufficent Rune");
                    return -1;
                }
            }
        }
        return Random.high(700,850);

    }


    private boolean CheckToAlch(){
        return Inventory.contains(items.NATURERUNE.getIdItem()) &&
                Inventory.contains(items.BRACELETCHARGED.getIdItem()) &&
                !Inventory.contains(items.BRACELETETHERUM.getIdItem()) &&
                !Inventory.contains(items.REVENANTETHER.getIdItem()) && Bank.isClosed() && !GrandExchange.isOpen();
    }
}
