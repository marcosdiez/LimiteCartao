package com.marcosdiez.extratocartao.datamodel;

import com.marcosdiez.extratocartao.Util;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcos on 2016-02-08.
 */
public class MonthlyPurchaseHelper extends SugarRecord<MonthlyPurchaseHelper> {

    public static final String TAG = "MonthlyPurchaseHelper";

    String name;
    double amount;
    long timestamp;

    public MonthlyPurchaseHelper() {
    }

    public static List<MonthlyPurchase> getList(int first_day_of_the_credit_card_statement, Card card) {
        ArrayList<MonthlyPurchase> output = new ArrayList<>();
        MonthlyPurchase theMonthlyPurchase = null;

        double totalAmount = 0;

        long statementDate = getNextStatmentDate(first_day_of_the_credit_card_statement);

        for (MonthlyPurchaseHelper mph : getMontlyPurchaseHelperList(card)) {
            if (mph.getDate().getTime() < statementDate) {
                theMonthlyPurchase = new MonthlyPurchase(mph.getName(), statementDate, totalAmount);
                output.add(theMonthlyPurchase);
                statementDate = subtractOneMonthFromDate(statementDate);
            }
            theMonthlyPurchase.addAmont(mph.amount);
            totalAmount += mph.amount;
        }
        return output;
    }

    private static long subtractOneMonthFromDate(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(date));
        cal.add(Calendar.MONTH, -1);
        return cal.getTime().getTime();
    }

    private static List<MonthlyPurchaseHelper> getMontlyPurchaseHelperList(Card card) {
        String query = "select " +
                "purchase.id, card.name, amount, timestamp \n" +
                "from purchase\n" +
                "join card on card.id = purchase.card\n" +
                "where purchase.card = %d\n" +
                "order by timestamp desc";

        query = String.format(query, card.getId());

        List<MonthlyPurchaseHelper> pList = MonthlyPurchaseHelper.findWithQuery(MonthlyPurchaseHelper.class, query);

        return pList;
    }

    private static long getNextStatmentDate(int first_day_of_the_credit_card_statement) {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        int today_day = cal.get(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, first_day_of_the_credit_card_statement);
        if (today_day > first_day_of_the_credit_card_statement) {
            cal.add(Calendar.MONTH, 1);
        }

        return cal.getTime().getTime();
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getTimestampString() {
        return Util.dateFormat.format(timestamp);
    }

    public Date getDate() {
        return new Date(this.timestamp);
    }
}
