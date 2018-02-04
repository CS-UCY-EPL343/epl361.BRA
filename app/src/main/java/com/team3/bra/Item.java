package com.team3.bra;

import java.util.Vector;

/**
 * Created by GamerMakrides on 01/02/2018.
 */

public class Item {
    private int id;
    private String name;
    private float price;
    private String descreption;
    private String category;

    public Item(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.name = (String) vec.get(1);
        this.price = Float.parseFloat(vec.get(2).toString());
        this.descreption = (String)vec.get(3);
        this.category=(String) vec.get(4).toString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescreption() {
        return descreption;
    }
    public String getCategoryName() {
        return category;
    }
}
