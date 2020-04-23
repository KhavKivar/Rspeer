package com.khavkivar.utils;


public enum items {
    BRACELETETHERUM(21817,28,"Bracelet of ethereum (uncharged)"),
    REVENANTETHER(21820,29,"Revenant ether"),
    NATURERUNE(561,28,"Nature rune"),
    BRACELETCHARGED(21816,0,"Bracelet of ethereum");



    private final int idItem;
    private final int putItem;
    private final String name;
    items(int idItem, int putItem,String name) {
        this.idItem = idItem;
        this.putItem = putItem;
        this.name = name;
    }

    public String getName() { return name; }

    public int getIdItem() {
        return idItem;
    }
    public int getPutItem() {
        return putItem;
    }
}
