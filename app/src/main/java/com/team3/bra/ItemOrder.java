package com.team3.bra;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by GamerMakrides on 04/02/2018.
 */

public class ItemOrder implements Serializable{
    private int itemID;
    private String name;
    private String description;
    private int quantity;
    private boolean done;
    private String notes;
    private int itemOrderID;
    private double currentPrice;
    private float vat;

    private String category;

    @Override
    public String toString() {
        return "ItemOrder{" +
                "itemID=" + itemID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", done=" + done +
                ", notes='" + notes + '\'' +
                ", itemOrderID=" + itemOrderID +
                ", currentPrice=" + currentPrice +
                ", vat=" + vat +
                '}';
    }

    public ItemOrder(Item item, int quantity, String notes) {
        this.itemOrderID=-1;
        this.itemID = item.getId();
        this.name=item.getName();
        this.description=item.getDescreption();
        this.quantity = quantity;
        this.notes = notes;
    }

    public ItemOrder(Vector<Object> vec) {
        this.itemID = (int) vec.get(0);
        this.name= vec.get(1).toString();
        this.description=vec.get(2).toString();
        this.quantity = (int) vec.get(3);
        this.done = (boolean) vec.get(4);
        this.notes = vec.get(5).toString();
        this.itemOrderID=(int) vec.get(6);
        this.currentPrice = Double.parseDouble(vec.get(7).toString());
    }

    public ItemOrder(Vector<Object> vec,boolean alter) {
        this.itemID = (int) vec.get(0);
        this.name= vec.get(1).toString();
        this.description= vec.get(2).toString();
        this.quantity = (int) vec.get(3);
        this.done = (boolean) vec.get(4);
        this.notes =  vec.get(5).toString();
        this.itemOrderID=(int) vec.get(6);
        this.currentPrice = Double.parseDouble(vec.get(7).toString());
        this.vat = Float.parseFloat(vec.get(8).toString());
        this.category =vec.get(9).toString();
    }


    public int getItemOrderID() {
        return itemOrderID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public int isDone() {
        if(done==true)return 1;
        return 0;
    }

    public String getNotes() {
        return notes;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public String getCategory() {
        return category;
    }

}