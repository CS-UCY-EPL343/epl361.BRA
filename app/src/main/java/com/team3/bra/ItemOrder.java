package com.team3.bra;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by GamerMakrides on 04/02/2018.
 */

public class ItemOrder {
    private int itemID;
    private String name;
    private String description;
    private int quantity;
    private boolean done;
    private String notes;

    public ItemOrder(Vector<Object> vec) {
        this.itemID = (int) vec.get(0);
        this.name=(String) vec.get(1).toString();
        this.description=(String) vec.get(2).toString();
        this.quantity = (int) vec.get(3);
        this.done = (boolean) vec.get(4);
        this.notes = (String) vec.get(5).toString();
    }

    public int getItemID() {
        return itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isDone() {
        return done;
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
}