package com.team3.bra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ManagerEditUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_edit_user_layout);


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

    }

    public void backClicked(View v){
        finish();
    }
    public void deleteClicked(View v){
        //todo
        finish();
    }
    public void saveClicked(View v){
        //todo
        finish();
    }
}
