package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        JDBC.establishConnection();

    }
    public void onLoginClick(View v){
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
    public void exitClicked(View v){
        finish();
    }
}
