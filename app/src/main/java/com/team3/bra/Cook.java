package com.team3.bra;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class Cook extends AppCompatActivity {
    Dialogues dialogue;



    public void backClicked(View v){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_layout);

        loadOrders();

    }
    View temp;
    View temp2;
    Order temp3;
    public void markOrderDialogue(View v,View v2,Order v3) {
        temp=v;
        temp2=v2;
        temp3=v3;
        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_mark_order_dialogue);

    }

    public void sendOrderDialogue(View v,View v2,Order v3) {
        temp=v;
        temp2=v2;
        temp3=v3;
        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_send_order_dialogue);
    }


    public void markOrder(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"OrderView marked.",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
        temp.setBackgroundColor(Color.parseColor("#90dd00"));
        temp2.setBackgroundColor(Color.parseColor("#90dd00"));
        temp.setTag("clicked");
        temp3.setState(1);

    }
    public void sendOrder(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"OrderView sent.",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
        ((LinearLayout)temp.getParent()).removeView(temp);
        ((LinearLayout)temp2.getParent()).removeView(temp2);
        temp3.setState(2);

    }
    public void cancelCook(View v){
        dialogue.dismiss();
    }

    public void loadOrders(){
        Order.findCookOrders();
        final ArrayList<Order> orders = new ArrayList<>();
        for (int i=0; i<Order.cookOrders.size();i++){

            orders.add(Order.cookOrders.get(i));
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
            final TextView tvorder=new TextView(this);
            String inport="";
            for (int m=0; m<orders.get(i).getItems().size();m++) {
               inport= inport+orders.get(i).getItems().get(m).getQuantity()+ orders.get(i).getItems().get(m).getName()+orders.get(i).getItems().get(m).getNotes()+"\n";
            }
            tvorder.setText(inport);

            if (orders.get(i).getState()==1){
                tvorder.setBackgroundColor(Color.parseColor("#90dd00"));
                tv.setBackgroundColor(Color.parseColor("#90dd00"));
                tvorder.setTag("clicked");
            }
            else{
                tv.setBackgroundColor(Color.parseColor("#faff31"));
                tvorder.setBackgroundColor(Color.parseColor("#faff31"));
            }

            tvorder.setVisibility(View.GONE);
            final Order orderChangeState = orders.get(i);
            /*
            ShapeDrawable sd = new ShapeDrawable();

            // Specify the shape of ShapeDrawable
            sd.setShape(new RectShape());

            // Specify the border color of shape
            sd.getPaint().setColor(Color.parseColor("#000000"));

            // Set the border width
            sd.getPaint().setStrokeWidth(5f);

            // Specify the style is a Stroke
            sd.getPaint().setStyle(Paint.Style.STROKE);

            // Finally, add the drawable background to TextView
            tv.setBackground(sd);
            tvorder.setBackground(sd);
            */


            tv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                   if( tvorder.getVisibility()==View.GONE){
                       tvorder.setVisibility(View.VISIBLE);
                   }
                   else{
                       tvorder.setVisibility(View.GONE);
                   }
                }
            });
            tvorder.setOnClickListener(new View.OnClickListener(){
                boolean flag=false;
                @Override
                public void onClick(View v){
                    if(v.getTag()!="clicked")
                        markOrderDialogue(v,tv,orderChangeState);
                    else{
                        sendOrderDialogue(v,tv,orderChangeState);
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


    }

}

