package com.khavkivar.utils;

public enum items {
    BRACELETETHERUM(21817,28),
    REVENANTETHER(21820,28),
    NATURERUNE(561,28);

    private int idItem;
    private int putItem;

    items(int idItem, int putItem) {
        this.idItem = idItem;
        this.putItem = putItem;
    }

    public int getIdItem() {
        return idItem;
    }

    public int getPutItem() {
        return putItem;
    }
}
