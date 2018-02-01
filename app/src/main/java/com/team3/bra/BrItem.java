package com.team3.bra;

import java.util.Vector;

/**
 * Created by GamerMakrides on 01/02/2018.
 */

public class BrItem {
    private int id;
    private String name;
    private float price;
    private String descreption;
    private int catID;

    public BrItem(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.name = (String) vec.get(1);
        this.price = Float.parseFloat(vec.get(2).toString());
        this.descreption = (String)vec.get(3);
        this.catID=(int) vec.get(4);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getVat() {
        return price;
    }

    public String getDescreption() {
        return descreption;
    }
    public String getCatID() {
        return descreption;
    }
}
