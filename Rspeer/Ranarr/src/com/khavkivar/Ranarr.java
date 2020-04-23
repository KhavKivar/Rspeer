package com.khavkivar;

import com.khavkivar.impl.*;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "KhavKivar",
        name ="RanarrK",
        desc = "Make ranarr unf potion",
        category = ScriptCategory.MONEY_MAKING,
        version = 0.01)

public class Ranarr extends TaskScript {

    public static int moneyCount = 0;
    public static int CountVial;
    public static int CountRanarr;
    public static int toBuy = 0;
    public static int countRanarrUnf = -1;



    public static final int VIAL_ID = 227;
    public static final int RANARR_ID = 257;
    public static final int RANARR_UNF_ID = 99;
    public static final int MONEY_ID = 995;

    public static final int RANARR_UNF_NOTED_ID = 100;



    private static final Task[] TASKS ={
            new MakePotion(),
            new Banking(),
            new Withdraw(),
            new RepairBug(),
            new BuyingGe(),
            new SellingOnGE()
    };
    @Override
    public void onStart() {
        Log.fine("Start");
        CountVial = -1;
        CountRanarr = -1;

        submit(TASKS);
    }
}
