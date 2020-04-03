package com.khavkivar.advance;

import javafx.animation.Animation;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.event.listeners.AnimationListener;
import org.rspeer.runetek.event.listeners.SkillListener;
import org.rspeer.runetek.event.types.AnimationEvent;
import org.rspeer.runetek.event.types.SkillEvent;
import org.rspeer.ui.Log;

public class Settings  implements AnimationListener,AutoCloseable {
    private static int dur;
    private static StopWatch timer;


    public static int CountCastBracelet = 0;


    public static String getTimer() {
        return timer.toElapsedString();
    }
    public static void startTimer() {
        timer = StopWatch.start();
    }
    public static int getDur() {
        return dur;
    }

    public static int getCountCastBracelet() {
        return CountCastBracelet;
    }


    public static void setCountCastBracelet(int countCastBracelet) {
        CountCastBracelet = countCastBracelet;
    }



    public Settings() {
        Game.getEventDispatcher().register(this);
    }


    @Override
    public void close(){
        Game.getEventDispatcher().deregister(this);
    }

    @Override
    public void notify(AnimationEvent animationEvent) {
        if(animationEvent.getCurrent() == 713){
            setCountCastBracelet(getCountCastBracelet()+1);
        }

    }
}
