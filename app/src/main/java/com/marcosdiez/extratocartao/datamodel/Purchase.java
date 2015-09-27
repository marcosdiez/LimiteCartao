package com.marcosdiez.extratocartao.datamodel;

import android.location.Location;

import com.marcosdiez.extratocartao.Util;
import com.marcosdiez.extratocartao.sms.BankSms;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Marcos on 2015-08-02.
 */
public class Purchase extends SugarRecord<Purchase> {
    public static final String TAG = "EC-Purchase";
    final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    final static SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static SimpleDateFormat ofxDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private static String sep = ",";
    Card card;
    Store store;
    long timestamp;
    double amount;
    double latitude = 0;
    double longitude = 0;
    float accuracy = 0;
    @Ignore
    double totalAmount = 0;

    public Purchase() {

    }


    public Purchase(BankSms bankSms) {
        Bank theBank = Bank.getOrCreate(bankSms.nomeBanco);
        Card theCard = Card.getOrCreate(bankSms.nomeCartao, theBank);
        Store theStore = Store.getOrCreate(bankSms.estabelecimentoAndCidade);
        double theAmount = fixAmount(bankSms.amount);
        init(theCard, theStore, bankSms.timestamp, theAmount);
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

    public static String getCsvHeader() {

        return "ID" + sep + "Banco" + sep + "Cartão" + sep + "Estabelecimento" + sep + "Valor" + sep + "Data" + sep + "Latitude" + sep + "Longitude" + sep + "Precisão (m)" + sep + "Mapa\n";
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private double fixAmount(String amount) {
        return Double.parseDouble(amount.replace(".", "").replace(",", "."));
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
        return dateTimeFormat.format(getDate());
    }

    public String getDateStampString() {
        return dateFormat.format(getDate());
    }

    public String getDateIso() {
        return isoDateFormat.format(getDate());
    }

    public Date getDate() {
        return new Date(this.timestamp);
    }

    public String getOfxTimeStampString() {
        return ofxDateFormat.format(getDate());
    }


    @Override
    public String toString() {
        String forma;

        if (hasMap()) {
            forma = "Compra %d, banco: %s. Posição: %f/%f, precisão: %.1fm";
        } else {
            forma = "Compra %d, banco: %s. Posição da compra não disponível.";
        }

        return String.format(forma,
                getId(), card.getBank().getName(), latitude, longitude, accuracy);
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

    public String toCsvLine() {
        String quote = "\"";

        String googleMapsUrl = "";
        if (getLatitude() != 0 || getLongitude() != 0) {
            googleMapsUrl = quote + "=HYPERLINK(" + quote + quote + Util.buildGoogleMapsUrl(getLatitude(), getLongitude()) + quote + quote + ")" + quote;
        }

        return getId() + sep +
                quote + getCard().getBank().getName() + quote + sep +
                quote + getCard().getName() + quote + sep +
                quote + getStore().getName() + quote + sep +
                getAmount() + sep +
                getDateIso() + sep +
                getLatitude() + sep +
                getLongitude() + sep +
                getAccuracy() + sep +
                googleMapsUrl
                + "\n";
    }


    public String toOfxLine() {

        String model = "<STMTTRN>\n\t<TRNTYPE>CREDIT\n\t<DTPOSTED>%s\n\t<TRNAMT>%f\n\t<FITID>%d\n\t<CHECKNUM>%d\n\t<MEMO>%s\n</STMTTRN>\n";
        String output = String.format(model,
                getOfxTimeStampString(),
                -1.0 * getAmount(),
                getId(),
                getId(),
                getStore()
        );


        return output;
    }

}
