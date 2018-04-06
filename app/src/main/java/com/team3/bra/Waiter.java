package com.team3.bra;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

/**
 * This is the Waiter class. It is responsible for displaying the Waiter layout
 * which lists all the Orders that the waiter can interact with. The Waiter can
 * add a new order, edit or delete the order,calling the OrderView class. This
 * view is also responsible for displaying the Orders that are ready from the
 * kitchen.
 */
public class Waiter extends Activity {
    public static Waiter myWaiter;
    static Waiter waiterClass = null;
    static Waiter oldWaiterClass = null;
    private Waiter that = this;

    boolean checkNotification;

    private static final long serialVersionUID = 1L;

    private ArrayList<Order> listOrders = new ArrayList<Order>();
    private ArrayList<Order> notifications = new ArrayList<Order>();

    private OrderArrayAdapter adapter;
    private Button btnNot;
    private Dialogues dialogue;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_layout);
        btnNot = findViewById(R.id.btnNot);

        checkNotification = false;

        myWaiter = this;

        adapter = new OrderArrayAdapter(this, listOrders);
        getOrders();

        ListView lv = (ListView) findViewById(R.id.listOrders);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (checkNotification != true) {
                    if (position == 0) {
                        showNewTableDialogue();
                    } else {
                        selectedOrder = listOrders.get(position);
                        selectedOrder.fillOrder();
                        showOrderDialogue();
                    }
                } else {
                    selectedOrder = listOrders.get(position);
                    showNotificationDialogue();
                }
            }
        });
        waiterClass = this;

        Order.findNotificationOrders(notifications);
        btnNot.setText("Notifications (" + Order.getNotificationOrders().size() + ")");
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrders();
        waiterClass = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Order.clearNotifications();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
        waiterClass = null;
        oldWaiterClass = null;
    }

    /**
     * Sets the current selected order of the Waiter to the given Order.
     *
     * @param o
     *            The given order.
     */
    public void setSelectedOrder(Order o) {
        this.selectedOrder = o;
    }

    /**
     * This function is called by a button to close the Waiter layout.
     *
     * @param v
     */
    public void backClicked(View v) {
        finish();
    }

    /**
     * This function is responsible for cancelling the specified order
     * Notification.
     *
     * @param o
     *            the order to cancel its notification.
     */
    public static void cancelNotification(Order o) {
        waiterClass = oldWaiterClass;
        NotificationManager nm = (NotificationManager) Waiter.myWaiter.getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(o.getId());
    }

    /**
     * Shows a picker of available tables in order to choose one.
     */
    private void showNewTableDialogue() {
        oldWaiterClass = waiterClass;
        waiterClass = null;
        dialogue = Dialogues.dialogueFactory(this, Waiter.this, R.layout.waiter_table_dialogue);
        Spinner sp = ((Spinner) dialogue.getView().findViewById(R.id.quantity));
        ArrayList<Integer> tables = new ArrayList<Integer>();
        String a[] = {};
        Vector<Vector<Object>> occupied = JDBC.callProcedure("ACTIVETABLES", a);
        for (int i = 1; i <= 50; i++)
            tables.add(i);
        for (Vector<Object> ob : occupied)
            tables.remove((Integer) ob.get(0));

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,
                tables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
    }

    /**
     * This is responsible for the menu when an Order is being clicked.
     */
    private void showOrderDialogue() {
        oldWaiterClass = waiterClass;
        waiterClass = null;
        dialogue = Dialogues.dialogueFactory(this, Waiter.this, R.layout.waiter_order_dialogue);
        Button apodixi=((Button)dialogue.getView().findViewById(R.id.btnReceipt));
        Button resta=((Button)dialogue.getView().findViewById(R.id.btnResta));
        if(selectedOrder.getState()==3) {
            apodixi.setVisibility(View.VISIBLE);
            resta.setVisibility(View.GONE);
        }else if(selectedOrder.getState()==5) {
            apodixi.setVisibility(View.GONE);
            if(MainActivity.TAMIAKI==true)
                resta.setVisibility(View.VISIBLE);
            else
                resta.setVisibility(View.GONE);
        }else{
            apodixi.setVisibility(View.GONE);
            resta.setVisibility(View.GONE);
        }
    }

    /**
     * This is responsible for the menu when an Order is being clicked at the
     * Notification Tab.
     */
    private void showNotificationDialogue() {
        oldWaiterClass = waiterClass;
        waiterClass = null;
        dialogue = Dialogues.dialogueFactory(this, Waiter.this, R.layout.waiter_accept_dialogue);
    }

    /**
     * This function is used by a button. It gets the value of the table
     * spinner.
     *
     * @param v
     */
    public void addTable(View v) {
        Spinner s = ((Spinner) dialogue.getView().findViewById(R.id.quantity));
        int table = (int) s.getSelectedItem();
        dialogue.dismiss();

        Order o = new Order(table);
        selectedOrder = o;

        editOrder(v);
    }

    /**
     * This function is used by a button. It dismisses the dialogue that called
     * this function.
     *
     * @param v
     */
    public void cancelItem(View v) {
        dialogue.dismiss();
        waiterClass = oldWaiterClass;
    }

    /**
     * This function refreshes the arrayAdapter that displays the orders.
     */
    private void getOrders() {
        listOrders.clear();
        btnNot.setTextColor(Color.BLACK);
        btnNot.setBackgroundColor(Color.TRANSPARENT);
        Order.findActiveOrders();
        listOrders.add(new Order(-1));
        listOrders.addAll(Order.getOrders());
        adapter.notifyDataSetChanged();
    }

    /**
     * This function refreshes the arrayAdapter that displays the orders that
     * have just been finished in the kitchen.
     */
    private void getNotifications() {
        listOrders.clear();
        btnNot.setTextColor(Color.RED);
        btnNot.setBackgroundColor(Color.LTGRAY);
        checkNots();
        listOrders.addAll(Order.getNotificationOrders());
        adapter.notifyDataSetChanged();
    }

    /**
     * Checks for new notification orders and notifies the waiter.
     */
    private void checkNots() {
        Order.findNotificationOrders(notifications);
        for (Order o : Order.getNewNotificationOrders())
            notifyWaiter(o);
        btnNot.setText("Notifications (" + Order.getNotificationOrders().size() + ")");
    }

    /**
     * This function is responsible to create an Android notification to let the
     * Waiter know that the specified order is ready from the kitchen.
     *
     * @param o
     *            The order that is ready from the kitchen.
     */
    private void notifyWaiter(Order o) {
        notifications.add(o);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.broadwayrestarauntlogo);
        notification.setTicker(o + " is ready.");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(o + " is ready.");
        notification.setAutoCancel(true);
        notification.setGroup("BROADWAYAPP");
        notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intentAccept = new Intent(this, ActionReceiver.class);
        intentAccept.setAction("Accept");
        intentAccept.putExtra("order", o);
        Intent intentIgnore = new Intent(this, ActionReceiver.class);
        intentIgnore.setAction("Ignore");
        intentIgnore.putExtra("order", o);

        PendingIntent piAccept = PendingIntent.getBroadcast(this, o.getId(), intentAccept, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent piIgnore = PendingIntent.getBroadcast(this, o.getId(), intentIgnore, PendingIntent.FLAG_ONE_SHOT);

        notification.addAction(R.drawable.ic_launcher_foreground, "Accept", piAccept);
        notification.addAction(R.drawable.ic_launcher_foreground, "Ignore", piIgnore);
        notification.setContentIntent(piIgnore);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(o.getId(), notification.build());
    }

    /**
     * This function is called by a button. It triggers between the notification
     * orders tab and normal orders tab.
     *
     * @param v
     */
    public void notificationsClick(View v) {
        checkNotification = !checkNotification;
        if (checkNotification == true) {
            getNotifications();
        } else {
            getOrders();
        }
    }

    /**
     * This function is called by a button. It cancels the current dialogue.
     *
     * @param v
     */
    public void cancelOrderClick(View v) {
        dialogue.dismiss();
        waiterClass = oldWaiterClass;
    }

    /**
     * This function is called by a button. It calculates the amount of change
     * that need to be given to the user.
     *
     * @param v
     */
    public void calcResta(View v) {
        dialogue.dismiss();
        dialogue = Dialogues.dialogueFactory(this, Waiter.this, R.layout.waiter_find_resta);
        TextView table=((TextView)dialogue.getView().findViewById(R.id.txtTable));
        TextView sum=((TextView)dialogue.getView().findViewById(R.id.txtSum));
        final EditText paid=((EditText)dialogue.getView().findViewById(R.id.txtPaid));
        final TextView change=((TextView)dialogue.getView().findViewById(R.id.txtChange));
        table.setText(selectedOrder.toString());

        String a[] = {selectedOrder.getId()+""};
        Vector<Vector<Object>> resta = JDBC.callProcedure("SumOrder", a);
        BigDecimal bd= (BigDecimal) resta.get(0).get(0);
        final double total= bd.doubleValue();

        DecimalFormat df= new DecimalFormat("0.00");
        sum.setText(df.format(total));
        change.setText("");

        paid.addTextChangedListener(new TextWatcher()
        {   @Override
            public void afterTextChanged(Editable mEdit) {
                try {
                    double x = Double.parseDouble(mEdit.toString());
                    double calculation=((x * 1.0) - total);
                    DecimalFormat df= new DecimalFormat("0.00");
                    change.setText(df.format(calculation));
                }catch(Exception e){}
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        });
    }
    public void doneResta(View v){
        selectedOrder.setState(6);
        Toast toast = Toast.makeText(getApplicationContext(), "Transaction completed", Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }
    /**
     * This function is called by a button. It is responsible for editing an
     * order.
     *
     * @param v
     */
    public void editOrder(View v) {
        Bundle b = new Bundle();
        b.putSerializable("Order", selectedOrder);
        Intent intent = new Intent(Waiter.this, OrderView.class);
        intent.putExtras(b);
        startActivity(intent);
        dialogue.dismiss();
        getOrders();
    }

    /**
     * This function is called by a button. It is responsible for printing a
     * receipt of the specified order.
     *
     * @param v
     */
    public void printReceipt(View v) {
        waiterClass = oldWaiterClass;
        Toast toast = Toast.makeText(getApplicationContext(), "Printing Receipt", Toast.LENGTH_SHORT);
        toast.show();
        selectedOrder.setState(4);
        dialogue.dismiss();
    }

    /**
     * This function is called by a button. This function is to accept a
     * notification of an order that is ready by the kitchen.
     *
     * @param v
     */
    public void acceptNot(View v) {
        waiterClass = null;
        selectedOrder.setState(3);
        Toast toast = Toast.makeText(getApplicationContext(), "OrderView Accepted", Toast.LENGTH_SHORT);
        toast.show();
        getNotifications();
        if (dialogue != null)
            dialogue.dismiss();
        waiterClass = oldWaiterClass;
    }

    /**
     * This function is responsible for refreshing all the tabs of the screen
     * getting all new information from the DB.
     */
    public void refresh() {
        if (checkNotification == true) {
            getNotifications();
        } else {
            getOrders();
            checkNots();
        }
    }
}
