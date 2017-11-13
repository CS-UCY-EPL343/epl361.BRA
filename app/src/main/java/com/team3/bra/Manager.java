package com.team3.bra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mefty on 13/11/2017.
 */

public class Manager extends Activity {

    Button logout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.manager);
     logout=(Button)findViewById(R.id.btnlogout);
    }
    public void logoutClicked(View v){
            setContentView(R.layout.login);
    }

}
