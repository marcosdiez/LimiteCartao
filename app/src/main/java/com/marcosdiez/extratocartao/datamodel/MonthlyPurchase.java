package com.marcosdiez.extratocartao.datamodel;

import com.marcosdiez.extratocartao.Util;

/**
 * Created by Marcos on 2016-05-15.
 */
public class MonthlyPurchase {
    String card;
    long timestamp;
    int numPurchases = 0;
    double amount = 0;
    double totalAmount;

    public MonthlyPurchase(String card, long timestamp, double totalAmount) {
        this.card = card;
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;
    }


    public double addAmont(double amount) {
        numPurchases++;
        this.amount += amount;
        this.totalAmount += amount;
        return totalAmount;
    }

    public int getNumPurchases(){
        return numPurchases;
    }

    public String getCard() {
        return card;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTimestampString() {
        return Util.dateFormat.format(timestamp);
    }


    public double getAmount() {
        return amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
