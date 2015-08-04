package com.marcosdiez.extratocartao.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 2015-08-02.
 */
public class SmsReader {
    public static final String TAG = "EC-SMS";

    public static List<SMSData> readSms(Context ctx) {

        List<SMSData> smsList = new ArrayList<SMSData>();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = ctx.getContentResolver().query(uri, null, null, null, "date ASC");
        // startManagingCursor(c);

        // Read the sms data and store it in the list
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                SMSData sms = new SMSData();
                sms.setId(c.getInt((int) c.getColumnIndexOrThrow("_id")));
                sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
                sms.setDate(c.getString(c.getColumnIndexOrThrow("date")));
                smsList.add(sms);

                c.moveToNext();
            }
        }
        c.close();

//
//        Cursor cursor = ctx.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
//
//        if (cursor.moveToFirst()) { // must check the result to prevent exception
//            do {
//                String msgData = "";
//                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
//                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
//                }
//                // use msgData
//            } while (cursor.moveToNext());
//        } else {
//            // empty box, no SMS
//        }

        return smsList;

    }

}
