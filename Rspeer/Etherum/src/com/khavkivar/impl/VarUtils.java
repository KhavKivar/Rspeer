package com.khavkivar.impl;

public abstract class VarUtils {
    public static int runeStacks;
    public static int bracelectStacks;
    public static int revenantStacks;
    public static int coinsStacks;

    public static int getRuneStacks() {
        return runeStacks;
    }

    public static void setRuneStacks(int runeStacks) {
        VarUtils.runeStacks = runeStacks;
    }

    public static int getBracelectStacks() {
        return bracelectStacks;
    }

    public static void setBracelectStacks(int bracelectStacks) {
        VarUtils.bracelectStacks = bracelectStacks;
    }

    public static int getRevenantStacks() {
        return revenantStacks;
    }

    public static void setRevenantStacks(int revenantStacks) {
        VarUtils.revenantStacks = revenantStacks;
    }

    public static int getCoinsStacks() {
        return coinsStacks;
    }

    public static void setCoinsStacks(int coinsStacks) {
        VarUtils.coinsStacks = coinsStacks;
    }

    public static int getNivel(int position){
        return  (int) Math.floor(position/4.0);
    }
}
