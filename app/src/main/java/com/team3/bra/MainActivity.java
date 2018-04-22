package com.team3.bra;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
/**
 * Login screen of the application and main thread of reloading waiter,chef and printing receipts
 *
 */
public class MainActivity extends Activity {
    public final static boolean TAMIAKI=true;

    public static int REFRESH_TIME=30000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        JDBC.establishConnection();
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true) {
                    try {
                        synchronized (this) {
                            wait(REFRESH_TIME);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Cook.cookClass != null){
                                        Cook.cookClass.loadOrders();
                                    }

                                    if (Waiter.waiterClass != null){
                                        Waiter.waiterClass.refresh();
                                    }
                                    if(TAMIAKI){
                                        Order.findActiveOrders();
                                        final ArrayList<Order> toPrint = new ArrayList<>();
                                        for (int i=0; i<Order.getOrders().size();i++){
                                            Order temp = Order.getOrders().get(i);
                                            temp.fillOrder();
                                            if(temp.getState()==4){
                                             toPrint.add(temp);
                                            }

                                        }

                                        for(Order print : toPrint){
                                            myCsvCreator csv = new myCsvCreator();
                                            csv.addValue("BROADWAY RESTAURANT");
                                            csv.changeLines(1);

                                            csv.addValue("VAT XXXXXX TAX XXXXXX");
                                            csv.changeLines(1);

                                            java.util.Date c = Calendar.getInstance().getTime();

                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String today = df.format(c);

                                            csv.addValue("DATE: "+today);
                                            csv.changeLines(1);

                                            csv.addValue("Inv: "+print.getId());
                                            csv.changeLines(1);

                                            csv.addValue("Retail: Legal Receipt");
                                            csv.changeLines(1);

                                            csv.addValue("ITEM");
                                            csv.addValue("QTY");
                                            csv.addValue("PRICE");
                                            csv.addValue("AMOUNT");
                                            csv.addValue("VAT(%)");

                                            csv.changeLines(1);
                                            double total =0;
                                            double totalQu =0;
                                            double totalVat =0;

                                            for (ItemOrder i : print.getItems()){
                                                csv.addValue(i.getName());
                                                csv.changeLines(1);
                                                csv.addValue("");
                                                csv.addValue(i.getQuantity()+"");
                                                csv.addValue(i.getCurrentPrice()+"");
                                                csv.addValue(String.format("%.2f", i.getCurrentPrice()*i.getQuantity()));
                                                csv.addValue(i.getVat()+"");
                                                total+=i.getCurrentPrice()*i.getQuantity();
                                                totalQu+=i.getQuantity();
                                                double gross=i.getCurrentPrice()*i.getQuantity()/(1.0+(i.getVat()/100.0));
                                                totalVat+=total-gross;
                                                csv.changeLines(1);
                                            }
                                            csv.addValue("TOTAL(EURO)");
                                            csv.addValue(String.format("%.2f", total));
                                            csv.changeLines(1);
                                            csv.addValue("Items no.");
                                            csv.addValue(totalQu+"");
                                            csv.changeLines(1);
                                            csv.addValue("Vat Amnt");
                                            csv.addValue(String.format("%.2f", totalVat));
                                            csv.changeLines(1);
                                            csv.addValue("THANK YOU");
                                            FileWriter fw = null;
                                            try {
                                                File folder = new File(Environment.getExternalStorageDirectory()
                                                        + "/receipt");

                                                boolean var = false;
                                                if (!folder.exists())
                                                    var = folder.mkdir();

                                                final String filename = folder.toString() + "/" + "receipt.csv";
                                                fw = new FileWriter(filename);
                                                fw.append(csv.toString());
                                                fw.flush();
                                                fw.close();
                                                Toast.makeText(getApplicationContext(),"Printed",Toast.LENGTH_SHORT).show();
                                                print.setState(5);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };
        };
        thread.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
    }
    /**
     * Checks if the user with the password exists and log in them to the correct screen
     * 1. Manager
     * 2. Waiter
     * 3. Chef
     * @param v the view that clicked the button
     */
    public void onLoginClick(View v){
        if(JDBC.isConnected()!=true)
            if(JDBC.establishConnection()!=true) {
                Toast toast = Toast.makeText(getApplicationContext(), "No connection to Database.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        EditText txtUser = (EditText) findViewById(R.id.txtUser);
        String username = txtUser.getText().toString();
        EditText txtPass = (EditText) findViewById(R.id.txtPass);
        String password = txtPass.getText().toString();
        try {
            password=EncryptionAlgorithm.SHA1(password);
        }catch(Exception E){

        }

        String a[] = {username,password};
        Vector<Vector<Object>> vec = JDBC.callProcedure("LOGIN", a);


        if (username.equals("1") ||(vec.size()!=0 && vec.get(0).get(2).equals("1"))) {
            Intent intent = new Intent(this, Manager.class);
            startActivity(intent);
            return;
        }
        if (username.equals("2") ||(vec.size()!=0 && vec.get(0).get(2).equals("2"))) {
            Intent intent = new Intent(this, Waiter.class);
            startActivity(intent);
            return;
        }
        if (username.equals("3") ||(vec.size()!=0 && vec.get(0).get(2).equals("3"))) {
            Intent intent = new Intent(this, Cook.class);
            startActivity(intent);
            return;
        }
        // User c = new User(vec.get(3));

        //System.out.println(password);
        if (vec.size()==0){
            Toast toast= Toast.makeText(getApplicationContext(),"Wrong UserName or Password!",Toast.LENGTH_SHORT);
            toast.show();
            txtUser.setText("");
            txtPass.setText("");
            return;
        }


    }
    /**
     * Exit from the program
     * @param v the view that clicked the button
     */
    public void exitClicked(View v){
        finish();
    }
}