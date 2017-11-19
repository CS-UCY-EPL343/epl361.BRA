package com.team3.bra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),WindowManager.LayoutParams.WRAP_CONTENT);
        Bundle b = getIntent().getExtras();
        if(b != null && b.getInt("add")==1){
            ((Button)findViewById(R.id.btnaddItemDelete)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.btnItemAdd)).setText("ADD");
            ((EditText)findViewById(R.id.txtItemNotes)).setText("");
            ((EditText)findViewById(R.id.txtItemQty)).setText("");
        }
        else{
            ((TextView)findViewById(R.id.itemTitle)).setText("Keo Beer");
        }
    }

    public void newItemCancel (View v){
        finish();
    }

    public void newItemDelete (View v){
        finish();
    }

    public void newItemAdd (View v){
        finish();
    }
}
