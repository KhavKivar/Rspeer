package com.khavkivar;

import com.khavkivar.Progress.Settings;
import com.khavkivar.impl.*;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;
import java.text.DecimalFormat;


@ScriptMeta(developer = "KhavKivar",
        name ="EtherumVK2",
        desc = "Etherum Enchanted",
        category = ScriptCategory.MONEY_MAKING,
version = 0.01)

public class Etherum extends TaskScript implements RenderListener {


    private static final Task[] TASKS = {
            new OpenAndBuyOnGe(),
            new WithdrawBracelet(),
            new AlchBracelet(),
            new TransformBracelet(),
            new WithdrawRevenant(),
            new Repair()
    };
    private Settings handler;


    @Override
    public void onStart() {
        Log.fine("Start");
        com.khavkivar.impl.VarUtils.runeStacks = -1;
        com.khavkivar.impl.VarUtils.bracelectStacks = -1;
        com.khavkivar.impl.VarUtils.revenantStacks = -1;
        com.khavkivar.impl.VarUtils.coinsStacks = -1;
        handler = new Settings();
        Settings.startTimer();
        submit(TASKS);
    }

    @Override
    public void onStop() {
        handler.close();
        super.onStop();
    }

    @Override
    public void notify(RenderEvent renderEvent) {

        //x and y are destinations the size of a pixel on the canvas, canvas is 503x765
        Graphics g = renderEvent.getSource();
        int alpha = 140;
        Color myColour = new Color(0, 0, 0,alpha);
        g.setColor(myColour);
        g.fillRect(4,252,512,85);
        g.setColor(Color.ORANGE);
        g.drawRect(4,252,512,85);
        int row1Y = 282;
        int row2Y = 307;
        int column1X = 10;
        int column2X = 230;
        int row3Y= 330;

        g.setFont(new Font("Arial",Font.PLAIN, 16));
        g.setFont(new Font("Arial",Font.PLAIN, 16));

        g.drawString("Runtime: "+ Settings.getTimer(),column2X, row1Y);
        DecimalFormat formatter = new DecimalFormat("#,###");

        g.drawString("Cast Etherum: "+ Settings.getCountCastBracelet() ,column2X, row2Y);
        g.drawString("Profit: "+ Settings.getCountCastBracelet()*1100 ,column2X, row3Y);

        g.setFont(new Font("Arial",Font.PLAIN, 10));
        g.drawString("Version 0.01",column1X, 327);

        g.setFont(new Font("Monospaced",Font.PLAIN, 22));
        g.drawString("Etherum V.01",column1X, row1Y);


    }
}
