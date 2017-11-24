package com.team3.bra;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class Cook extends AppCompatActivity {
    Dialogues dialogue;

    TextView descText;
    ImageButton plus, minus;

    TextView descText2;
    ImageButton plus2, minus2;

    TextView descText3;
    ImageButton plus3, minus3;

    public void backClicked(View v){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_layout);

        descText = (TextView) findViewById(R.id.description_text6);
        plus = (ImageButton) findViewById(R.id.plus);
        minus = (ImageButton) findViewById(R.id.minus);

        descText2 = (TextView) findViewById(R.id.description_text);
        plus2 = (ImageButton) findViewById(R.id.plus2);
        minus2 = (ImageButton) findViewById(R.id.minus2);

        descText3 = (TextView) findViewById(R.id.description_text4);
        plus3 = (ImageButton) findViewById(R.id.plus3);
        minus3 = (ImageButton) findViewById(R.id.minus3);



        plus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                plus.setVisibility(View.GONE);
                minus.setVisibility(View.VISIBLE);
                descText.setMaxLines(Integer.MAX_VALUE);

            }
        });

        plus2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                plus2.setVisibility(View.GONE);
                minus2.setVisibility(View.VISIBLE);
                descText2.setMaxLines(Integer.MAX_VALUE);

            }
        });

        plus3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                plus3.setVisibility(View.GONE);
                minus3.setVisibility(View.VISIBLE);
                descText3.setMaxLines(Integer.MAX_VALUE);

            }
        });

        minus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                minus.setVisibility(View.GONE);
                plus.setVisibility(View.VISIBLE);
                descText.setMaxLines(5);

            }
        });

        minus2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                minus2.setVisibility(View.GONE);
                plus2.setVisibility(View.VISIBLE);
                descText2.setMaxLines(5);

            }
        });

        minus3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                minus3.setVisibility(View.GONE);
                plus3.setVisibility(View.VISIBLE);
                descText3.setMaxLines(5);

            }
        });


    }
    public void markOrderDialogue(View v) {
        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_mark_order_dialogue);
    }
    public void sendOrderDialogue(View v) {
        dialogue=Dialogues.dialogueFactory(this,Cook.this,R.layout.cook_send_order_dialogue);
    }


    public void markOrder(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"Order marked.",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }
    public void sendOrder(View v){
        Toast toast= Toast.makeText(getApplicationContext(),"Order sent.",Toast.LENGTH_SHORT);
        toast.show();
        dialogue.dismiss();
    }
    public void cancelCook(View v){
        dialogue.dismiss();
    }

}

