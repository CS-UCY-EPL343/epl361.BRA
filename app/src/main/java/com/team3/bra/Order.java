package com.team3.bra;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 * This class is for an object representing an Order.
 *
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private static ArrayList<Order> orders = new ArrayList<Order>();
    private static ArrayList<Order> notificationOrders = new ArrayList<Order>();
    private static ArrayList<Order> newNotificationOrders = new ArrayList<Order>();
    private static ArrayList<Order> cookOrders = new ArrayList<Order>();

    private int id;
    private String dateTime;
    private int state;
    private int table;
    private int userID;
    private ArrayList<ItemOrder> items;

    /**
     * A constructor of the Category object. It gives the given tableID to the
     * order, and also sets its date to the current date and time, its id and
     * state to -1 and the user that created the order.
     *
     * @param tableID
     *            the tableID to set the order to.
     */
    public Order(int tableID) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.dateTime = formatter.format(now);
        this.id = -1;
        this.state = -1;
        this.table = tableID;
        this.userID = User.currentUser.getId();
        this.items = new ArrayList<ItemOrder>();
    }

    /**
     * A constructor of the Order object. It reads a vector that was returned
     * from JDBC in order to construct the object as it is on the database.
     *
     * @param vec
     *            a vector that is returned from JDBC
     */
    public Order(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.dateTime = (String) vec.get(1).toString();
        this.state = (int) vec.get(2);
        this.table = (int) vec.get(3);
        this.userID = (int) vec.get(4);
        this.items = new ArrayList<ItemOrder>();
    }

    /**
     * This function uses a JDBC call to fill the list of the Waiter's Normal
     * orders.
     */
    public static void findActiveOrders() {
        orders = new ArrayList<Order>();
        String a[] = {};
        Vector<Vector<Object>> vec = JDBC.callProcedure("activeOrders", a);
        for (int i = 0; i < vec.size(); i++) {
            Order o = new Order(vec.get(i));
            orders.add(o);
        }
    }

    /**
     * This function uses a JDBC call to fill the list of the Waiter's
     * Notification Orders. It is responsible to find new notification orders
     * and check if the existing ones still exist.
     */
    public static void findNotificationOrders(ArrayList<Order> waiterNot) {
        ArrayList<Order> oldNotificationOrders = new ArrayList<Order>();
        oldNotificationOrders.addAll(notificationOrders);

        newNotificationOrders = new ArrayList<Order>();
        notificationOrders = new ArrayList<Order>();

        String a[] = {};
        Vector<Vector<Object>> vec = JDBC.callProcedure("notifications", a);

        // Find new notifications
        for (int i = 0; i < vec.size(); i++) {
            Order o = new Order(vec.get(i));
            notificationOrders.add(o);
            boolean old = false;
            for (int j = 0; j < oldNotificationOrders.size(); j++)
                if (oldNotificationOrders.get(j).id == o.id)
                    old = true;
            if (old == false)
                newNotificationOrders.add(o);
        }
        // Find old notifications that no longer exist.
        for (int j = 0; j < oldNotificationOrders.size(); j++) {
            boolean flag = false;
            for (int i = 0; i < notificationOrders.size(); i++) {
                if (oldNotificationOrders.get(j).getId() == notificationOrders.get(i).getId())
                    flag = true;
            }
            if (flag == false) {
                Waiter.cancelNotification(oldNotificationOrders.get(j));
                waiterNot.remove(oldNotificationOrders.get(j));
            }
        }
    }

    /**
     * This function uses a JDBC call to find the list of the cook Orders.
     */
    public static void findCookOrders() {
        cookOrders = new ArrayList<Order>();
        String a[] = {};
        Vector<Vector<Object>> vec = JDBC.callProcedure("cookView", a);
        for (int i = 0; i < vec.size(); i++) {
            Order o = new Order(vec.get(i));
            cookOrders.add(o);
        }
    }

    /**
     * Fills the current order with its ItemOrders as set in the DB.
     */
    public void fillOrder() {
        this.items = new ArrayList<ItemOrder>();
        String a[] = { this.getId() + "" };
        Vector<Vector<Object>> vec = JDBC.callProcedure("FindItemOrder", a);
        for (int i = 0; i < vec.size(); i++) {
            this.items.add(new ItemOrder(vec.get(i)));
        }
    }

    /**
     * Sets the state of an order. It is responsible for updating the database
     * using a JDBC call.
     *
     * @param state
     */
    public void setState(int state) {
        this.state = state;
        String a[] = { this.id + "", this.state + "" };
        Vector<Vector<Object>> vec = JDBC.callProcedure("EditOrderState", a);

    }

    /**
     * Function that sets the id of an order. It only works when the current id
     * is -1.
     *
     * @param id
     */
    public void setId(int id) {
        if (this.id == -1)
            this.id = id;
    }

    /**
     * A function that clears the notificationOrders list.
     */
    public static void clearNotifications() {
        notificationOrders = new ArrayList<Order>();
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

    public ArrayList<ItemOrder> getItems() {
        return items;
    }

    public static ArrayList<Order> getCookOrders() {
        return cookOrders;
    }

    public static ArrayList<Order> getNotificationOrders() {
        return notificationOrders;
    }

    public static ArrayList<Order> getNewNotificationOrders() {
        return newNotificationOrders;
    }

    public static ArrayList<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        if (this.table != -1)
            return "Table: " + this.table;
        else
            return "<New Order>";
    }
}
