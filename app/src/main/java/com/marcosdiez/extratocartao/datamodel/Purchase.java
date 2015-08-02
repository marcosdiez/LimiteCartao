package com.marcosdiez.extratocartao.datamodel;

import com.orm.SugarRecord;

/**
 * Created by Marcos on 2015-08-02.
 */
public class Purchase extends SugarRecord<Purchase> {
    Card card;
    Store store;
    int timestamp;
    double amount;
    double latitute = 0;
    double longitude = 0;

    public Purchase() {
    }

    public Purchase(Card card, Store store, int timestamp, double amount) {
        this.card = card;
        this.timestamp = timestamp;
        this.amount = amount;
        this.store = store;
    }

}
