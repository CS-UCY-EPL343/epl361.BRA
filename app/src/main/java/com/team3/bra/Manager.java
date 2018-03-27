package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * The main screen of the manager
 *
 */
public class Manager extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_layout);
    }
    /**
     * Exit from the screen and return to the login screen
     * @param v the view that clicked the button
     */
    public void logoutClicked(View v){
        finish();
    }
    /**
     * Opens the waiter interface
     * @param v the view that clicked the button
     */
    public void waiterOptionsClicked(View v){
        Intent intent = new Intent(this, Waiter.class);
        startActivity(intent);
    }
    /**
     * Opens the report screen
     * @param v the view that clicked the button
     */
    public void reportsClicked(View v){
        Intent intent = new Intent(this, ReportView.class);
        startActivity(intent);
    }
    /**
     * Opens the edit user screen
     * @param v the view that clicked the button
     */
    public void editUsersClicked(View v){
        Intent intent = new Intent(this, ManagerShowUsers.class);
        startActivity(intent);
    }
    /**
     * Opens the edit menu screen
     * @param v the view that clicked the button
     */
    public void editMenuClicked(View v){
        Intent intent = new Intent(this, ManagerMenu.class);
        startActivity(intent);
    }
}