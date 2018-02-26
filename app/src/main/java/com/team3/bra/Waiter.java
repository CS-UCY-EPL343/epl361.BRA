package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class Waiter extends Activity {
    private ArrayAdapter<Order> adapter;
    private ArrayList<Order> listOrders =new ArrayList<Order>();
    private Button btnNot;
    private boolean checkNotification;
    private Dialogues dialogue;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_layout);
        btnNot=findViewById(R.id.btnNot);

        checkNotification=false;

        adapter=new ArrayAdapter<Order>(this,
                R.layout.custom_listview_layout,
                listOrders);

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
    }

    @Override
    public void onResume(){
        super.onResume();
        getOrders();
    }


    public void backClicked(View v){
        finish();
    }


    public void showNewTableDialogue() {
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
        dialogue=Dialogues.dialogueFactory(this,Waiter.this,R.layout.waiter_order_dialogue);
    }
    public void showNotificationDialogue() {
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
    public void cancelItem(View v){dialogue.dismiss();}

    private void getOrders(){
        listOrders.clear();
        btnNot.setTextColor(Color.BLACK);
        btnNot.setBackgroundColor(Color.TRANSPARENT);
        Order.findActiveOrders();
        listOrders.add(new Order(-1));
        listOrders.addAll(Order.orders);
        adapter.notifyDataSetChanged();
    }

    private void getNotifications(){
        listOrders.clear();
        btnNot.setTextColor(Color.RED);
        btnNot.setBackgroundColor(Color.LTGRAY);
        Order.findNotificationOrders();
        listOrders.addAll(Order.notificationOrders);
        adapter.notifyDataSetChanged();
    }

    public void notificationsClick(View v){
        checkNotification=!checkNotification;
        if(checkNotification==true)
            getNotifications();
        else
            getOrders();
    }

    public void cancelOrderClick(View v){
        dialogue.dismiss();
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
        Toast toast= Toast.makeText(getApplicationContext(),"Printing Receipt",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }
    public void acceptNot(View v){
        selectedOrder.setState(3);
        Toast toast= Toast.makeText(getApplicationContext(),"OrderView Accepted",Toast.LENGTH_SHORT);
        toast.show();
        getNotifications();
        dialogue.dismiss();
    }
}
