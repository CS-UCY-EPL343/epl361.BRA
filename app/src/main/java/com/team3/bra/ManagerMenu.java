package com.team3.bra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagerMenu extends Activity {
    ScrollView scrollItems, scrollCategories;
    LinearLayout addCateg,addItem;
    TextView txtItemTitle,txtCatTitle;
    Button btnItemDelete,btnCatDelete,btnCatSave,btnItemSave;
    EditText etItemName,etItemDesc,etItemPrice,etCatName,etCatDesc,etCatVat;
    Spinner spnItemCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_menu_layout);

        scrollCategories = (ScrollView) findViewById(R.id.scrollCat);

        scrollItems = (ScrollView) findViewById(R.id.scrollSide);
        scrollItems.setVisibility(View.GONE);
        addCateg = (LinearLayout) findViewById(R.id.addCat);
        addCateg.setVisibility(View.GONE);
        addItem= (LinearLayout) findViewById(R.id.addItem);
        addItem.setVisibility(View.GONE);

        txtItemTitle = (TextView) findViewById(R.id.txtItemTitle);
        txtCatTitle = (TextView) findViewById(R.id.txtCatTitle);

        btnItemDelete = (Button) findViewById(R.id.btnItemDelete);
        btnCatDelete = (Button) findViewById(R.id.btnCatDelete);
        btnCatSave = (Button) findViewById(R.id.btnCatSave);
        btnItemSave = (Button) findViewById(R.id.btnItemSave);

        etItemName= (EditText) findViewById(R.id.etItemName);
        etItemDesc= (EditText) findViewById(R.id.etItemDesc);
        etItemPrice= (EditText) findViewById(R.id.etItemPrice);
        spnItemCat= (Spinner) findViewById(R.id.spnItemCat);
        etCatName= (EditText) findViewById(R.id.etCatName);
        etCatDesc= (EditText) findViewById(R.id.etCatDesc);
        etCatVat= (EditText) findViewById(R.id.etCatVat);

        showCategories();

    }
    private void fillSpinner(Spinner sp,Category cat){
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, Category.categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        sp.setSelection(Category.categories.indexOf(cat));
    }

    public void orderBackManager(View v){
        finish();
    }

    public void editCategory(View v,final Category cat){
        scrollItems.setVisibility(View.VISIBLE);
        scrollCategories.setVisibility(View.GONE);
        addCateg.setVisibility(View.VISIBLE);
        btnCatDelete.setVisibility(View.VISIBLE);

        txtCatTitle.setText("Edit Category");
        etCatName.setText(cat.getName());
        etCatDesc.setText(cat.getDescription());
        etCatVat.setText(cat.getVat()+"");

        btnCatDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                catDeleteClicked(v,cat.getId());
            }
        });
        btnCatSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                catSaveClicked(v,cat.getId());
            }
        });

    }
    public void orderBackToCategManager(View v){
        scrollItems.setVisibility(View.GONE);
        scrollCategories.setVisibility(View.VISIBLE);
        addCateg.setVisibility(View.GONE);
        addItem.setVisibility(View.GONE);
    }

    public void addCategory(View v){
        txtCatTitle.setText("New Category");
        etCatName.setText("");
        etCatDesc.setText("");
        etCatVat.setText("");

        addCateg.setVisibility(View.VISIBLE);
        btnCatDelete.setVisibility(View.GONE);

        btnCatSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                catSaveClicked(v,-1);
            }
        });
    }
    public void addItem(View v,final Category cat){
        txtItemTitle.setText("New Item");
        addItem.setVisibility(View.VISIBLE);

        btnItemDelete.setVisibility(View.GONE);
        fillSpinner(spnItemCat,cat);
        etItemPrice.setText("");
        etItemName.setText("");
        etItemDesc.setText("");

        btnItemSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemSaveClicked(v,-1,cat);
            }
        });
    }

    public void editItem(View v,final Item item,final Category cat){
        txtItemTitle.setText("Edit Item");
        addItem.setVisibility(View.VISIBLE);
        btnItemDelete.setVisibility(View.VISIBLE);
        fillSpinner(spnItemCat,cat);

        etItemPrice.setText(item.getPrice()+"");
        etItemName.setText(item.getName());
        etItemDesc.setText(item.getDescription());

        btnItemDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemDeleteClicked(v,item.getId(),cat);
            }
        });
        btnItemSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemSaveClicked(v,item.getId(),cat);
            }
        });
    }

    public void itemSaveClicked(View v,int itemID,Category cat){
        String a[] = { itemID+"",etItemName.getText().toString(),etItemPrice.getText().toString(), etItemDesc.getText().toString(),((Category)spnItemCat.getSelectedItem()).getId()+"" };
        JDBC.callProcedure("AddItem", a);
        showItems(cat);//Will refresh current viewed category only.

        Toast toast = Toast.makeText(getApplicationContext(), "Item saved", Toast.LENGTH_SHORT);
        toast.show();
        addItem.setVisibility(View.GONE);
    }

    public void catSaveClicked(View v,int selectedID){
        String a[] = { selectedID+"",etCatName.getText().toString(),etCatVat.getText().toString(), etCatDesc.getText().toString() };
        JDBC.callProcedure("AddCategory", a);
        showCategories();

        Toast toast = Toast.makeText(getApplicationContext(), "Category saved", Toast.LENGTH_SHORT);
        toast.show();
        addCateg.setVisibility(View.GONE);
    }

    public void cancelClicked(View v){
        addCateg.setVisibility(View.GONE);
        addItem.setVisibility(View.GONE);
    }

    public void itemDeleteClicked(View v,final int itemID,final Category cat){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        String a[] = {itemID+""};
                        JDBC.callProcedure("RemoveItem", a);
                        showItems(cat); //Will refresh current viewed category only.

                        Toast toast = Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT);
                        toast.show();
                        addItem.setVisibility(View.GONE);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete "+etItemName.getText().toString()+"?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
    public void catDeleteClicked(View v,final int selectedID){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        String a[] = {selectedID+""};
                        JDBC.callProcedure("RemoveCategory", a);
                        showCategories();

                        Toast toast = Toast.makeText(getApplicationContext(), "Category deleted", Toast.LENGTH_SHORT);
                        toast.show();
                        addCateg.setVisibility(View.GONE);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete "+etCatName.getText().toString()+"?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    public void showCategories(){
        scrollItems.setVisibility(View.GONE);
        scrollCategories.setVisibility(View.VISIBLE);

        Category.findCategories();
        final ArrayList<Category> categories=Category.categories;

        final float scale = getResources().getDisplayMetrics().density;
        LinearLayout ll = (LinearLayout) findViewById(R.id.categoryLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)(80*scale),1.0f);
        lp.gravity= Gravity.TOP | Gravity.BOTTOM;
        int i=-2;
        int count=0;

        ll.removeAllViews();
        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(newLayout);

        while(categories.size()>i){
            Button b = new Button(this);
            b.getBackground().setColorFilter(ContextCompat.getColor(this,R.color.transparent), PorterDuff.Mode.MULTIPLY);

            if (i==-2) {
                b.setText("BACK");
                b.setTextColor(Color.RED);
                newLayout.addView(b,lp);
                i++;
                count++;
                b.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        orderBackManager(v);
                    }
                });
                continue;
            }
            else if (i==-1) {
                b.setText("ADD CATEGORY");
                newLayout.addView(b,lp);
                i++;
                count++;
                b.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        addCategory(v);
                    }
                });
                continue;
            }else {

                b.setText(categories.get(i).getName());

                final int t = i;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showItems(categories.get(t));
                    }
                });
            }
            if(count < 3){
                newLayout.addView(b,lp);
            }
            else{
                newLayout = new LinearLayout(this);
                newLayout.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(newLayout);
                count=0;
                newLayout.addView(b,lp);
            }
            i++;
            count++;

        }

        while(count == 1 || count ==2) {
            newLayout.addView(new TextView(this), lp);
            count++;
        }

    }

    public void showItems(final Category cat){
        cat.fillCategory();
        final ArrayList<Item> items=cat.getItems();

        final float scale = getResources().getDisplayMetrics().density;
        LinearLayout ll = (LinearLayout) findViewById(R.id.itemLayout);
        ll.removeAllViews();

        TextView categoryName = new TextView(this);
        categoryName.setText(cat.getName());
        LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        categoryName.setGravity(Gravity.CENTER);
        categoryName.setTextSize(18);
        categoryName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editCategory(v,cat);
            }
        });
        ll.addView(categoryName,lparam);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)(80*scale),1.0f);
        lp.gravity= Gravity.TOP | Gravity.BOTTOM;
        int i=-2;
        int count=0;

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(newLayout);

        while(items.size()>i){
            Button b = new Button(this);
            b.getBackground().setColorFilter(ContextCompat.getColor(this,R.color.transparent), PorterDuff.Mode.MULTIPLY);
            //insert BACK button
            if (i==-2) {
                b.setText("BACK");
                b.setTextColor(Color.RED);
                newLayout.addView(b,lp);
                i++;
                count++;
                b.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        orderBackToCategManager(v);
                    }
                });
                continue;
            }
            //insery ADD ITEM button
            else if (i==-1) {
                b.setText("ADD ITEM");
                newLayout.addView(b, lp);
                i++;
                count++;
                b.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        addItem(v,cat);
                    }
                });
                continue;
            }
            //insert food item buttons
            b.setText(items.get(i).getName());
            final int t=i;
            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    editItem(v,items.get(t),cat);
                }
            });

            if(count < 3){
                newLayout.addView(b,lp);
            }
            else{
                newLayout = new LinearLayout(this);
                newLayout.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(newLayout);
                count=0;
                newLayout.addView(b,lp);
            }
            i++;
            count++;

        }
        //fll the empty spaces
        while(count == 1 || count ==2) {
            newLayout.addView(new TextView(this), lp);
            count++;
        }


        scrollItems.setVisibility(View.VISIBLE);
        scrollCategories.setVisibility(View.GONE);
        addCateg.setVisibility(View.GONE);
    }

}
