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
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;
import java.util.Vector;

public class OrderView extends Activity {
    private ScrollView side;
    private ScrollView categ;
    private Dialogues dialogue;
    private Order currentOrder;
    private ItemOrder itemOrder;
    private static Context con;
    private ArrayList<ItemOrder> addItemOrders =new ArrayList<ItemOrder>();
    private ArrayList<ItemOrder> removeItemOrders =new ArrayList<ItemOrder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);

        Bundle b = getIntent().getExtras();
        currentOrder =(Order)b.getSerializable("Order");

        TextView txtOrderNum=(TextView) findViewById(R.id.txtOrderNum);
        txtOrderNum.setText(currentOrder.toString());
        LinearLayout llContents=(LinearLayout)findViewById(R.id.llContents);

        con=getApplicationContext();
        insertCategories();
        showItems();

    }

    public void backClicked(View v){
        finish();
    }
    public void orderCancel(View v){
        finish();
    }

    public void orderSave(View v){
        ItemOrder io;
        while(addItemOrders.isEmpty()!=true){
            io=addItemOrders.remove(0);
            String a[] = {io.getItemOrderID()+"",currentOrder.getId()+"",io.getItemID()+"",io.getQuantity()+"",io.isDone()+"",io.getNotes() };
            Vector<Vector<Object>> vec= JDBC.callProcedure("AddItem_Order", a);
        }
        while(removeItemOrders.isEmpty()!=true){
            io=removeItemOrders.remove(0);
            String a[] = {io.getItemOrderID()+""};
            Vector<Vector<Object>> vec= JDBC.callProcedure("RemoveItem_Order", a);
        }
        finish();
    }

    public void orderDelete(View v){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        String a[] = {currentOrder.getId()+""};
                        JDBC.callProcedure("RemoveOrder", a);
                        Toast toast= Toast.makeText(getApplicationContext(),"Order deleted",Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this order?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
    public void sideClick(View v){
        side.setVisibility(View.VISIBLE);
        categ.setVisibility(View.GONE);
    }

    public void fromCategoriesToItems(Category cat){

        cat.fillCategory();
        final ArrayList<Item> items=cat.getItems();

        final float scale = getResources().getDisplayMetrics().density;
        LinearLayout ll = (LinearLayout) findViewById(R.id.itemLayout);
        ll.removeAllViews();
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,(int)(80*scale),1.0f);
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
                        orderBackToCateg(v);
                    }
                });
                continue;
            }

            b.setText(items.get(i).getName());
            final int t=i;
            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    showItemDialogue(v,items.get(t));
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

    public void orderBackToCateg(View v){
        side.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);
    }

    public void showItemDialogue(View view, final Item item) {
        dialogue=Dialogues.dialogueFactory(this,OrderView.this,R.layout.order_item_add_dialogue);
        Button b= (Button) dialogue.getView().findViewById(R.id.btnAddItem);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addItem(v,item);
            }
        });
    }
    public void addItem(View v,Item item){
        View dialogueView=dialogue.getView();
        EditText e= (EditText)dialogueView.findViewById(R.id.txtComments);
        EditText qt=((EditText)  dialogueView.findViewById(R.id.txtQuantity));
        TextView t= (TextView) dialogueView.findViewById(R.id.txtItem);
        int quantity= (int) Integer.parseInt((String) (qt.getText().toString()));
        String comments= e.getText().toString();

        ItemOrder io=new ItemOrder(item,quantity,comments);
        addItemOrders.add(io);
        currentOrder.getItems().add(io);
        showItems();

        Toast toast= Toast.makeText(getApplicationContext(),quantity+" "+t.getText().toString()+" "+comments,Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }

    public void editItem(View v,ItemOrder io){
        View myView=dialogue.getView();
        EditText e= (EditText)myView.findViewById(R.id.txtComments);
        TextView qt= (TextView) myView.findViewById(R.id.txtQuantity);
        TextView t= (TextView) myView.findViewById(R.id.txtItem);
        int quantity= (int) Integer.parseInt(qt.getText().toString());
        String comments= e.getText().toString();

        int id=io.getItemID();
        addItemOrders.remove(io);
        currentOrder.getItems().remove(io);
        io.setQuantity(quantity);
        io.setNotes(comments);
        addItemOrders.add(io);
        currentOrder.getItems().add(io);

        showItems();

        Toast toast= Toast.makeText(getApplicationContext(),quantity+" "+t.getText().toString()+" "+comments,Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }

    public void cancelItem(View v){dialogue.dismiss();}


    public void itemClicked(final ItemOrder io){
        dialogue=Dialogues.dialogueFactory(this,OrderView.this,R.layout.order_item_edit_dialogue);
        View myView=dialogue.getView();
        Button b= (Button) dialogue.getView().findViewById(R.id.btnEditItem);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editItem(v,io);
            }
        });
        Button b2= (Button) dialogue.getView().findViewById(R.id.btnDeleteItem);
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteItem(v,io);
            }
        });
        ((TextView)myView.findViewById(R.id.txtItem)).setText(io.getName());
        ((TextView)myView.findViewById(R.id.txtDescr)).setText(io.getDescription());
        ((EditText)myView.findViewById(R.id.txtQuantity)).setText(io.getQuantity()+"");
        ((EditText)myView.findViewById(R.id.txtComments)).setText(io.getNotes());
    }

    public void deleteItem(View v,final ItemOrder io){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if(addItemOrders.remove(io)!=true)
                                    removeItemOrders.add(io);
                        currentOrder.getItems().remove(io);
                        showItems();

                        Toast toast= Toast.makeText(getApplicationContext(),"Item deleted.",Toast.LENGTH_SHORT);
                        toast.show();
                        dialogue.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void insertCategories(){
        side.setVisibility(View.GONE);
        categ.setVisibility(View.VISIBLE);

        Category.findCategories();
        final ArrayList<Category> categories=Category.categories;

        final float scale = getResources().getDisplayMetrics().density;
        LinearLayout ll = (LinearLayout) findViewById(R.id.categoryLayout);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,(int)(80*scale),1.0f);
        lp.gravity= Gravity.TOP | Gravity.BOTTOM;
        int i=0;
        int count=0;

        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(newLayout);

        while(categories.size()>i){
            Button b = new Button(this);
            b.getBackground().setColorFilter(ContextCompat.getColor(this,R.color.transparent), PorterDuff.Mode.MULTIPLY);
            b.setText(categories.get(i).getName());
            final int t=i;
            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    fromCategoriesToItems(categories.get(t));
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

    }
    public void showItems(){
        ArrayList<ItemOrder> itemOrders = currentOrder.getItems();
        LinearLayout ll = (LinearLayout)findViewById(R.id.llContents);
        ll.removeAllViews();


        LayoutParams lp = new LayoutParams(0,LayoutParams.WRAP_CONTENT,0.20f);

        LayoutParams lp2 = new LayoutParams(0,LayoutParams.WRAP_CONTENT,0.40f);

        LayoutParams lp3 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp3.gravity = Gravity.CENTER;
        for(int i=0;i<itemOrders.size();i++){
            final ItemOrder tempItem = itemOrders.get(i);
            LinearLayout llnew = new LinearLayout(this);


            TextView txtQty = new TextView(this);
            txtQty.setText("QTY: "+tempItem.getQuantity());
            llnew.addView(txtQty,lp);

            TextView txtName = new TextView(this);
            txtName.setText(tempItem.getName());
            llnew.addView(txtName,lp2);

            TextView txtNotes = new TextView(this);
            txtNotes.setText(tempItem.getNotes());
            llnew.addView(txtNotes,lp2);


            txtName.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemClicked(tempItem);
                }
            });
            txtNotes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemClicked(tempItem);
                }
            });
            txtQty.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemClicked(tempItem);
                }
            });
            ll.addView(llnew,lp3);
        }
    }
}
