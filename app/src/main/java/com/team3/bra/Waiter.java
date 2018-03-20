package com.team3.bra;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class Waiter extends Activity  {
    private static final long serialVersionUID = 1L;
    static Waiter waiterClass=null;
    static Waiter oldWaiterClass=null;

    public static Waiter myWaiter;
    private OrderArrayAdapter adapter;
    private ArrayList<Order> listOrders =new ArrayList<Order>();
    private ArrayList<Order> notifications =new ArrayList<Order>();
    private Button btnNot;
    boolean checkNotification;
    private Dialogues dialogue;
    private Order selectedOrder;
    private Waiter that=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_layout);
        btnNot=findViewById(R.id.btnNot);

        checkNotification=false;

        adapter=new OrderArrayAdapter(this,listOrders);
        getOrders();

        ListView lv=(ListView) findViewById(R.id.listOrders);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if(checkNotification!=true) {
                    if (position == 0) {
                        showNewTableDialogue();
                    } else {
                        selectedOrder=listOrders.get(position);
                        selectedOrder.fillOrder();
                        showOrderDialogue();
                    }
                }else{
                    selectedOrder=listOrders.get(position);
                    showNotificationDialogue();
                }
            }
        });
        waiterClass=this;
        getNotificationsNum();
    }

    @Override
    public void onResume(){
        super.onResume();
        getOrders();
        if (oldWaiterClass!=null)
            waiterClass=oldWaiterClass;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Order.clearNotifications();
        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
        waiterClass=null;
        oldWaiterClass=null;
    }

    public void setSelectedOrder(Order o){
        this.selectedOrder=o;
    }

    public void backClicked(View v){
        finish();
    }

    public static void cancelNotification(Order o){
        waiterClass=oldWaiterClass;
        NotificationManager nm=(NotificationManager)Waiter.oldWaiterClass.getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(o.getId());
    }

    public void showNewTableDialogue() {
        oldWaiterClass=waiterClass;
        waiterClass=null;
        dialogue=Dialogues.dialogueFactory(this,Waiter.this,R.layout.waiter_table_dialogue);
        Spinner sp=((Spinner)  dialogue.getView().findViewById(R.id.quantity));
        ArrayList<Integer> tables=new ArrayList<Integer>();
        String a[] = {};
        Vector<Vector<Object>> occupied=JDBC.callProcedure("ACTIVETABLES", a);
        for(int i=1;i<=50;i++)
            tables.add(i);
        for(Vector<Object> ob : occupied)
            tables.remove((Integer)ob.get(0));

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, tables );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
    }

    public void showOrderDialogue() {
        oldWaiterClass=waiterClass;
        waiterClass=null;
        dialogue=Dialogues.dialogueFactory(this,Waiter.this,R.layout.waiter_order_dialogue);
    }

    public void showNotificationDialogue() {
        oldWaiterClass=waiterClass;
        waiterClass=null;
        dialogue=Dialogues.dialogueFactory(this,Waiter.this,R.layout.waiter_accept_dialogue);
    }

    public void addTable(View v){
        Spinner s=((Spinner)  dialogue.getView().findViewById(R.id.quantity));
        int table= (int) s.getSelectedItem();
        dialogue.dismiss();


        Order o=new Order(table);
        String a[] = {o.getDateTime(),o.getState()+"",o.getTable()+"",o.getUserID()+"" };
        Vector<Vector<Object>> vec= JDBC.callProcedure("AddOrder", a);
        o.setId(Integer.parseInt(vec.get(0).get(0).toString()));
        selectedOrder=o;

        editOrder(v);
    }

    public void cancelItem(View v){
        dialogue.dismiss();
        waiterClass=oldWaiterClass;
    }

    private void getOrders(){
        listOrders.clear();
        btnNot.setTextColor(Color.BLACK);
        btnNot.setBackgroundColor(Color.TRANSPARENT);
        Order.findActiveOrders();
        listOrders.add(new Order(-1));
        listOrders.addAll(Order.getOrders());
        adapter.notifyDataSetChanged();
    }

    private void getNotificationsNum(){
        Order.findNotificationOrders(notifications);
        btnNot.setText("Notifications ("+Order.getNotificationOrders().size()+")");
    }

    private void getNotifications(){
        listOrders.clear();
        btnNot.setTextColor(Color.RED);
        btnNot.setBackgroundColor(Color.LTGRAY);
        checkNots();
        listOrders.addAll(Order.getNotificationOrders());
        adapter.notifyDataSetChanged();
    }

    public void checkNots(){
        Order.findNotificationOrders(notifications);
        for (Order o : Order.getNewNotificationOrders())
            notifyWaiter(o);
        btnNot.setText("Notifications ("+Order.getNotificationOrders().size()+")");
    }

    public void notifyWaiter(Order o){
        notifications.add(o);
        NotificationCompat.Builder notification=new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.broadwayrestarauntlogo);
        notification.setTicker(o+" is ready.");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(o+" is ready.");
        notification.setAutoCancel(true);
        notification.setGroup("BROADWAYAPP");
        notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intentAccept=new Intent(this,ActionReceiver.class);
        intentAccept.setAction("Accept");
        intentAccept.putExtra("order", o);
        Intent intentIgnore=new Intent(this,ActionReceiver.class);
        intentIgnore.setAction("Ignore");
        intentIgnore.putExtra("order",o);

        myWaiter=this;

        PendingIntent piAccept=PendingIntent.getBroadcast(this,o.getId(),intentAccept,PendingIntent.FLAG_ONE_SHOT);
        PendingIntent piIgnore=PendingIntent.getBroadcast(this,o.getId(),intentIgnore,PendingIntent.FLAG_ONE_SHOT);

        notification.addAction(R.drawable.ic_launcher_foreground,"Accept",piAccept);
        notification.addAction(R.drawable.ic_launcher_foreground,"Ignore",piIgnore);
        notification.setContentIntent(piIgnore);

        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(o.getId(),notification.build());
    }

    public void notificationsClick(View v){
        checkNotification=!checkNotification;
        if(checkNotification==true){
            getNotifications();
        }
        else{
            getOrders();
        }
    }

    public void cancelOrderClick(View v){
        dialogue.dismiss();
        waiterClass=oldWaiterClass;
    }

    public void calcResta(View v){

        dialogue.dismiss();
    }

    public void editOrder(View v){
        Bundle b=new Bundle();
        b.putSerializable("Order",selectedOrder);
        Intent intent = new Intent(Waiter.this, OrderView.class);
        intent.putExtras(b);
        startActivity(intent);
        dialogue.dismiss();
        getOrders();
    }

    public void printReceipt(View v){
        waiterClass=oldWaiterClass;
        Toast toast= Toast.makeText(getApplicationContext(),"Printing Receipt",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }

    public void acceptNot(View v){
        waiterClass=null;
        selectedOrder.setState(3);
        Toast toast= Toast.makeText(getApplicationContext(),"OrderView Accepted",Toast.LENGTH_SHORT);
        toast.show();
        getNotifications();
        if(dialogue!=null)
            dialogue.dismiss();
        waiterClass = oldWaiterClass;
    }
    public void refresh(){
        if(checkNotification==true){
            getNotifications();
        }
        else{
            getOrders();
            getNotificationsNum();
       }
    }
}
