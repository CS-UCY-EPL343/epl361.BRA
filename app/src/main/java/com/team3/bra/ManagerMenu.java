package com.team3.bra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ManagerMenu extends Activity {
    ScrollView side;
    ScrollView categ;
    ScrollView addCateg;
    LinearLayout price;
    LinearLayout vat;
    TextView title;
    Button delete;
    EditText etName;
    EditText etPrice;
    EditText etVat;
    EditText etDesciption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manager_menu);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);
        addCateg = (ScrollView) findViewById(R.id.scrollAdd);
        price = (LinearLayout) findViewById(R.id.llPrice);
        vat = (LinearLayout) findViewById(R.id.llVat);
        title = (TextView) findViewById(R.id.txtManagerMenu);
        delete = (Button) findViewById(R.id.btnDelete);
        etDesciption = (EditText) findViewById(R.id.etDesc);
        etName = (EditText) findViewById(R.id.etName);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etVat = (EditText) findViewById(R.id.etVat);
    }
    public void orderBackManager(View v){
        finish();
    }
    public void sideClickManager(View v){
        side.setVisibility(View.VISIBLE);
        categ.setVisibility(View.GONE);
        addCateg.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        title.setText("Edit Category SIDE");
        etName.setText("SIDE");
        etDesciption.setText("side dishes");
    }
    public void orderBackToCategManager(View v){
        side.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);
        addCateg.setVisibility(View.GONE);
        etVat.setText("");
        etPrice.setText("");
        etName.setText("");
        etDesciption.setText("");
    }
    public void addCategory(View v){
        title.setText("New Category");
        etVat.setText("");
        etPrice.setText("");
        etName.setText("");
        etDesciption.setText("");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.GONE);
        vat.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
    }
    public void addItem(View v){
        title.setText("New Item");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        vat.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        etVat.setText("");
        etPrice.setText("");
        etName.setText("");
        etDesciption.setText("");
    }

    public void editItem(View v){
        title.setText("Edit Item FETTA CHEESE");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        vat.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        etVat.setText("19");
        etPrice.setText("4.50");
        etName.setText("FETTA CHEESE");
        etDesciption.setText("greek cheese");


    }

    public void saveCancelDelete(View v){
        addCateg.setVisibility(View.GONE);
    }


}
