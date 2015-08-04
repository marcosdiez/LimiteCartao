package com.marcosdiez.extratocartao.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.datamodel.Settings;
import com.marcosdiez.extratocartao.glue.PurchaseListAdapter;
import com.marcosdiez.extratocartao.glue.SmsParser;
import com.marcosdiez.extratocartao.sms.SMSData;
import com.marcosdiez.extratocartao.sms.SmsReader;

import java.util.List;

public class MainActivity extends Activity {
    public static final String TAG = "EC-MAIN";

    private ListView purchase_list_view;


    static String buildAndroidMapsUri(double latitude, double longitude) {
        String theURL = "geo:0,0?q=" + latitude + "," + longitude;
        return theURL;
    }


    void openUrl(String theURL) { // }, Activity theActivity, String thePackage) {
        Log.d(TAG, "Opening location:" + theURL);
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(theURL));
//        if (thePackage != null) {
//            intent.setPackage(thePackage);
//        }
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        purchase_list_view = (ListView) findViewById(R.id.purchase_list_view);
        purchase_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Purchase p = (Purchase) purchase_list_view.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), p.toString(), Toast.LENGTH_LONG).show();

                if (p.hasMap()) {
                    openUrl(buildAndroidMapsUri(p.getLatitude(), p.getLongitude()));
                }

            }
        });


        loadStoredSmsData();
        populateListView();

//        testeDB();
//        listPurchases();

        Log.d(TAG, "Done");
    }

    private void populateListView() {
        Log.d(TAG, "Population view");
        List<Purchase> pList = Purchase.listAll(Purchase.class);
        PurchaseListAdapter pla = new PurchaseListAdapter(this, pList);
        purchase_list_view.setAdapter(pla);

    }

//    void listPurchases() {
//        Log.d(TAG, "Listing purchases");
//        for (Purchase p : Purchase.listAll(Purchase.class)) {
//            Log.d(TAG, p.toString());
//        }
//    }

    void loadStoredSmsData() {
        if (!Settings.getFirstTime()) {
            return;
        }
        Log.d(TAG, "Loading stored SMS data...");
        for (SMSData sms : SmsReader.readSms(this)) {
            Purchase p = SmsParser.parseSms(sms);
            if (p != null) {
                Log.d(TAG, "SMS:" + sms.getBody());
                Log.d(TAG, "Adding new purchase:" + p.toString());
                p.save();
            }
        }
        Settings.setFirstTimeFalse();
    }

//    void testeDB() {
//        Bank b = Bank.getOrCreate("Bradesco");
//        Card c = Card.getOrCreate("Final 5761", b);
//        Store s = Store.getOrCreate("blah");
//        Purchase p = new Purchase(c, s, "13/12/2015 09:28", 10, 0);
//        p.save();
//    }

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
