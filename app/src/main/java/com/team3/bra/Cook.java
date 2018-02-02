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
    public void markOrderDialogue(View v,View v2) {
        temp=v;
        temp2=v2;
        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_mark_order_dialogue);
    }
    public void sendOrderDialogue(View v,View v2) {
        temp=v;
        temp2=v2;
        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_send_order_dialogue);
    }


    public void markOrder(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"Order marked.",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
        temp.setBackgroundColor(Color.parseColor("#90dd00"));
        temp2.setBackgroundColor(Color.parseColor("#90dd00"));
        temp.setTag("clicked");

    }
    public void sendOrder(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"Order sent.",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
        ((LinearLayout)temp.getParent()).removeView(temp);
        ((LinearLayout)temp2.getParent()).removeView(temp2);

    }
    public void cancelCook(View v){
        dialogue.dismiss();
    }

    public void loadOrders(){

        ArrayList<String> orders = new ArrayList<>();
        orders.add("orders1\norders1\norders1\norders1\norders1\n");
        orders.add("orders2");
        orders.add("orders3");
        orders.add("orders4");
        orders.add("orders5");
        orders.add("orders6");
        orders.add("orders7");

        ArrayList<String> tables = new ArrayList<>();
        tables.add("tables1");
        tables.add("tables2");
        tables.add("tables3");
        tables.add("tables4");
        tables.add("tables5");
        tables.add("tables6");
        tables.add("tables7");


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
            tv.setText(tables.get(i));
            final TextView tvorder=new TextView(this);
            tvorder.setText(orders.get(i));
            tvorder.setVisibility(View.GONE);
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
            tv.setBackgroundColor(Color.parseColor("#faff31"));
            tvorder.setBackgroundColor(Color.parseColor("#faff31"));

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
                        markOrderDialogue(v,tv);
                    else{
                        sendOrderDialogue(v,tv);
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

