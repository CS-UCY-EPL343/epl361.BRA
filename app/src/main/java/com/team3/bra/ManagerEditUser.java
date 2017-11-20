package com.team3.bra;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManagerEditUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_users_edit_layout);
        Bundle b = getIntent().getExtras();
        if(b != null && b.getInt("key")==0){
            ((Button)findViewById(R.id.btn_Delete)).setVisibility(View.GONE);
        }
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spnr_roleList);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(" ");
        categories.add("Waiter");
        categories.add("Chef");
        categories.add("Manager");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        if(b != null && b.getInt("key")!=0){
            String temp = b.getString("info");
            String username=temp.split(" ")[0];
            String category=temp.split(" ")[1];
            String name=temp.split(" ")[2];
            String surname=temp.split(" ")[3];
            String password=temp.split(" ")[4];
            ((EditText)findViewById(R.id.txt_editName)).setText(name);
            ((EditText)findViewById(R.id.txt_editSurname)).setText(surname);
            ((EditText)findViewById(R.id.txt_editUsername)).setText(username);
            ((EditText)findViewById(R.id.txt_editPassword)).setText(password);
            if(category.equals("Waiter")){
                ((Spinner)findViewById(R.id.spnr_roleList)).setSelection(1);
            }
            if(category.equals("Chef")){
                ((Spinner)findViewById(R.id.spnr_roleList)).setSelection(2);
            }
            if(category.equals("Manager")){
                ((Spinner)findViewById(R.id.spnr_roleList)).setSelection(3);
            }
        }

    }

    public void backClicked(View v){
        finish();
    }
    public void deleteClicked(View v){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "User deleted", duration);
        toast.show();
        finish();
    }
    public void saveClicked(View v){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "User saved", duration);
        toast.show();
        finish();
    }
}
