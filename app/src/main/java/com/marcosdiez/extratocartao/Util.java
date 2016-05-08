package com.marcosdiez.extratocartao;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.marcosdiez.extratocartao.datamodel.Bank;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.datamodel.Settings;
import com.marcosdiez.extratocartao.sms.IncomingSms;
import com.marcosdiez.extratocartao.sms.SMSData;
import com.marcosdiez.extratocartao.sms.SmsReader;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Marcos on 2015-08-09.
 */
public class Util {
    private final static String TAG = "EC-Util";

    public final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public final static SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
    public final static SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static SimpleDateFormat ofxDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void openUrl(Purchase p, Context context){
         openUrl(buildAndroidMapsUri(p.getLatitude(), p.getLongitude()), context);
    }

    public static void openUrl(String theURL, Context context) {
        Log.d(TAG, "Opening location:" + theURL);
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(theURL));
        context.startActivity(intent);
    }

    public static String buildGoogleMapsUrl(double latitude, double longitude) {
        return "https://maps.google.com/maps?q=" + latitude + "," + longitude;
    }

    static String buildAndroidMapsUri(double latitude, double longitude) {
        String theURL = "geo:0,0?q=" + latitude + "," + longitude;
        return theURL;
    }

    public static String getPublicWritableFolder(){
        return Environment.getExternalStorageDirectory() + "/Android/data/" +
                BuildConfig.APPLICATION_ID;
    }

    private static boolean hasData(){
        List<Bank> banks = Bank.listAll(Bank.class);
        return banks.size() > 0;
    }

    public static void loadStoredSmsData(Context context)  throws ParsingSmsException {
        if (hasData()) {
            return;
        }
        Log.d(TAG, "Loading stored SMS data...");
        for (SMSData sms : SmsReader.readSms(context)) {
            IncomingSms.createPurchaseIfTheSmsIsFromABank(sms);
        }
        Settings.setFirstTimeFalse();
    }

    public static String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
