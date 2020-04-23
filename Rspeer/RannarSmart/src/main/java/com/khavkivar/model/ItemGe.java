package com.khavkivar.model;

public class ItemGe {

    String name;
    int id;
    String member;
    int buy_average;
    int sell_average;

    int overall_average;
    int overall_quantity;
    int sell_quantity;
    int buy_quantity;

    ItemGe(){ }

    public ItemGe(String name, int id, String member, int buy_average, int sell_average, int overall_average, int overall_quantity, int sell_quantity, int buy_quantity) {
        this.name = name;
        this.id = id;
        this.member = member;
        this.buy_average = buy_average;
        this.sell_average = sell_average;
        this.overall_average = overall_average;
        this.overall_quantity = overall_quantity;
        this.sell_quantity = sell_quantity;
        this.buy_quantity = buy_quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getBuy_average() {
        return buy_average;
    }

    public void setBuy_average(int buy_average) {
        this.buy_average = buy_average;
    }

    public int getSell_average() {
        return sell_average;
    }

    public void setSell_average(int sell_average) {
        this.sell_average = sell_average;
    }

    public int getOverall_average() {
        return overall_average;
    }

    public void setOverall_average(int overall_average) {
        this.overall_average = overall_average;
    }

    public int getOverall_quantity() {
        return overall_quantity;
    }

    public void setOverall_quantity(int overall_quantity) {
        this.overall_quantity = overall_quantity;
    }

    public int getSell_quantity() {
        return sell_quantity;
    }

    public void setSell_quantity(int sell_quantity) {
        this.sell_quantity = sell_quantity;
    }

    public int getBuy_quantity() {
        return buy_quantity;
    }

    public void setBuy_quantity(int buy_quantity) {
        this.buy_quantity = buy_quantity;
    }
}
