package com.marcosdiez.extratocartao.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
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

        // Read the sms data and store it in the list
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                SMSData sms = createSmsObject(c);
                smsList.add(sms);

                c.moveToNext();
            }
        }
        c.close();

        return smsList;

    }

    @NonNull
    private static SMSData createSmsObject(Cursor c) {
        SMSData sms = new SMSData();
        sms.setId(c.getInt(c.getColumnIndexOrThrow("_id")));
        sms.setBody(c.getString(c.getColumnIndexOrThrow("body")));
        sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")));
        sms.setDate(new Date(c.getLong(c.getColumnIndexOrThrow("date"))));
        return sms;
    }

}
