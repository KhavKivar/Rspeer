package com.khavkivar;

import com.khavkivar.advance.Settings;
import com.khavkivar.impl.*;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;

import static com.khavkivar.advance.Settings.CountCastBracelet;


@ScriptMeta(developer = "KhavKivar",
        name ="EtherumK",
        desc = "Etherum Enchanted")


public class Etherum extends TaskScript implements RenderListener {

    public static HashMap<Integer, Integer> diff = new HashMap<Integer, Integer>();
    Settings settings;


    public static int runeStacks=-1;
    public static int bracelectStacks=-1;
    public static int revenantStacks=-1;
    public static int coinsStacks=-1;


    private static final Task[] TASKS={
            new Transform(),
            new OpenBankEther(),
            new Enchanted(),
            new OpenBankBracelet(),
            new granExchangeBuying()
    };

    public static void setRuneStacks(int runeStacks) {
        Etherum.runeStacks = runeStacks;
    }

    public static void setBracelectStacks(int bracelectStacks) {
        Etherum.bracelectStacks = bracelectStacks;
    }

    public static void setRevenantStacks(int revenantStacks) {
        Etherum.revenantStacks = revenantStacks;
    }

    public static void setCoinsStacks(int coinsStacks) {
        Etherum.coinsStacks = coinsStacks;
    }

    public static int getRuneStacks() {
        return runeStacks;
    }

    public static int getBracelectStacks() {
        return bracelectStacks;
    }

    public static int getRevenantStacks() {
        return revenantStacks;
    }

    public static int getCoinsStacks() {
        return coinsStacks;
    }

    @Override
    public void onStart() {

          settings= new Settings();
        Settings.startTimer();


        int base = 650;
        int add = 50;
        diff.put(0,base);
        diff.put(1,base+add);
        diff.put(2,base+add*2);
        diff.put(3,base+add*3);
        diff.put(4,base+add*4);
        diff.put(5,base+add*5);
        diff.put(6,base+add*6);
        Log.fine("Iniciando");
        submit(TASKS);
    }


    public static int getNivel(int position){
        return  (int) Math.floor(position/4.0);
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
        g.drawString("Ganancia: "+ Settings.getCountCastBracelet()*1371 ,column2X, row3Y);

        g.setFont(new Font("Arial",Font.PLAIN, 10));
        g.drawString("Version 0.01",column1X, 327);

        g.setFont(new Font("Monospaced",Font.PLAIN, 22));
        g.drawString("Etherum V.01",column1X, row1Y);


        /*
        if (Settings.getAFKTimer() != null) {
            g.setFont(new Font("Arial",Font.PLAIN, 18));
            g.drawString("Afk time: "+Settings.getAFKTimer().toRemainingString(),column1X,row2Y);
        }
        g.setFont(new Font("Arial",Font.PLAIN, 16));

        g.drawString("Runtime: "+Settings.getTimer(),column2X, row1Y);

        DecimalFormat formatter = new DecimalFormat("#,###");
        if (Settings.getExp() > 1000) {
            String formattedExp = formatter.format(Settings.getExp());
            g.drawString("WoodCutting: "+formattedExp+" xp ("+formatter.format((int)Settings.getHourly())+"/h +"+Settings.getLvlUp()+")",column2X, row2Y);
        } else {
            g.drawString("WoodCutting: "+Settings.getExp()+" xp ("+formatter.format((int)Settings.getHourly())+"/h +"+ Settings.getLvlUp()+")",column2X, row2Y);
        }

        g.setFont(new Font("Arial",Font.PLAIN, 10));
        g.drawString("Version 0.01",column1X, 327);

        g.setFont(new Font("Monospaced",Font.PLAIN, 22));
        g.drawString("WoodCut V.01",column1X, row1Y);
        *

         */
    }
}
