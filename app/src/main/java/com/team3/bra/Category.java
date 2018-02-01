package com.team3.bra;

import java.util.Vector;

/**
 * Created by GamerMakrides on 01/02/2018.
 */

public class Category {
    private int id;
    private String name;
    private float vat;
    private String descreption;

    public Category(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.name = (String) vec.get(1);
        this.vat = (float) vec.get(2);
        this.descreption = (String) vec.get(3);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getVat() {
        return vat;
    }

    public String getDescreption() {
        return descreption;
    }
}
