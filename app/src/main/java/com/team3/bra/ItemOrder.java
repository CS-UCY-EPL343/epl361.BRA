package com.team3.bra;

import java.io.Serializable;
import java.util.Vector;

/**
 * This class is for an object representing an ItemOrder which represents each
 * item that belongs to an order.
 *
 */
public class ItemOrder implements Serializable {
    private static final long serialVersionUID = 2L;

    private int itemID;
    private String name;
    private String description;
    private int quantity;
    private boolean done;
    private String notes;
    private int itemOrderID;
    private double currentPrice;
    private float vat;
    private String category;

    /**
     * A constructor of the ItemOrder object. It sets the item id, name and
     * description that represents, as well as its quantity and any extra notes.
     * The order that it belongs is set to -1.
     *
     * @param item
     *            the item that represents.
     * @param quantity
     *            the quantity of that item in the order.
     * @param notes
     *            any extra notes for that item.
     */
    public ItemOrder(Item item, int quantity, String notes) {
        this.itemOrderID = -1;
        this.itemID = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.quantity = quantity;
        this.notes = notes;
    }

    /**
     * A constructor of the ItemOrder object for report creation. It reads a vector that was
     * returned from JDBC in order to construct the object as it is on the
     * database.
     *
     * @param vec
     *            a vector that is returned from JDBC
     */
    public ItemOrder(Vector<Object> vec) {
        this.itemID = (int) vec.get(0);
        this.name = vec.get(1).toString();
        this.description = vec.get(2).toString();
        this.quantity = (int) vec.get(3);
        this.done = (boolean) vec.get(4);
        this.notes = vec.get(5).toString();
        this.itemOrderID = (int) vec.get(6);
        this.currentPrice = Double.parseDouble(vec.get(7).toString());
    }

    /**
     * A constructor of the ItemOrder object. It reads a vector that was
     * returned from JDBC in order to construct the object as it is on the
     * database.
     *
     * @param vec
     *            a vector that is returned from JDBC
     */
    public ItemOrder(Vector<Object> vec, boolean alter) {
        this.itemID = (int) vec.get(0);
        this.name = vec.get(1).toString();
        this.description = vec.get(2).toString();
        this.quantity = (int) vec.get(3);
        this.done = (boolean) vec.get(4);
        this.notes = vec.get(5).toString();
        this.itemOrderID = (int) vec.get(6);
        this.currentPrice = Double.parseDouble(vec.get(7).toString());
        this.vat = Float.parseFloat(vec.get(8).toString());
        this.category = vec.get(9).toString();
    }

    public int getItemOrderID() {
        return itemOrderID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public int isDone() {
        if (done == true)
            return 1;
        return 0;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public String getCategory() {
        return category;
    }

    public float getVat() {
        return vat;
    }

    @Override
    public String toString() {
        return "ItemOrder{" + "itemID=" + itemID + ", name='" + name + '\'' + ", description='" + description + '\''
                + ", quantity=" + quantity + ", done=" + done + ", notes='" + notes + '\'' + ", itemOrderID="
                + itemOrderID + ", currentPrice=" + currentPrice + ", vat=" + vat + '}';
    }

}