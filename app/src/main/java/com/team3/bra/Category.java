package com.team3.bra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by GamerMakrides on 01/02/2018.
 */

public class Category implements Comparable<Category> {
    protected static ArrayList<Category> categories= new ArrayList<>();

    private int id;
    private String name;
    private float vat;
    private String description;
    private boolean food;
    private ArrayList<Item> items = new ArrayList<Item>();

    public Category(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.name = (String) vec.get(1);
        this.vat = Float.parseFloat(vec.get(2).toString());
        this.description = (String) vec.get(3);
        this.food =(boolean) vec.get(4);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public float getVat() {
        return vat;
    }

    public String getDescription() {
        return description;
    }

    public static void findCategories(){
            categories=new ArrayList<Category>();
            String a[] = {"0"};
            Vector<Vector<Object>> vec = JDBC.callProcedure("FindCategory", a);
            for (int i = 0; i < vec.size(); i++) {
                Category c = new Category(vec.get(i));
                categories.add(c);
            }
    }

    public void fillCategory(){
            this.items=new ArrayList<Item>();
            String a[] = {"-1", this.getId() + ""};
            Vector<Vector<Object>> vec = JDBC.callProcedure("FindItem", a);
            for (int i = 0; i < vec.size(); i++) {
                this.items.add(new Item(vec.get(i)));
            }
    }

    public int isFood() {
        if(food ==true)
            return 1;
        return 0;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int compareTo(@NonNull Category category) {
        return this.name.compareTo(category.name);
    }
}
