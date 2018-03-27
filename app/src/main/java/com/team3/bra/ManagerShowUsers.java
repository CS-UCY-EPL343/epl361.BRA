package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
/**
 * Show all the users to the manager screen
 *
 */
public class ManagerShowUsers extends Activity {
    UserArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_users_show_layout);
        loadUsers();
    }
    /**
     * Load all the users from the database to the screen of the manager
     */
    public void loadUsers(){
        User.findUsers();
        adapter=new UserArrayAdapter(this, User.getUsers());

        ListView lv=(ListView) findViewById(R.id.listUsers);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ManagerShowUsers.this, ManagerEditUser.class);
                Bundle b = new Bundle();
                b.putInt("key", (int)id);
                b.putString("info", User.getUserById((int)id));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        loadUsers();

    }
    /**
     * Return to the main menu of the manager
     * @param v the view that clicked the button
     */
    public void backClicked(View v){
        finish();
    }


}