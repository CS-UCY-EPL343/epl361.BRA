package com.team3.bra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * This class is for an object representing an item Category.
 *
 */
public class Category implements Comparable<Category> {
    protected static ArrayList<Category> categories = new ArrayList<>();

    private int id;
    private String name;
    private float vat;
    private String description;
    private boolean food;
    private ArrayList<Item> items = new ArrayList<Item>();

    /**
     * The constructor of the Category object. It reads a vector that was
     * returned from JDBC in order to construct the object as it is on the
     * database.
     *
     * @param vec
     *            a vector that is returned from JDBC
     */
    public Category(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.name = (String) vec.get(1);
        this.vat = Float.parseFloat(vec.get(2).toString());
        this.description = (String) vec.get(3);
        this.food = (boolean) vec.get(4);
    }

    /**
     * Getter for the Category id.
     *
     * @return the Category id.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the Category name.
     *
     * @return the Category name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the list of items in the category.
     *
     * @return the list of items in the category.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Getter for the VAT of the Category.
     *
     * @return the VAT of the Category.
     */
    public float getVat() {
        return vat;
    }

    /**
     * Getter for the description of the Category.
     * @return the description of the Category.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Finds all the categories of the DB using a JDBC call and stores them in a
     * static arraylist.
     */
    public static void findCategories() {
        categories = new ArrayList<Category>();
        String a[] = { "0" };
        Vector<Vector<Object>> vec = JDBC.callProcedure("FindCategory", a);
        for (int i = 0; i < vec.size(); i++) {
            Category c = new Category(vec.get(i));
            categories.add(c);
        }
    }

    /**
     * Fills the current order with items using a JDBC call.
     */
    public void fillCategory() {
        this.items = new ArrayList<Item>();
        String a[] = { "-1", this.getId() + "" };
        Vector<Vector<Object>> vec = JDBC.callProcedure("FindItem", a);
        for (int i = 0; i < vec.size(); i++) {
            this.items.add(new Item(vec.get(i)));
        }
    }

    /**
     * Returns if the category is food.
     *
     * @return if the category is food.
     */
    public int isFood() {
        if (food == true)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(@NonNull Category category) {
        return this.name.compareTo(category.name);
    }
}
