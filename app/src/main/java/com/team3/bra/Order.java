package com.team3.bra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.order_layout);
        side = (ScrollView) findViewById(R.id.scrollSide);
        categ = (ScrollView) findViewById(R.id.scrollCat);
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
        dialogue=Dialogues.dialogueFactory(this,Order.this,R.layout.order_item_dialogue);
        TextView t= (TextView) dialogue.getView().findViewById(R.id.txtItem);
        t.setText("FETTA CHEESE");
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
    public void cancelItem(View v){dialogue.dismiss();}
}
