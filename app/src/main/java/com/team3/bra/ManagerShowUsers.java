package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

public class ManagerShowUsers extends Activity {
    ArrayAdapter<String> adapter;
    ArrayList<String> listUsers =new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_show_users_layout);

        String arr[] = getResources().getStringArray(R.array.users_array);
        for(int i=0;i<arr.length;i++)
            listUsers.add(arr[i]);

        adapter=new ArrayAdapter<String>(this,
                R.layout.custom_listview_layout,
                listUsers);

        ListView lv=(ListView) findViewById(R.id.listUsers);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ManagerShowUsers.this, ManagerEditUser.class);
                startActivity(intent);
            }
        });
    }

    public void backClicked(View v){
        finish();
    }


}
