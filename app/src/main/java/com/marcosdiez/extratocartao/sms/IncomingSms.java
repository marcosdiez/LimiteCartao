package com.marcosdiez.extratocartao.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.glue.SmsParser;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Marcos on 2015-08-03.
 */
// http://androidexample.com/Incomming_SMS_Broadcast_Receiver_-_Android_Example/index.php?view=article_discription&aid=62&aaid=87
public class IncomingSms extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    // Get the object of SmsManager
    private final String TAG = "EC-IncomingSms";

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i(TAG, "senderNum: " + senderNum + "; message: " + message);


                    SMSData newSms = new SMSData();
                    newSms.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                    newSms.setBody(message);
                    newSms.setNumber(phoneNumber);
                    newSms.setId(0);

                    Purchase p = SmsParser.parseSms(newSms);
                    if (p != null) {
                        Log.d(TAG, "SMS:" + newSms.getBody());
                        Log.d(TAG, "Adding new purchase:" + p.toString());
                        p.save();
                    }


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "Sms Recebido de " + senderNum + ":[" + message + "]", duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }
}
