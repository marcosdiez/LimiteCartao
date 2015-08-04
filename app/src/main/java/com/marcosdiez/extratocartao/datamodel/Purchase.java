package com.marcosdiez.extratocartao.datamodel;

import android.location.Location;

import com.orm.SugarRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Marcos on 2015-08-02.
 */
public class Purchase extends SugarRecord<Purchase> {
    public static final String TAG = "EC-Purchase";
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    Card card;
    Store store;
    long timestamp;
    double amount;

    double latitude = 0;
    double longitude = 0;
    float accuracy = 0;

    public Purchase() {

    }

    public Purchase(String nomeBanco, String nomeCartao, String timestamp, String amount, String estabelecimentoAndCidade) {
        Bank theBank = Bank.getOrCreate(nomeBanco);
        Card theCard = Card.getOrCreate(nomeCartao, theBank);
        Store theStore = Store.getOrCreate(estabelecimentoAndCidade);
        double theAmount = fixAmount(amount);
        init(theCard, theStore, timestamp, theAmount);
    }

    public Purchase(Card card, Store store, String timestamp, double amount) {
        init(card, store, timestamp, amount);
    }


    private double fixAmount(String amount) {
        return Double.parseDouble(amount.replace(",", "."));
    }

    private void init(Card card, Store store, String timestamp, double amount) {
        this.card = card;
        this.amount = amount;
        this.store = store;
        setTimeStamp(timestamp);
    }

    private void setTimeStamp(String timeStamp) {
//        String str = "Jun 13 2003 23:11:52.454 UTC";
//        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        long epoch = 0;
        try {
            Date date = df.parse(timeStamp);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.timestamp = epoch;
//        Log.d(TAG, epoch + " " + timestamp);
    }

    public long getTimeStampEpoch() {
        return this.timestamp;
    }

    public String getTimeStampString() {
        Date theDate = new Date(this.timestamp);
        return dateFormat.format(theDate);
    }


    @Override
    public String toString() {
        return "Purchase: id: " + getId() + " amount: [" + amount + "] pos: ["
                + latitude + "," + longitude + " precision: " + accuracy
                + "m], timestamp: " + getTimeStampString() + " "
                + store.getName() + " " + card.getName() + "/" + card.getBank().getName();
    }

    public Card getCard() {
        return card;
    }

    public Store getStore() {
        return store;
    }

    public long getTimestamp() {
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

    public float getAccuracy() {
        return accuracy;
    }

    public void setLocation(Location mLocation) {
        this.latitude = mLocation.getLatitude();
        this.longitude = mLocation.getLongitude();
        this.accuracy = mLocation.getAccuracy();
        save();
    }


    public boolean hasMap() {
        return latitude != 0 || longitude != 0;
    }


}
