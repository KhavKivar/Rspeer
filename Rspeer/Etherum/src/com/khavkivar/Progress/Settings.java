package com.khavkivar.Progress;

import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.event.listeners.AnimationListener;
import org.rspeer.runetek.event.types.AnimationEvent;
import org.rspeer.ui.Log;

public class Settings implements AnimationListener,AutoCloseable {
    private static StopWatch timer;
    public static int CountCastBracelet = 0;
    public static String getTimer() {
        return timer.toElapsedString();
    }
    public static void startTimer() {
        timer = StopWatch.start();
    }

    public static int getCountCastBracelet() {
        return CountCastBracelet;
    }
    public static void setCountCastBracelet(int countCastBracelet) {
        CountCastBracelet = countCastBracelet;
    }

    public Settings()
    {
        Game.getEventDispatcher().register(this);
    }

    @Override
    public void close() {
        Game.getEventDispatcher().deregister(this);
    }

    @Override
    public void notify(AnimationEvent animationEvent) {
        if(animationEvent.getCurrent() == 713){

        }
    }
}
