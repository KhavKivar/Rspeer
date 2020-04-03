package com.khavkivar.impl;

import com.khavkivar.Etherum;
import com.khavkivar.advance.Settings;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Enchanted extends Task {
    private static final int spellposition = 2;

    private static final Spell.Modern alchSpells = Spell.Modern.HIGH_LEVEL_ALCHEMY;
    @Override
    public boolean validate() {
        return !Bank.isOpen() && CheckToAlch();
    }


    static int base = 800;
    static int incremento = 30;

    @Override
    public int execute() {

        if(Magic.canCast(alchSpells)){
            Log.fine("Cast Alchemy");
            Boolean spellx = Magic.interact(alchSpells,"Cast");
            Time.sleepUntil(()->spellx,Random.high(400,500),1500);
            if(spellx) {
                Item item = Inventory.getFirst(Transform.braceletCharged);
                int total = Math.abs(spellposition - Etherum.getNivel(item.getIndex()));
                Time.sleep(Random.high(600, base + incremento * total));
                if(!item.interact("Cast")) return -1;
                return Random.high(700, 900);
            }

        }
        else{
            if(Tabs.open(Tab.MAGIC)){
                Time.sleepUntil(()->Tabs.isOpen(Tab.MAGIC),600,1000);
                if(!Magic.canCast(alchSpells)){
                    Log.fine("insufficent runes");
                    return -1;
                }
            }
        }
        return Random.high(700,850);
    }

    private boolean CheckToAlch(){
        if(Inventory.contains(Transform.rune) &&
        Inventory.contains(Transform.braceletCharged) &&
                !Inventory.contains(Transform.bracelectEtherum)
                && !Inventory.contains(Transform.revenentEther)
        )return true;

        return false;
    }

}
