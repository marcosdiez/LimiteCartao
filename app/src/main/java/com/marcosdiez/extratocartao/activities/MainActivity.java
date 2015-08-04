package com.marcosdiez.extratocartao.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends ListActivity {
    public static final String TAG = "EC-MAIN";

    static String buildAndroidMapsUri(double latitude, double longitude) {
        String theURL = "geo:0,0?q=" + latitude + "," + longitude;
        return theURL;
    }

    void openUrl(String theURL) {
        Log.d(TAG, "Opening location:" + theURL);
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(theURL));
        startActivity(intent);
    }

    @Override
    public void onListItemClick(ListView theList, View theView, int position, long id) {
        Purchase p = (Purchase) getListAdapter().getItem(position);
        Toast.makeText(getBaseContext(), p.toString(), Toast.LENGTH_LONG).show();

        if (p.hasMap()) {
            openUrl(buildAndroidMapsUri(p.getLatitude(), p.getLongitude()));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadStoredSmsData();
        populateListView();

        Log.d(TAG, "Done");
    }

    private void populateListView() {
        Log.d(TAG, "Population view");
        List<Purchase> pList = Purchase.listAll(Purchase.class);
        PurchaseListAdapter pla = new PurchaseListAdapter(this, pList);
        setListAdapter(pla);
    }


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

}
