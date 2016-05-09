package com.marcosdiez.extratocartao.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.marcosdiez.extratocartao.Gps;
import com.marcosdiez.extratocartao.ParsingSmsException;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.glue.SmsParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcos on 2015-08-03.
 */
// http://androidexample.com/Incomming_SMS_Broadcast_Receiver_-_Android_Example/index.php?view=article_discription&aid=62&aaid=87
public class IncomingSms extends BroadcastReceiver {
    // Get the object of SmsManager
    private final static String TAG = "EC-IncomingSms";
    private static List<Gps> gpsList = new ArrayList<Gps>();
    final SmsManager sms = SmsManager.getDefault();

    @Nullable
    public static Purchase createPurchaseIfTheSmsIsFromABank(SMSData newSms, Boolean saveToDb)  throws ParsingSmsException {
        Purchase p = SmsParser.parseSmsPurchase(newSms);
        if (p != null) {
            Log.d(TAG, "SMS:" + newSms.getBody());
            Log.d(TAG, "Adding new purchase:" + p.toString());
            if(saveToDb) {
                p.save();
            }
        }
        return p;
    }

    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null) {
                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        if (currentMessage != null) {
                            String senderPhoneNumber = currentMessage.getDisplayOriginatingAddress();
                            String message = currentMessage.getDisplayMessageBody();

                            Log.i(TAG, "SMS Received! senderNum: " + senderPhoneNumber + "; message: " + message);

                            SMSData newSms = createSms(senderPhoneNumber, message);

                            Purchase p = createPurchaseIfTheSmsIsFromABank(newSms, true);
                            if (p != null) {
                                Log.d(TAG, "SMS belongs to a bank");
                                Gps gps = new Gps(context, this);
                                gps.setLocationWhenAvailable(p);
                                printToast(context, senderPhoneNumber, message);
                                gpsList.add(gps);
                            }
                        }
                    } // end for loop
                } // pdusObj is null
            } // bundle is null

        } catch (Exception e) {
            Log.e(TAG, "Exception smsReceiver" + e);
        }
    }

    public void removeGpsFromList(Gps gps) {
        gpsList.remove(gps);
        Log.d(TAG, "GPS Info should be dealocated.");
    }

    private void printToast(Context context, String senderNum, String message) {
        // Show Alert
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,
                "Sms de Banco:[" + message + "]", duration);
        toast.show();
    }

    @NonNull
    private SMSData createSms(String phoneNumber, String message) {
        SMSData newSms = new SMSData();
        newSms.setDate(new Date());
        newSms.setBody(message);
        newSms.setNumber(phoneNumber);
        newSms.setId(0);
        return newSms;
    }
}
