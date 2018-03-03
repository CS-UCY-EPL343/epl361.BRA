package com.team3.bra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;


public class ReportView extends Activity {
    String fromStr = "";
    String toStr = "";
    Vector<Vector<Object>> result;
    ArrayList<ItemOrder> itemOrder = new ArrayList<>();
    public void backClicked(View v){
        finish();
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.reports_layout);
    }

    public void ordersReport(View v){
        ArrayList<Order> orders = new ArrayList<>();
        checkInput();
        if(!getOrdes()) {
            return;
        }

        Item.fillAllItems();

        for(Vector<Object> vector:result){
            itemOrder.add(new ItemOrder(vector,true));
        }

        System.out.println(itemOrder.size());






    }


    public void taxReport(View v){
        checkInput();
        if(getOrdes()){

        }
    }

    public void checkInput(){
        fromStr="";
        toStr="";
        String from = ((EditText) findViewById(R.id.fromTime)).getText().toString();
        String to = ((EditText) findViewById(R.id.toTime)).getText().toString();
        String[] fromAr = from.split("/");
        String[] toAr = to.split("/");
        if(fromAr.length==3){
            from = fromAr[1]+'/'+fromAr[0]+'/'+fromAr[2];
        }
        else {
            Toast.makeText(getApplicationContext(), "Wrong From date format", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.fromTime)).setText("");
        }

        if(toAr.length==3){
            to = toAr[1]+'/'+toAr[0]+'/'+toAr[2];
        }
        else {
            Toast.makeText(getApplicationContext(), "Wrong To date format", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.toTime)).setText("");
        }
        System.out.println(from+" "+to);
        Date fromDate=null;
        Date toDate=null;
        try {
            fromDate = new Date(new java.util.Date(from).getTime());
            toDate = new Date(new java.util.Date(to).getTime());

            System.out.println(fromDate + " " + toDate);
            fromStr = fromDate.toString() + " 00:00:00";
            toStr = toDate.toString() + " 23:59:59";
            System.out.println(fromStr + " " + toStr);
        }
        catch (Exception e){
            if(fromDate==null){
                Toast.makeText(getApplicationContext(), "Wrong From date format", Toast.LENGTH_SHORT).show();
                ((EditText) findViewById(R.id.fromTime)).setText("");
            }
            else if(toDate==null){
                Toast.makeText(getApplicationContext(), "Wrong To date format", Toast.LENGTH_SHORT).show();
                ((EditText) findViewById(R.id.toTime)).setText("");
            }
        }

    }
    public boolean getOrdes(){
        result=null;
        if(!fromStr.isEmpty()&&!toStr.isEmpty()){
            String a[] = {fromStr,toStr};
            result = JDBC.callProcedure("REPORTS", a);
        }
        if(result==null){
            return false;
        }
        return true;
    }
}
