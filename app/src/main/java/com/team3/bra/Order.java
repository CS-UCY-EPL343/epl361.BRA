package com.team3.bra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Order extends Activity {
    ScrollView side;
    ScrollView categ;

    public void backClicked(View v){
        finish();
    }
    private static Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);
        Bundle b = getIntent().getExtras();
        if(b != null && b.getInt("new")==1){
            ((Button)findViewById(R.id.btnOrderDelete)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.ll1)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.ll2)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.ll3)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.ll4)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.txtOrderNum)).setText("Order : "+b.getString("Table"));
        }
        con=getApplicationContext();

    }
    public void orderCancel(View v){
        finish();
    }
    public void orderSave(View v){
        //TODO
        finish();
    }
    public void orderDelete(View v){
        //TODO
        finish();
    }
    public void sideClick(View v){
        side.setVisibility(View.VISIBLE);
        categ.setVisibility(View.GONE);
    }
    public void orderBackToCateg(View v){
        side.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);
    }
    public void HalloumiClicked(View v){
        Bundle b = new Bundle();
        b.putInt("add",1);
        Intent intent = new Intent(Order.this, NewItem.class);
        intent.putExtras(b);
        startActivity(intent);
    }
    public void keoItemClicked(View v){
        Bundle b = new Bundle();
        b.putInt("add",0);
        Intent intent = new Intent(Order.this, NewItem.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
