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
    double latitude = 0;
    double longitude = 0;

    @Override
    public String toString() {
        return "Purchase: id: " + getId() + " amount: [" + amount + "] pos: [ " + latitude + "," + longitude + "], timestamp: " + timestamp + " " + store.toString() + " " + card.toString();
    }

    public Purchase() {

    }

    public Purchase(Card card, Store store, int timestamp, double amount) {
        this.card = card;
        this.timestamp = timestamp;
        this.amount = amount;
        this.store = store;
    }

    public Card getCard() {
        return card;
    }

    public Store getStore() {
        return store;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
