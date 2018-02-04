package com.team3.bra;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by GamerMakrides on 04/02/2018.
 */

public class Order {
    protected static ArrayList<Order> orders=new ArrayList<Order>();

    private int id;
    private String dateTime;
    private int state;
    private int table;
    private int userID;
    private ArrayList<ItemOrder> items;

    public Order(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        dateTime = (String) vec.get(1).toString();
        this.state = (int) vec.get(2);
        this.table =(int) vec.get(3);
        this.userID =(int) vec.get(4);
        items=new ArrayList<ItemOrder>();
    }

    public int getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getState() {
        return state;
    }

    public int getTable() {
        return table;
    }

    public int getUserID() {
        return userID;
    }

    public static void findOrders(){
        if(orders.isEmpty()) {
            orders = new ArrayList<Order>();
            String a[] = {"0"};
            Vector<Vector<Object>> vec = JDBC.callProcedure("FindOrder", a);
            for (int i = 0; i < vec.size(); i++) {
                Order o = new Order(vec.get(i));
                orders.add(o);
            }
        }
    }

    public void fillOrder(){
            String a[] = {this.getId() + ""};
            Vector<Vector<Object>> vec = JDBC.callProcedure("FindItemOrder", a);
            for (int i = 0; i < vec.size(); i++) {
                this.items.add(new ItemOrder(vec.get(i)));
            }
    }
}
