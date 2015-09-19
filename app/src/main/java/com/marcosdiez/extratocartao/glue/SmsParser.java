package com.marcosdiez.extratocartao.glue;

import android.util.Log;

import com.marcosdiez.extratocartao.bank.BancoDoBrasil;
import com.marcosdiez.extratocartao.bank.BaseBank;
import com.marcosdiez.extratocartao.bank.Bradesco;
import com.marcosdiez.extratocartao.bank.Itau;
import com.marcosdiez.extratocartao.bank.ItauPersonnalite;
import com.marcosdiez.extratocartao.bank.Santander;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.sms.BankSms;
import com.marcosdiez.extratocartao.sms.SMSData;

/**
 * Created by Marcos on 2015-08-02.
 */
public class SmsParser {
    private static final String TAG = "EC-SmsParser";

    private static BaseBank[] bankList = new BaseBank[]{
            new BancoDoBrasil(),
            new Bradesco(),
            new Itau(),
            new ItauPersonnalite(),
            new Santander()
    };

    public static BankSms parseSms(SMSData theSMS) {
        for (BaseBank thisBank : bankList) {
            Log.d(TAG, "Checking V2 if the SMS is from " + thisBank.getClass().getName());
            if (thisBank.isSmsFromBank(theSMS)) {
                return thisBank.getBankSms();
            }
        }
        return null;
    }

    public static Purchase parseSmsPurchase(SMSData theSms) {
        BankSms theBankSms = parseSms(theSms);
        if (theBankSms != null) {
            return new Purchase(theBankSms);
        }
        return null;
    }

}


