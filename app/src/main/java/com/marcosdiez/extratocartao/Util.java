package com.marcosdiez.extratocartao;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.datamodel.Settings;
import com.marcosdiez.extratocartao.glue.SmsParser;
import com.marcosdiez.extratocartao.sms.SMSData;
import com.marcosdiez.extratocartao.sms.SmsReader;

/**
 * Created by Marcos on 2015-08-09.
 */
public class Util {
    private final static String TAG = "EC-Util";

    public static void openUrl(Purchase p, Context context){
         openUrl(buildAndroidMapsUri(p.getLatitude(), p.getLongitude()), context);
    }

    public static void openUrl(String theURL, Context context) {
        Log.d(TAG, "Opening location:" + theURL);
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(theURL));
        context.startActivity(intent);
    }

    static String buildAndroidMapsUri(double latitude, double longitude) {
        String theURL = "geo:0,0?q=" + latitude + "," + longitude;
        return theURL;
    }

    public static void calcualteTotal(Context context) {

    }

    public static void loadStoredSmsData(Context context) {
        if (!Settings.getFirstTime()) {
            return;
        }
        Log.d(TAG, "Loading stored SMS data...");
        for (SMSData sms : SmsReader.readSms(context)) {
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
