package com.team3.bra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Vector;

/**
 * This class is for an object representing an Item.
 *
 */
public class Item implements Comparable<Item> {
    public static ArrayList<Item> items;

    private int id;
    private String name;
    private float price;
    private String description;
    private int categoryID;

    /**
     * The constructor of the Item object. It reads a vector that was returned
     * from JDBC in order to construct the object as it is on the database.
     *
     * @param vec
     *            a vector that is returned from JDBC
     */
    public Item(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.name = (String) vec.get(1);
        this.price = Float.parseFloat(vec.get(2).toString());
        this.description = (String) vec.get(3);
        this.categoryID = (int) vec.get(4);
    }

    /**
     * Fills an arraylist that holds all the items in the DB using a JDBC call.
     */
    public static void fillAllItems() {
        items = new ArrayList<Item>();
        String a[] = { "0", "0" };
        Vector<Vector<Object>> vec = JDBC.callProcedure("FindItem", a);
        for (int i = 0; i < vec.size(); i++) {
            items.add(new Item(vec.get(i)));
        }
    }

    /**
     * Getter for the Item id.
     *
     * @return the Item id.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the Item name.
     *
     * @return the Item name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the Item price.
     *
     * @return the Item price.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Getter for the Item description.
     *
     * @return the Item description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the Item category id.
     *
     * @return the Item category id.
     */
    public int getCategoryID() {
        return categoryID;
    }

    @Override
    public int compareTo(@NonNull Item item) {
        return this.name.compareTo(item.name);
    }
}
