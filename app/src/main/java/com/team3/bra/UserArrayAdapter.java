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
 * Alternation of the ArrayAdapter<User> class. By default, the array adapter
 * creates a view by calling toString() on each data object in the collection
 * you provide, and places the result in a TextView. You may also customize what
 * type of view is used for the data object in the collection. In order to be
 * customized to a specific type of view that is used for Users, the function
 * getView(int, View, ViewGroup) is overriden and a new view resource is
 * inflated. This view (uses the listview_user_layout) and has two textboxes,
 * one for the user name and the other for the user role in the system.
 */
public class UserArrayAdapter extends ArrayAdapter<User> {
    /**
     * The constructor of the UserArrayAdapter.
     *
     * @param context
     * @param objects
     */
    public UserArrayAdapter(@NonNull Context context, @NonNull List<User> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_user_layout, parent, false);

        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView userType = (TextView) convertView.findViewById(R.id.userType);

        User u = super.getItem(position);
        userName.setText(u.getUsername());
        String pos = u.getPosition();
        userType.setVisibility(View.VISIBLE);
        if (pos.compareTo("1") == 0)
            pos = "Manager";
        else if (pos.compareTo("2") == 0)
            pos = "Waiter";
        else if (pos.compareTo("3") == 0)
            pos = "Chef";
        else if (pos.compareTo("-1") == 0) {
            pos = "";
            userType.setVisibility(View.GONE);
        }
        userType.setText(pos);
        return convertView;
    }
}
