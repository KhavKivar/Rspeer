package com.khavkivar.model;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;

public class ItemOffer {
    private int itemId;
    private int offer;
    private RSGrandExchangeOffer.Type rsGranExchangedOfferType;
    private boolean increasePrice = false;
    private boolean decreasePrice = false;
    private int increaseTimes = 0;
    private int decreaseTimes = 0;
    private int priceOffer = -1;

    public ItemOffer(int itemId,int offer,RSGrandExchangeOffer.Type rsGranExchangedOfferType,int priceOffer){
        this.itemId = itemId;
        this.offer = offer;
        this.rsGranExchangedOfferType = rsGranExchangedOfferType;
        this.priceOffer = priceOffer;

    }

    public ItemOffer(int itemId, int offer, int priceOffer, boolean increasePrice, boolean decreasePrice, int increaseTimes, int decreaseTimes, RSGrandExchangeOffer.Type rsGranExchangedOfferType) {
        this.itemId = itemId;
        this.offer = offer;
        this.priceOffer = priceOffer;
        this.increasePrice = increasePrice;
        this.decreasePrice = decreasePrice;
        this.increaseTimes = increaseTimes;
        this.decreaseTimes = decreaseTimes;
        this.rsGranExchangedOfferType = rsGranExchangedOfferType;
    }



    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public int getPriceOffer() {
        return priceOffer;
    }

    public void setPriceOffer(int priceOffer) {
        this.priceOffer = priceOffer;
    }

    public boolean isIncreasePrice() {
        return increasePrice;
    }

    public void setIncreasePrice(boolean increasePrice) {
        this.increasePrice = increasePrice;
    }

    public boolean isDecreasePrice() {
        return decreasePrice;
    }

    public void setDecreasePrice(boolean decreasePrice) {
        this.decreasePrice = decreasePrice;
    }

    public int getIncreaseTimes() {
        return increaseTimes;
    }

    public void setIncreaseTimes(int increaseTimes) {
        this.increaseTimes = increaseTimes;
    }

    public int getDecreaseTimes() {
        return decreaseTimes;
    }

    public void setDecreaseTimes(int decreaseTimes) {
        this.decreaseTimes = decreaseTimes;
    }

    public RSGrandExchangeOffer.Type getRsGranExchangedOfferType() {
        return rsGranExchangedOfferType;
    }

    public void setRsGranExchangedOfferType(RSGrandExchangeOffer.Type rsGranExchangedOfferType) {
        this.rsGranExchangedOfferType = rsGranExchangedOfferType;
    }
}

