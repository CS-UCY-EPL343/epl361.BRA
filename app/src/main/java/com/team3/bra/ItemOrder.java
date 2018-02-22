package com.team3.bra;

import java.io.Serializable;
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

    public ItemOrder(Item item,int quantity,String notes) {
        this.itemOrderID=-1;
        this.itemID = item.getId();
        this.name=item.getName();
        this.description=item.getDescreption();
        this.quantity = quantity;
        this.notes = notes;
    }

    public ItemOrder(Vector<Object> vec) {
        this.itemID = (int) vec.get(0);
        this.name=(String) vec.get(1).toString();
        this.description=(String) vec.get(2).toString();
        this.quantity = (int) vec.get(3);
        this.done = (boolean) vec.get(4);
        this.notes = (String) vec.get(5).toString();
        this.itemOrderID=(int) vec.get(6);
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
}