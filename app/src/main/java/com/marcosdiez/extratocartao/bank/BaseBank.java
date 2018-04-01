package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.ParsingSmsException;
import com.marcosdiez.extratocartao.sms.BankSms;
import com.marcosdiez.extratocartao.sms.SMSData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marcos on 2015-09-19.
 */
public abstract class BaseBank {
    private static String TAG = "EC-Bank";

    protected final String diaMesAno = "(\\d+/\\d+/\\d+)";
    protected final String diaMesAnoHoraMinuto = "(\\d+/\\d+/\\d+\\s+\\d+:\\d+)";
    protected final String valor = "(\\d+[\\.\\d]?\\d*,\\d+)";

    protected SMSData lastSms;
    protected Matcher lastMatch;
    private Pattern pattern = Pattern.compile(getRegEx(), Pattern.CASE_INSENSITIVE);

    public boolean isSmsFromBank(SMSData theSms) {
        lastSms = theSms;
        lastMatch = pattern.matcher(theSms.getBody());
        return lastMatch.find();
    }

    protected abstract String getRegEx();

    protected abstract BankSms getBankSmsHelper();

    public BankSms getBankSms() throws ParsingSmsException {
        try {
            return getBankSmsHelper();
        } catch (Exception e) {
            throw new ParsingSmsException(lastSms.getBody(), e);
        }

    }
}
