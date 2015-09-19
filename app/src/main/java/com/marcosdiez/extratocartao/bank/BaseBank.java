package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;
import com.marcosdiez.extratocartao.sms.SMSData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marcos on 2015-09-19.
 */
public abstract class BaseBank {
    private static String TAG = "EC-Bank";

    protected SMSData lastSms;
    protected Matcher lastMatch;
    private Pattern pattern = Pattern.compile(getRegEx(), Pattern.CASE_INSENSITIVE);;

    public boolean isSmsFromBank(SMSData theSms) {
        lastSms = theSms;
        lastMatch = pattern.matcher(theSms.getBody());
        return lastMatch.find();

    }
    protected abstract String getRegEx();
    public abstract BankSms getBankSms();
}
