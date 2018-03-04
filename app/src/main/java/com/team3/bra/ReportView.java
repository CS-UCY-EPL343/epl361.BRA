package com.team3.bra;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class ReportView extends Activity {
    private class reportOrder implements Comparable<reportOrder>{
        String name;
        String quantity;
        String profit;
        String category;
        public reportOrder(String name,String qty,String profit,String category){
            this.name=name;
            this.quantity=qty;
            this.profit = profit;
            this.category = category;
        }

        @Override
        public int compareTo(@NonNull reportOrder o) {
            return this.category.compareTo(o.category);
        }
    }
    String fromStr = "";
    String toStr = "";
    Vector<Vector<Object>> result;
    public void backClicked(View v){
        finish();
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.reports_layout);
    }

    public void ordersReport(View v){
        checkInput();
        if(!getOrdes()) {
            return;
        }

        Item.fillAllItems();
        HashMap<Integer, List<ItemOrder>> hashMap = new HashMap<Integer, List<ItemOrder>>();
        final ArrayList<reportOrder> reports = new ArrayList<>();

        for(Vector<Object> vector:result){
            ItemOrder i = new ItemOrder(vector,true);

            if (!hashMap.containsKey(i.getItemID())) {
                List<ItemOrder> list = new ArrayList<ItemOrder>();
                list.add(i);

                hashMap.put(i.getItemID(), list);
            } else {
                hashMap.get(i.getItemID()).add(i);
            }
        }

        for(Integer i : hashMap.keySet()){
            int quantity=0;
            double profit=0;
            for (ItemOrder o :hashMap.get(i)){
                quantity+=o.getQuantity();
                profit+=o.getCurrentPrice();
            }
            ItemOrder temp =hashMap.get(i).get(0);
            reportOrder r = new reportOrder(temp.getName(),quantity+"",profit+"",temp.getCategory());
            reports.add(r);
        }
        Collections.sort(reports);



        File folder = new File(Environment.getExternalStorageDirectory()
                + "/reports");

        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();

        System.out.println("" + var);


        final String filename = folder.toString() + "/" + "orders-"+fromStr+"-"+toStr+".csv";

        // show waiting screen
        CharSequence contentTitle = getString(R.string.app_name);
        final ProgressDialog progDailog = ProgressDialog.show(
                ReportView.this, contentTitle, "Please wait...",
                true);//please wait
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                Toast.makeText(getApplicationContext(), "Csv saved", Toast.LENGTH_SHORT).show();

            }
        };

        new Thread() {
            public void run() {
                try {

                    FileWriter fw = new FileWriter(filename);

                    fw.append("BROADWAY RESTAURANT,");
                    fw.append('\n');

                    fw.append("VAT XXXXXX TAX XXXXXX,");
                    fw.append('\n');

                    java.util.Date c = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String today = df.format(c);

                    fw.append("DATE CREATED: "+today+",");
                    fw.append('\n');

                    String period = "PERIOD "+fromStr.split("[:space]")[0] +" - "+toStr.split("[:space]")[0]+",";
                    period = "PERIOD "+fromStr.substring(0,fromStr.indexOf(' '))+" - "+toStr.substring(0,toStr.indexOf(' '))+",";

                    fw.append(period);
                    fw.append('\n');
                    fw.append('\n');
                    fw.append("REPORT");
                    fw.append('\n');
                    fw.append('\n');


                    fw.append("CATEGORY");
                    fw.append(',');
                    fw.append("ITEM NAME,,");
                    fw.append(',');

                    fw.append("QTY(SOLD)");
                    fw.append(',');

                    fw.append("PROFIT");
                    fw.append(',');


                    fw.append('\n');

                    fw.append('\n');

                    String oldCategory="";
                    for (int i = 0; i < reports.size(); i++) {
                        reportOrder r = reports.get(i);
                        if(oldCategory.compareTo(r.category)!=0){
                            if(oldCategory.compareTo("")!=0)
                                fw.append("\n");
                            fw.append(r.category+",\n");
                            oldCategory=r.category;

                        }
                        fw.append(',');
                        fw.append(r.name);
                        fw.append(',');
                        fw.append(',');
                        fw.append(',');

                        fw.append(r.quantity);
                        fw.append(',');

                        fw.append(r.profit);
                        fw.append(',');

                        fw.append('\n');

                    }
                    fw.flush();
                    fw.close();
                }
                catch (Exception e) {
                    System.out.println(e.toString());
                }
                handler.sendEmptyMessage(0);
                progDailog.dismiss();
            }
        }.start();






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
