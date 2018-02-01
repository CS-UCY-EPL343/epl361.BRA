package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }
    public void onLoginClick(View v){
        EditText txtUser = (EditText) findViewById(R.id.txtUser);
        String username = txtUser.getText().toString();

        if (username.equals("3")) {
           JDBC.establishConnection();
            Intent intent = new Intent(this, Manager.class);
            startActivity(intent);
        }
        if (username.equals("1")) {
            JDBC.establishConnection();
            Intent intent = new Intent(this, Waiter.class);
            startActivity(intent);
        }
        if (username.equals("2")) {
            JDBC.establishConnection();
            Intent intent = new Intent(this, Cook.class);
            startActivity(intent);
        }

    }
    public void exitClicked(View v){
        finish();
    }
}
