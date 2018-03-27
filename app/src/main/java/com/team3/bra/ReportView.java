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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * The screen that is responsible to generate reports
 *
 */
public class ReportView extends Activity {

    /**
     * Private auxiliary class
     *
     */
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

    /**
     * Private auxiliary class
     *
     */
    private class reportTax implements Comparable<reportTax>{
        Double sales;
        float vat;
        public reportTax(Double sales,float vat){
            this.sales=sales;
            this.vat=vat;
        }

        public String GetVatString(){
            return  new DecimalFormat("#.##").format(this.vat);
        }

        @Override
        public int compareTo(@NonNull reportTax o) {
            return this.GetVatString().compareTo(o.GetVatString());
        }
    }
    String fromStr = "";
    String toStr = "";
    Vector<Vector<Object>> result;
    /**
     * Return to the main menu of the manager
     * @param v the view that clicked the button
     */
    public void backClicked(View v){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_layout);
    }

    /**
     * Create the report about the statistic's of the orders
     * @param v the view that clicked the button
     */
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
                    myCsvCreator csv = new myCsvCreator();
                    FileWriter fw = new FileWriter(filename);

                    csv.addValue("BROADWAY RESTAURANT");
                    csv.changeLines(1);

                    csv.addValue("VAT XXXXXX TAX XXXXXX");
                    csv.changeLines(1);

                    java.util.Date c = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String today = df.format(c);

                    csv.addValue("DATE CREATED: "+today);
                    csv.changeLines(1);

                    String period = "PERIOD "+fromStr.substring(0,fromStr.indexOf(' '))+" - "+toStr.substring(0,toStr.indexOf(' '));
                    csv.addValue(period);
                    csv.changeLines(2);
                    csv.addValue("REPORT");
                    csv.changeLines(2);
                    csv.addValue("CATEGORY");
                    csv.addValue("ITEM NAME,,");
                    csv.addValue("QTY(SOLD)");
                    csv.addValue("PROFIT");


                    csv.changeLines(2);


                    String oldCategory="";
                    for (int i = 0; i < reports.size(); i++) {
                        reportOrder r = reports.get(i);
                        if(oldCategory.compareTo(r.category)!=0){
                            if(oldCategory.compareTo("")!=0)
                                csv.changeLines(1);
                            csv.addValue(r.category);
                            csv.changeLines(1);
                            oldCategory=r.category;

                        }
                        csv.addSpace(1);
                        csv.addValue(r.name);
                        csv.addSpace(2);


                        csv.addValue(r.quantity);

                        csv.addValue(r.profit);
                        csv.changeLines(1);

                    }
                    fw.append(csv.toString());
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

    /**
     * Create the report about the tax of the orders
     * @param v the view that clicked the button
     */
    public void taxReport(View v){
        checkInput();
        if(!getOrdes()) {
            return;
        }

        Item.fillAllItems();
        HashMap<Integer, List<ItemOrder>> hashMap = new HashMap<Integer, List<ItemOrder>>();
        final ArrayList<reportTax> reports = new ArrayList<>();

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
            reportTax r = new reportTax(profit,temp.getVat());
            reports.add(r);
        }
        Collections.sort(reports);



        File folder = new File(Environment.getExternalStorageDirectory()
                + "/reports");

        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();

        final String filename = folder.toString() + "/" + "TAXES-"+fromStr+"-"+toStr+".csv";

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
                    myCsvCreator csv = new myCsvCreator();
                    FileWriter fw = new FileWriter(filename);

                    csv.addValue("BROADWAY RESTAURANT");
                    csv.changeLines(1);

                    csv.addValue("VAT XXXXXX TAX XXXXXX");
                    csv.changeLines(1);

                    java.util.Date c = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String today = df.format(c);

                    csv.addValue("DATE CREATED: "+today);
                    csv.changeLines(1);

                    String period = "PERIOD "+fromStr.substring(0,fromStr.indexOf(' '))+" - "+toStr.substring(0,toStr.indexOf(' '));
                    csv.addValue(period);
                    csv.changeLines(2);
                    csv.addValue("REPORT");
                    csv.changeLines(2);


                    csv.addValue("SALES & TAXES ANALYSIS BY VAT");
                    csv.changeLines(2);

                    csv.addValue("VAT(%)");
                    csv.addValue("GROSS");
                    csv.addValue("VAT AMOUNT");
                    csv.addValue("TOTAL");
                    csv.changeLines(2);


                    String oldVat="";
                    double total=0;
                    double allTotal=0;
                    double allTax=0;
                    double allGross=0;

                    reportTax r=null;
                    DecimalFormat def = new DecimalFormat("#.##");
                    for (int i = 0; i < reports.size(); i++) {
                        r = reports.get(i);

                        if(oldVat.compareTo(r.GetVatString())!=0){
                            if(oldVat.compareTo("")!=0){
                                double gross=total/(1.0+(reports.get(i-1).vat/100.0));
                                double vat=total-gross;
                                csv.addValue(oldVat);
                                csv.addValue(def.format(gross));
                                csv.addValue(def.format(vat));
                                csv.addValue(def.format(total));
                                csv.changeLines(1);
                                allTax+=vat;
                                allGross+=gross;
                                allTotal+=total;
                            }
                            oldVat=r.GetVatString();
                            total=0;
                        }
                        total+=r.sales;
                    }
                    if(oldVat.compareTo("")!=0){
                        double gross=total/(1.0+(r.vat/100.0));
                        double vat=total-gross;
                        csv.addValue(oldVat);
                        csv.addValue(def.format(gross));
                        csv.addValue(def.format(vat));
                        csv.addValue(def.format(total));
                        csv.changeLines(1);
                        allTax+=vat;
                        allGross+=gross;
                        allTotal+=total;
                    }

                    csv.changeLines(2);
                    csv.addValue("TOTAL GROSS");
                    csv.addValue("TOTAL VAT");
                    csv.addValue("TOTAL SALES");

                    csv.changeLines(2);

                    csv.addValue(def.format(allGross));
                    csv.addValue(def.format(allTax));
                    csv.addValue(def.format(allTotal));

                    fw.append(csv.toString());
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
    /**
     * check the input date from the user
     */
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
    /**
     * Get all the orders from the database
     * @return true if it was successful false if it was unsuccessful
     */
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