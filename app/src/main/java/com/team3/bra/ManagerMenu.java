package com.team3.bra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import java.util.PriorityQueue;

public class ManagerMenu extends Activity {
    boolean itemCategory;
    ScrollView side;
    ScrollView categ;
    LinearLayout addCateg;
    LinearLayout price;
    LinearLayout cat;
    LinearLayout vat;
    TextView title;
    Button delete;
    EditText etName;
    EditText etPrice;
    Spinner etCat;
    EditText etDescription;
    EditText etVat;
    int selectedID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_menu_layout);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);
        addCateg = (LinearLayout) findViewById(R.id.addCat);
        addCateg.setVisibility(View.GONE);
        price = (LinearLayout) findViewById(R.id.llPrice);
        cat = (LinearLayout) findViewById(R.id.llCat);
        vat=(LinearLayout) findViewById(R.id.llVat);
        title = (TextView) findViewById(R.id.txtManagerMenu);
        delete = (Button) findViewById(R.id.btnDelete);
        etDescription = (EditText) findViewById(R.id.etDesc);
        etName = (EditText) findViewById(R.id.etName);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etCat = (Spinner) findViewById(R.id.etCat);
        etVat=(EditText) findViewById(R.id.etVat);
        insertCategories();

    }
    private void fillSpinner(Spinner sp,int id){
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, Category.categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        sp.setSelection(id-1);//TODO will not work if the categories are sorted
    }

    public void orderBackManager(View v){
        finish();
    }
    public void editCategory(View v,Category category){
        selectedID=category.getId();
        itemCategory=true;
        side.setVisibility(View.VISIBLE);
        categ.setVisibility(View.GONE);
        addCateg.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        title.setText("Edit Category");
        price.setVisibility(View.GONE);
        cat.setVisibility(View.GONE);
        vat.setVisibility(View.VISIBLE);
        etName.setText(category.getName());
        etDescription.setText(category.getDescription());
        etVat.setText(category.getVat()+"");

    }
    public void orderBackToCategManager(View v){
        side.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);
        addCateg.setVisibility(View.GONE);
        vat.setVisibility(View.GONE);
        etPrice.setText("");
        etName.setText("");
        etDescription.setText("");
    }
    public void addCategory(View v){
        selectedID=-1;
        itemCategory=true;
        title.setText("New Category");
        etPrice.setText("");
        etName.setText("");
        etVat.setText("");
        etDescription.setText("");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.GONE);
        cat.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        categ.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);
        vat.setVisibility(View.VISIBLE);

    }
    public void addItem(View v){
        int categoryID= selectedID;
        selectedID=-1;
        itemCategory=false;
        title.setText("New Item");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        vat.setVisibility(View.GONE);
        cat.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        fillSpinner(etCat,categoryID);
        etPrice.setText("");
        etName.setText("");
        etDescription.setText("");
    }

    public void editItem(View v,Item item){
        selectedID=item.getId();
        itemCategory=false;
        title.setText("Edit Item");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        cat.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        vat.setVisibility(View.GONE);
        fillSpinner(etCat,item.getCategoryID());
        etPrice.setText(item.getPrice()+"");
        etName.setText(item.getName());
        etDescription.setText(item.getDescreption());


    }

    public void saveClicked(View v){
        String toastStr;
        if(itemCategory==true){//Category mode
            String a[] = { selectedID+"",etName.getText().toString(),etVat.getText().toString(), etDescription.getText().toString() };
            toastStr="Category";
            JDBC.callProcedure("AddCategory", a);
        }else{ //Item mode
            String a[] = { selectedID+"",etName.getText().toString(),etPrice.getText().toString(), etDescription.getText().toString(),((Category)etCat.getSelectedItem()).getId()+"" };
            toastStr="Item";
            JDBC.callProcedure("AddItem", a);
        }
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

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        String toastStr;
                        if(itemCategory==true){//Category mode
                            String a[] = {selectedID+""};
                            toastStr="Category";
                            JDBC.callProcedure("RemoveCategory", a);
                        }else{ //Item mode
                            String a[] = {selectedID+""};
                            toastStr="Item";
                            JDBC.callProcedure("RemoveItem", a);
                        }
                        toastStr+=" deleted";
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, toastStr, duration);
                        toast.show();
                        addCateg.setVisibility(View.GONE);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete "+etName.getText().toString()+"?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }


    public void insertCategories(){
        side.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);


        Category.findCategories();
        final ArrayList<Category> categories=Category.categories;

        final float scale = getResources().getDisplayMetrics().density;
        LinearLayout ll = (LinearLayout) findViewById(R.id.categoryLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)(80*scale),1.0f);
        lp.gravity= Gravity.TOP | Gravity.BOTTOM;
        int i=-2;
        int count=0;

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

                        fromCategoriesToItems(categories.get(t));
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

    public void fromCategoriesToItems(final Category cat){
        selectedID=cat.getId();

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

                        addItem(v);
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

                    editItem(v,items.get(t));
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


        side.setVisibility(View.VISIBLE);
        categ.setVisibility(View.GONE);
        addCateg.setVisibility(View.GONE);
    }

}
