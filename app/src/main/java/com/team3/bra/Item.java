package com.team3.bra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by GamerMakrides on 01/02/2018.
 */

public class Item implements Comparable<Item> {
    private int id;
    private String name;
    private float price;
    private String description;
    private int categoryID;
    public static ArrayList<Item> items;

    public static void fillAllItems(){
        items = new ArrayList<Item>();
        String a[] = {"0", "0"};
        Vector<Vector<Object>> vec = JDBC.callProcedure("FindItem", a);
        for (int i = 0; i < vec.size(); i++) {
            items.add(new Item(vec.get(i)));
        }
    }
    public Item(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.name = (String) vec.get(1);
        this.price = Float.parseFloat(vec.get(2).toString());
        this.description = (String)vec.get(3);
        this.categoryID=(int) vec.get(4);
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

    public String getDescription() {
        return description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    @Override
    public int compareTo(@NonNull Item item) {
        return this.name.compareTo(item.name);
    }
}
