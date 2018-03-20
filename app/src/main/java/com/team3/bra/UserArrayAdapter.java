package com.team3.bra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by GamerMakrides on 20/03/2018.
 */

public class UserArrayAdapter extends ArrayAdapter<User> {
    public UserArrayAdapter(@NonNull Context context, @NonNull List<User> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_user_layout, parent, false);

        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView userType = (TextView) convertView.findViewById(R.id.userType);

        User u = super.getItem(position);
        userName.setText(u.getUsername());
        String pos =u.getPosition();
        if(pos.compareTo("1")==0)
            pos="Manager";
        else  if(pos.compareTo("2")==0)
            pos="Waiter";
        else  if(pos.compareTo("3")==0)
            pos="Chef";
        else {
            pos = "";
            userType.setVisibility(View.GONE);
        }
        userType.setText(pos);
        return convertView;
    }
}
