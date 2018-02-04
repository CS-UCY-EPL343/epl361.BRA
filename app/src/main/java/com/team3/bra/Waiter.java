package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Waiter extends Activity {
    ArrayAdapter<String> adapter;
    ArrayList<String> listOrders =new ArrayList<String>();
    Button btnNot;
    boolean checkNotification;
    Dialogues dialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_layout);
        btnNot=findViewById(R.id.btnNot);

        checkNotification=false;

        adapter=new ArrayAdapter<String>(this,
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
                        showOrderDialogue();
                    }
                }else{
                    showNotificationDialogue();
                }
            }
        });
    }
    public void backClicked(View v){
        finish();
    }


    public void showNewTableDialogue() {
        dialogue=Dialogues.dialogueFactory(this,Waiter.this,R.layout.waiter_table_dialogue);
    }
    public void showOrderDialogue() {
        dialogue=Dialogues.dialogueFactory(this,Waiter.this,R.layout.waiter_order_dialogue);
    }
    public void showNotificationDialogue() {
        dialogue=Dialogues.dialogueFactory(this,Waiter.this,R.layout.waiter_accept_dialogue);
    }
    public void addTable(View v){
        Spinner s=((Spinner)  dialogue.getView().findViewById(R.id.quantity));
        int table= (int) Integer.parseInt((String) s.getSelectedItem());
        dialogue.dismiss();
        Bundle b=new Bundle();
        b.putInt("new",1);
        b.putString("Table","Table "+table);
        Intent intent = new Intent(Waiter.this, OrderView.class);
        intent.putExtras(b);
        startActivity(intent);
        adapter.add("Table "+table);
        adapter.notifyDataSetChanged();
    }
    public void cancelItem(View v){dialogue.dismiss();}

    private void getOrders(){
        listOrders.clear();
        btnNot.setTextColor(Color.BLACK);
        btnNot.setBackgroundColor(Color.TRANSPARENT);
        listOrders.add("<New OrderView>");
        for(int i=0;i<19;i++){
            if(Math.random()>0.5)
                listOrders.add("Table "+i);
        }
        adapter.notifyDataSetChanged();
    }

    private void getNotifications(){
        listOrders.clear();
        btnNot.setTextColor(Color.RED);
        btnNot.setBackgroundColor(Color.LTGRAY);
        for(int i=0;i<19;i++){
            if(Math.random()>0.8)
                listOrders.add("OrderView Ready: Table "+i);
        }
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
        Intent intent = new Intent(Waiter.this, OrderView.class);
        startActivity(intent);
        dialogue.dismiss();
    }
    public void printReceipt(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"Printing Receipt",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }
    public void acceptNot(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"OrderView Accepted",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }
}
