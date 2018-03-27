package com.team3.bra;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;
/**
 * Login screen of the application and main thread of reloading waiter,chef and printing receipts
 *
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        JDBC.establishConnection();
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true) {
                    try {
                        synchronized (this) {
                            wait(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Cook.cookClass != null){
                                        Cook.cookClass.loadOrders();
                                    }

                                    if (Waiter.waiterClass != null){
                                        Waiter.waiterClass.refresh();
                                    }
                                }
                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };
        };
        thread.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
    }
    /**
     * Checks if the user with the password exists and log in them to the correct screen
     * 1. Manager
     * 2. Waiter
     * 3. Chef
     * @param v the view that clicked the button
     */
    public void onLoginClick(View v){
        if(JDBC.isConnected()!=true)
            if(JDBC.establishConnection()!=true) {
                Toast toast = Toast.makeText(getApplicationContext(), "No connection to Database.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        EditText txtUser = (EditText) findViewById(R.id.txtUser);
        String username = txtUser.getText().toString();
        EditText txtPass = (EditText) findViewById(R.id.txtPass);
        String password = txtPass.getText().toString();
        try {
            password=EncryptionAlgorithm.SHA1(password);
        }catch(Exception E){

        }

        String a[] = {username,password};
        Vector<Vector<Object>> vec = JDBC.callProcedure("LOGIN", a);


        if (username.equals("1") ||(vec.size()!=0 && vec.get(0).get(2).equals("1"))) {
            Intent intent = new Intent(this, Manager.class);
            startActivity(intent);
            return;
        }
        if (username.equals("2") ||(vec.size()!=0 && vec.get(0).get(2).equals("2"))) {
            Intent intent = new Intent(this, Waiter.class);
            startActivity(intent);
            return;
        }
        if (username.equals("3") ||(vec.size()!=0 && vec.get(0).get(2).equals("3"))) {
            Intent intent = new Intent(this, Cook.class);
            startActivity(intent);
            return;
        }
        // User c = new User(vec.get(3));

        //System.out.println(password);
        if (vec.size()==0){
            Toast toast= Toast.makeText(getApplicationContext(),"Wrong UserName or Password!",Toast.LENGTH_SHORT);
            toast.show();
            txtUser.setText("");
            txtPass.setText("");
            return;
        }


    }
    /**
     * Exit from the program
     * @param v the view that clicked the button
     */
    public void exitClicked(View v){
        finish();
    }
}