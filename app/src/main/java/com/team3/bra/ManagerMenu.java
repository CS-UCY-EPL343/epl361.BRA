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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class ManagerMenu extends Activity {
    String toastStr;
    ScrollView side;
    ScrollView categ;
    LinearLayout addCateg;
    LinearLayout price;
    LinearLayout vat;
    TextView title;
    Button delete;
    EditText etName;
    EditText etPrice;
    EditText etCat;
    EditText etDesciption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastStr="Category";
        setContentView(R.layout.manager_menu_layout);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);
        addCateg = (LinearLayout) findViewById(R.id.addCat);
        price = (LinearLayout) findViewById(R.id.llPrice);
        vat = (LinearLayout) findViewById(R.id.llCat);
        title = (TextView) findViewById(R.id.txtManagerMenu);
        delete = (Button) findViewById(R.id.btnDelete);
        etDesciption = (EditText) findViewById(R.id.etDesc);
        etName = (EditText) findViewById(R.id.etName);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etCat = (EditText) findViewById(R.id.etCat);
        insertCategories();

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
        etDesciption.setText("side dishes");
    }
    public void orderBackToCategManager(View v){
        side.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);
        addCateg.setVisibility(View.GONE);
        etCat.setText("");
        etPrice.setText("");
        etName.setText("");
        etDesciption.setText("");
    }
    public void addCategory(View v){
        toastStr="Category";
        title.setText("New Category");
        etCat.setText("");
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
        toastStr="Item";
        title.setText("New Item");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        vat.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        etCat.setText("");
        etPrice.setText("");
        etName.setText("");
        etDesciption.setText("");
    }

    public void editItem(View v,BrItem item){
        toastStr="Item";
        title.setText("Edit Item FETTA CHEESE");
        addCateg.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        vat.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        etCat.setText(item.getCategoryName());
        etPrice.setText(item.getPrice()+"");
        etName.setText(item.getName());
        etDesciption.setText(item.getDescreption());


    }

    public void saveClicked(View v){
        toastStr+=" saved";
        String a[] = { etName.getText().toString(),etPrice.getText().toString(),etDesciption.getText().toString(),etCat.getText().toString() };
      //  String procedure = "EDITITEM";
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
        builder.setMessage("Are you sure you want to delete "+toastStr+"?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }


    public void insertCategories(){
        side.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);

        String a[] = { "0" };
        String procedure = "SHOWCATEGORYDETAILS";
        final ArrayList<Category> categories=new ArrayList<Category>();
        Vector<Vector<Object>> vec=JDBC.callProcedure( procedure, a);
        for(int i=0;i<vec.size();i++){
            categories.add(new Category(vec.get(i)));
        }


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

    public void fromCategoriesToItems(Category cat){
        System.out.println(cat.getId());

       final ArrayList<BrItem> items = new ArrayList<BrItem>();
        String a[] = { "-1",cat.getId()+"" };
        String procedure = "SHOWITEMDETAILS";
        Vector<Vector<Object>> vec=JDBC.callProcedure( procedure, a);
        for(int i=0;i<vec.size();i++){
            items.add(new BrItem(vec.get(i)));
        }

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

                sideClickManager(v);
            }
        });
        ll.addView(categoryName,lparam);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)(80*scale),1.0f);
        lp.gravity= Gravity.TOP | Gravity.BOTTOM;
        int i=-1;
        int count=0;

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(newLayout);

        while(items.size()>i){

            Button b = new Button(this);
            b.getBackground().setColorFilter(ContextCompat.getColor(this,R.color.transparent), PorterDuff.Mode.MULTIPLY);
            if (i==-1) {
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

        while(count == 1 || count ==2) {
            newLayout.addView(new TextView(this), lp);
            count++;
        }




        side.setVisibility(View.VISIBLE);
        categ.setVisibility(View.GONE);
    }

}
