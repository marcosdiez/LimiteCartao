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
        return Util.dateTimeFormat.format(getDate());
    }

    public String getDateStampString() {
        return Util.dateFormat.format(getDate());
    }

    public String getDateIso() {
        return Util.isoDateFormat.format(getDate());
    }

    public Date getDate() {
        return new Date(this.timestamp);
    }

    public String getOfxTimeStampString() {
        return Util.ofxDateFormat.format(getDate());
    }


    public String getStringToShare() {
        // no point is sharing the date nor the amount spent. that's useless info being shared.
        // it could be a privacy issue
        return String.format("Loja: %s. Localização: https://maps.google.com/maps?q=%f,%f",
                store.getName(), latitude, longitude);
    }

    @Override
    public String toString() {
        String forma;

        if (hasMap()) {
            forma = "Compra %d, Cartão: %s, Banco: %s Loja: %s Valor: %.2f. Posição: %f/%f, precisão: %.1fm";
        } else {
            forma = "Compra %d, Cartão: %s, banco: %s loja: %s Valor: %.2f. Posição da compra não disponível.";
        }

        // the card name is important because some cards have long names and we only show initials

        return String.format(forma,
                getId(), card.getName(), card.getBank().getName(), store.getName(), amount, latitude, longitude, accuracy);
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

        String model = "\t\t\t<STMTTRN>\n" +
                "\t\t\t\t<TRNTYPE>CREDIT\n" +
                "\t\t\t\t<DTPOSTED>%s\n" +
                "\t\t\t\t<TRNAMT>%f\n" +
                "\t\t\t\t<FITID>%d\n" +
                "\t\t\t\t<CHECKNUM>%d\n" +
                "\t\t\t\t<NAME>%s\n" +
//                "\t\t\t\t<MEMO>%s\n" +
                "\t\t\t</STMTTRN>\n";

        String storeName = getStore().toString().replace("&", "");

        String output = String.format(model,
                getOfxTimeStampString(),
                -1.0 * getAmount(),
                getId(),
                getId(),
                storeName,
                storeName
        );


        return output;
    }

}
