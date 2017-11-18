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

import java.util.ArrayList;

public class Waiter extends Activity {
    ArrayAdapter<String> adapter;
    ArrayList<String> listOrders =new ArrayList<String>();
    Button btnNot;
    boolean checkNotification;

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
                            addItems();
                        } else {
                            Intent intent = new Intent(Waiter.this, Order.class);
                            startActivity(intent);
                        }
                    }else{
                        //TODO ACCEPT ORDER
                    }
                }
            });
    }
    public void backClicked(View v){
        finish();
    }

    private void getOrders(){
        listOrders.clear();
        btnNot.setTextColor(Color.BLACK);
        btnNot.setBackgroundColor(Color.TRANSPARENT);
        listOrders.add("<New Order>");
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
                listOrders.add("Order Ready: Table "+i);
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
    public void addItems() {
        adapter.add("new order");
        adapter.notifyDataSetChanged();
    }
}
