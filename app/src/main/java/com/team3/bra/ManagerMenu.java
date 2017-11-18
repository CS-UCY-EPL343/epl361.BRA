package com.team3.bra;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerMenu extends Activity {
    String toastStr;
    ScrollView side;
    ScrollView categ;
    LinearLayout addCateg;
    LinearLayout price;
    LinearLayout vat;
    TextView title,catTitle;
    Button delete;
    EditText etName;
    EditText etPrice;
    EditText etVat;
    EditText etDesciption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastStr="Category";
        setContentView(R.layout.activity_manager_menu);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);
        addCateg = (LinearLayout) findViewById(R.id.addCat);
        price = (LinearLayout) findViewById(R.id.llPrice);
        vat = (LinearLayout) findViewById(R.id.llVat);
        title = (TextView) findViewById(R.id.txtManagerMenu);
        catTitle = (TextView) findViewById(R.id.txtCatName);
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
        toastStr="Category";
        side.setVisibility(View.VISIBLE);
        categ.setVisibility(View.GONE);
        addCateg.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        title.setText("Edit Category SIDE");
        price.setVisibility(View.GONE);
        vat.setVisibility(View.GONE);
        etName.setText("SIDE");
        catTitle.setText("SIDE");
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
        toastStr="Category";
        title.setText("New Category");
        etVat.setText("");
        etPrice.setText("");
        etName.setText("");
        etDesciption.setText("");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.GONE);
        vat.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        categ.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);
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
        toastStr="Item";
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

    public void saveClicked(View v){
        toastStr+=" saved";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastStr, duration);
        toast.show();
        addCateg.setVisibility(View.GONE);
    }
    public void cancelClicked(View v){
        addCateg.setVisibility(View.GONE);
    }
    public void deleteClicked(View v){
        toastStr+=" deleted";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastStr, duration);
        toast.show();
        addCateg.setVisibility(View.GONE);
    }


}
