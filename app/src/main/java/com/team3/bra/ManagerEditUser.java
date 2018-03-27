package com.team3.bra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * The manager edit user screen
 */

public class ManagerEditUser extends Activity {

    int idInArray=-1;
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
            idInArray = b.getInt("key");
            String temp = b.getString("info");
            String username=temp.split(" ")[0];
            String category=temp.split(" ")[1];
            String name=temp.split(" ")[2];
            String surname=temp.split(" ")[3];
            String password=temp.split(" ")[4];
            password = "";
            ((EditText)findViewById(R.id.txt_editName)).setText(name);
            ((EditText)findViewById(R.id.txt_editSurname)).setText(surname);
            ((EditText)findViewById(R.id.txt_editUsername)).setText(username);
            ((EditText)findViewById(R.id.txt_editPassword)).setText(password);

            if(category.equals("2")){
                ((Spinner)findViewById(R.id.spnr_roleList)).setSelection(1);
            }
            if(category.equals("3")){
                ((Spinner)findViewById(R.id.spnr_roleList)).setSelection(2);
            }
            if(category.equals("1")){
                ((Spinner)findViewById(R.id.spnr_roleList)).setSelection(3);
            }
        }

    }
    /**
     * Return to the show all users screen
     * @param v the view that clicked the button
     */
    public void backClicked(View v){
        finish();
    }
    /**
     * Delete the selected user after confirmation
     * @param v the view that clicked the button
     */
    public void deleteClicked(View v){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        User.deleteUser(User.users.get(idInArray).getId());
                        Toast toast= Toast.makeText(getApplicationContext(),"User deleted",Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this user?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
    /**
     * Save the changes to the selected user after checking for all input correctness
     * @param v the view that clicked the button
     */
    public void saveClicked(View v){
        int id = idInArray;
        if(idInArray!=-1){
            id=User.users.get(idInArray).getId();
        }
        System.out.println(idInArray+" "+id);
        String name = ((EditText)findViewById(R.id.txt_editName)).getText().toString();
        if(name.equals("")){
            Toast.makeText(getApplicationContext(), "Please add name", Toast.LENGTH_SHORT).show();
            return;
        }
        String lastname = ((EditText)findViewById(R.id.txt_editSurname)).getText().toString();
        if(lastname.equals("")){
            Toast.makeText(getApplicationContext(), "Please add surname", Toast.LENGTH_SHORT).show();
            return;
        }
        String username =((EditText)findViewById(R.id.txt_editUsername)).getText().toString();
        if(username.equals("")){
            Toast.makeText(getApplicationContext(), "Please add username", Toast.LENGTH_SHORT).show();
            return;
        }
        String password =((EditText)findViewById(R.id.txt_editPassword)).getText().toString();
        if(password.equals("") && id==-1){
            Toast toast = Toast.makeText(getApplicationContext(), "Please add password", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        else if(password.equals("") && idInArray!=-1){
            password=User.users.get(idInArray).getPassword();
        }
        else{
            try {
                password = EncryptionAlgorithm.SHA1(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String position="";
        int temp=((Spinner)findViewById(R.id.spnr_roleList)).getSelectedItemPosition();
        if(temp==1){
            position="2";
        }
        else if(temp==2){
            position="3";
        }
        else if(temp==3){
            position="1";
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select role", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        User.saveUser(id,lastname,username,password,name,position);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "User saved", duration);
        toast.show();
        finish();
    }
}