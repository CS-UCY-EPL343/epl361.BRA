package com.team3.bra;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Cook extends AppCompatActivity  {
    static Intent mServiceIntent=null;
    static Cook cookClass=null;
    static Cook oldCookClass=null;

    Dialogues dialogue;

    ArrayList <Integer> visibilityOrder = new ArrayList<>();
     ArrayList<TextView> tvOrdersList;

    public void backClicked(View v){
        oldCookClass=cookClass;
        cookClass=null;

        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_go_back);

    }

    public void goBack(View v){
        cookClass=null;
        dialogue.dismiss();
        finish();
    }

    public void noBack(View v){
        cookClass=oldCookClass;
        dialogue.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_layout);
        loadOrders();
        cookClass=this;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cookClass=null;
        oldCookClass=null;
    }
    View temp;
    View temp2;
    Order temp3;
    public void markOrderDialogue(View v,View v2,Order v3) {
        oldCookClass=cookClass;
        cookClass=null;
        temp=v;
        temp2=v2;
        temp3=v3;
        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_mark_order_dialogue);

    }

    public void sendOrderDialogue(View v,View v2,Order v3) {
        oldCookClass=cookClass;
        cookClass=null;
        temp=v;
        temp2=v2;
        temp3=v3;
        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_send_order_dialogue);
    }


    public void markOrder(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"OrderView marked.",Toast.LENGTH_SHORT);
        toast.show();

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFF90dd00);
        gd.setCornerRadius(0);
        gd.setStroke(5, 0xFFc5c7c4);


        dialogue.dismiss();
        temp.setBackgroundColor(Color.parseColor("#90dd00"));
        temp.setBackground(gd);
        temp2.setBackgroundColor(Color.parseColor("#90dd00"));
        temp2.setBackground(gd);
        temp.setTag("clicked");
        temp3.setState(1);
        cookClass=oldCookClass;
    }
    public void sendOrder(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"OrderView sent.",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
        ((LinearLayout)temp.getParent()).removeView(temp);
        ((LinearLayout)temp2.getParent()).removeView(temp2);
        temp3.setState(2);

        visibilityOrder.remove(tvOrdersList.indexOf(temp));
        tvOrdersList.remove(temp);
        cookClass=oldCookClass;
    }
    public void cancelCook(View v){
        dialogue.dismiss();
        cookClass=oldCookClass;
    }

    public void loadOrders(){
        oldCookClass=cookClass;
        cookClass=null;

        Order.findCookOrders();
        final ArrayList<Order> orders = new ArrayList<>();

        tvOrdersList = new ArrayList<>();


        for (int i=0; i<Order.getCookOrders().size();i++){

            orders.add(Order.getCookOrders().get(i));
            orders.get(i).fillOrder();
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity= Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL ;
        int i=0;
        int count=0;


        LinearLayout column1 = (LinearLayout) findViewById(R.id.column1);
        LinearLayout column2 = (LinearLayout) findViewById(R.id.column2);
        LinearLayout column3 = (LinearLayout) findViewById(R.id.column3);
        column1.removeAllViews();
        column2.removeAllViews();
        column3.removeAllViews();

        while(orders.size()>i){

            final TextView tv=new TextView(this);
            tv.setText("Table "+orders.get(i).getTable());
            tv.setGravity(Gravity.CENTER);
            tv.setHeight(50);
            tv.setTextSize(20);

            final TextView tvorder=new TextView(this);

            String inport="";
            for (int m=0; m<orders.get(i).getItems().size();m++) {
                if (orders.get(i).getItems().get(m).getNotes().isEmpty()) {
                    inport = inport + '\t' + orders.get(i).getItems().get(m).getQuantity() + " " + orders.get(i).getItems().get(m).getName() + "\n";
                } else {
                    inport = inport + '\t' + orders.get(i).getItems().get(m).getQuantity() + " " + orders.get(i).getItems().get(m).getName() + '\n' + '\t' + '\t' + orders.get(i).getItems().get(m).getNotes() + "\n";

                }
            }
            tvorder.setText('\n'+inport);
            tvorder.setTextSize(16);
            tvOrdersList.add(tvorder);
            if (tvOrdersList.size()> visibilityOrder.size()) {
                visibilityOrder.add(0);
            }


            GradientDrawable gd = new GradientDrawable();
             // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(0);
            gd.setStroke(5, 0xFFc5c7c4);



            if (orders.get(i).getState()==1){

                tvorder.setBackgroundColor(Color.parseColor("#90dd00"));
                tv.setBackgroundColor(Color.parseColor("#90dd00"));
                tvorder.setTag("clicked");
                gd.setColor(0xFF90dd00);

                tv.setBackground(gd);
                tvorder.setBackground(gd);


            }
            else{

                tv.setBackgroundColor(Color.parseColor("#faff31"));
                tvorder.setBackgroundColor(Color.parseColor("#faff31"));
                gd.setColor(0xFFfaff31);
                tv.setBackground(gd);
                tvorder.setBackground(gd);



                // Assign the created border to EditText widget

            }

            if (visibilityOrder.get(tvOrdersList.indexOf(tvorder))==1){
                tvorder.setVisibility(View.VISIBLE);
            }
            else {
                tvorder.setVisibility(View.GONE);
            }
            final Order orderChangeState = orders.get(i);



            tv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                   if( tvorder.getVisibility()==View.GONE){
                       tvorder.setVisibility(View.VISIBLE);
                       visibilityOrder.add(tvOrdersList.indexOf(tvorder),1);
                       visibilityOrder.remove(tvOrdersList.indexOf(tvorder)+1);
                   }
                   else{
                       tvorder.setVisibility(View.GONE);
                       visibilityOrder.add(tvOrdersList.indexOf(tvorder),0);
                       visibilityOrder.remove(tvOrdersList.indexOf(tvorder)+1);
                   }
                }
            });
            tvorder.setOnClickListener(new View.OnClickListener(){
                boolean flag=false;
                @Override
                public void onClick(View v){
                    if(tvorder.getTag()!="clicked")
                        markOrderDialogue(tvorder,tv,orderChangeState);
                    else{
                        sendOrderDialogue(tvorder,tv,orderChangeState);
                    }
                    flag=true;
                }
            });
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(tv,lp);
            ll.addView(tvorder,lp);
            if(count == 0){
                column1.addView(ll,lp);
            }
            else if (count==1){

                column2.addView(ll,lp);
            }
            else{
                column3.addView(ll,lp);
            }
            i++;
            count++;
            if (count >= 3){
                count=0;
            }
        }
        cookClass=oldCookClass;
    }

}

