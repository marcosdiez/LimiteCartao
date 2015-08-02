package com.marcosdiez.extratocartao.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.SmsParser;
import com.marcosdiez.extratocartao.datamodel.Bank;
import com.marcosdiez.extratocartao.datamodel.Card;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.datamodel.Store;
import com.marcosdiez.extratocartao.sms.SMSData;
import com.marcosdiez.extratocartao.sms.SmsReader;

public class MainActivity extends Activity {
    public static final String TAG = "EC-MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        testeDB();
        testSMS();
//        listPurchases();
    }


    void listPurchases() {
        for (Purchase p : Purchase.listAll(Purchase.class)) {
            Log.d(TAG, p.toString());
        }
    }

    void testSMS() {
        for (SMSData sms : SmsReader.readSms(this)) {
            Log.d(TAG, sms.toString());
            SmsParser.parseSms(sms);
        }
    }

    void testeDB() {
        Bank b = Bank.getOrCreate("Bradesco");
        Card c = Card.getOrCreate("Final 5761", b);
        Store s = Store.getOrCreate("blah");
        Purchase p = new Purchase(c, s, 0, 10);
        p.save();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
