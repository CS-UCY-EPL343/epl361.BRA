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
 * Alternation of the ArrayAdapter<Order> class. By default, the array adapter
 * creates a view by calling toString() on each data object in the collection
 * you provide, and places the result in a TextView. You may also customize what
 * type of view is used for the data object in the collection. In order to be
 * customized to a specific type of view that is used for Orders, the function
 * getView(int, View, ViewGroup) is overriden and a new view resource is
 * inflated. This view (uses the listview_order_layout) and has different color
 * boxes on each order depending on its state.
 */
public class OrderArrayAdapter extends ArrayAdapter<Order> {
    /**
     * The constructor of the OrderArrayAdapter.
     *
     * @param context
     * @param objects
     */
    public OrderArrayAdapter(@NonNull Context context, @NonNull List<Order> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_order_layout, parent, false);

        TextView orderName = (TextView) convertView.findViewById(R.id.orderName);
        View shape = (View) convertView.findViewById(R.id.orderCircle);

        Order o = super.getItem(position);
        orderName.setText(o.toString());
        switch (o.getState()) {
            case -1:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                break;
            case 0:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.stateZero));
                break;
            case 1:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.stateOne));
                break;
            case 2:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.stateTwo));
                break;
            case 3:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.stateThree));
                break;
            case 4:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.stateFour));
                break;
            case 5:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.stateFive));
                break;
            case 6:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.stateSix));
                break;
            default:
                shape.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                break;
        }
        return convertView;
    }
}
