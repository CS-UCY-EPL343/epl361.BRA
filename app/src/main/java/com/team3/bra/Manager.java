package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Manager extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.manager_layout);
    }

    public void logoutClicked(View v){
            finish();
    }

    public void waiterOptionsClicked(View v){
        Intent intent = new Intent(this, Waiter.class);
        startActivity(intent);
    }

    public void reportsClicked(View v){
        Intent intent = new Intent(this, Report.class);
        startActivity(intent);
    }

    public void editUsersClicked(View v){
        Intent intent = new Intent(this, ManagerShowUsers.class);
        startActivity(intent);
    }

    public void editMenuClicked(View v){
        Intent intent = new Intent(this, ManagerMenu.class);
        startActivity(intent);
    }
}
