package com.team3.bra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

public class Order extends Activity {
    ScrollView side;
    ScrollView categ;

    public void backClicked(View v){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);
    }
    public void orderBack(View v){
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
}
