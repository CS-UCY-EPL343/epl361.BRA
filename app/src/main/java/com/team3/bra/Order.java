package com.team3.bra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Order extends Activity {
    ScrollView side;
    ScrollView categ;
    Dialogues dialogue;

    public void backClicked(View v){
        finish();
    }
    private static Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);
        Bundle b = getIntent().getExtras();
        if(b != null && b.getInt("new")==1){
            String s=b.getString("Table");
            TextView txtOrderNum=(TextView) findViewById(R.id.txtOrderNum);
            txtOrderNum.setText(s);
            LinearLayout llContents=(LinearLayout)findViewById(R.id.llContents);
            llContents.setVisibility(View.INVISIBLE);
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
    public void showItemDialogue(View view) {
        dialogue=Dialogues.dialogueFactory(this,Order.this,R.layout.order_item_add_dialogue);
        TextView t= (TextView) dialogue.getView().findViewById(R.id.txtItem);
        t.setText(((Button) view).getText());
    }
    public void addItem(View v){
        View dialogueView=dialogue.getView();
        EditText e= (EditText)dialogueView.findViewById(R.id.txtComments);
        Spinner s=((Spinner)  dialogueView.findViewById(R.id.quantity));
        TextView t= (TextView) dialogue.getView().findViewById(R.id.txtItem);
        int quantity= (int) Integer.parseInt((String) s.getSelectedItem());
        String comments= e.getText().toString();
        Toast toast= Toast.makeText(getApplicationContext(),quantity+" "+t.getText().toString()+" "+comments,Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }

    public void editItem(View v){
        View myView=dialogue.getView();
        EditText e= (EditText)myView.findViewById(R.id.txtComments);
        TextView t= (TextView) myView.findViewById(R.id.txtItem);
        TextView tquantity= (TextView) myView.findViewById(R.id.txtQuantity);
        int quantity= (int) Integer.parseInt(tquantity.getText().toString());
        String comments= e.getText().toString();
        Toast toast= Toast.makeText(getApplicationContext(),quantity+" "+t.getText().toString()+" "+comments,Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }

    public void cancelItem(View v){dialogue.dismiss();}


    public void itemClicked(View v){
        dialogue=Dialogues.dialogueFactory(this,Order.this,R.layout.order_item_edit_dialogue);
        View myView=dialogue.getView();
        ((TextView)myView.findViewById(R.id.txtItem)).setText("Keo Beer");
        ((TextView)myView.findViewById(R.id.txtDescr)).setText("Second Best Beer");
    }

    public void deleteItem(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"Item deleted.",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }

}
