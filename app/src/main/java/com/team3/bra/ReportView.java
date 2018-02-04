package com.team3.bra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class ReportView extends Activity {

    public void backClicked(View v){
        finish();
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.reports_layout);
    }

}
