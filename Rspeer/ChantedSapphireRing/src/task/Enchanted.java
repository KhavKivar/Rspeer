package task;

import org.rspeer.runetek.adapter.Interactable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Magic;
import org.rspeer.runetek.api.component.tab.Spell;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Enchanted extends Task {
    private static final int idRecoils = 2550;
    private static final int idSapphire = 1637;
    private static final Spell.Modern sapphireSpells = Spell.Modern.LEVEL_1_ENCHANT;

    @Override
    public boolean validate() {
        return !Bank.isOpen() && isfinish();
    }

    @Override
    public int execute() {
        if(Magic.canCast(sapphireSpells)){
            Log.fine("CAST SAPPHIRE");
            Magic.interact(sapphireSpells,"Cast");
            Time.sleep(Random.high(400,600));
            Inventory.getFirst("Sapphire Ring").interact("Cast");
            Time.sleep(Random.high(400,600));
        }
        else{
            Log.fine("insufficent runes");
        }
        return Random.high(600,850);
    }

    private boolean isfinish(){
        if(Inventory.getCount(2550) != 27)return true;
        return false;
    }
}
