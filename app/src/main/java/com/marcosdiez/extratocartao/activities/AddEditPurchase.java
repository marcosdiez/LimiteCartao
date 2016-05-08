package com.marcosdiez.extratocartao.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.Util;
import com.marcosdiez.extratocartao.datamodel.Card;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.datamodel.Store;
import com.marcosdiez.extratocartao.fragments.DatePickerFragment;
import com.marcosdiez.extratocartao.fragments.TimePickerFragment;

import java.util.Date;
import java.util.List;

public class AddEditPurchase extends AppCompatActivity {

    private static String TAG = "EC-AddEditPurcase";

    private Spinner spinPlasticCard;
    private Spinner spinStore;
    private EditText editTextAmount;
    private Button buttonPickPurchaseTime;
    private Button buttonPickPurchaseDate;

    public Date getDate() {
        return thePurchase.getDate();
    }

    private Purchase thePurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_purchase);
        populateBoilerplate();

        long thePurchaseId = getIntent().getExtras().getLong("thePurchaseId");
        thePurchase = Purchase.findById(Purchase.class, thePurchaseId);
        populateWidgetValues();
    }

    private void populateWidgetValues() {
        Log.d(TAG, thePurchase.toString());
        Log.d(TAG, thePurchase.toCsvLine());

        populateCards(thePurchase.getCard().getId());
        populateStore(thePurchase.getStore().getId());

        Log.d(TAG, thePurchase.toString());

        double x = thePurchase.getAmount();

        editTextAmount.setText(String.format("%.2f", x));


        //editTextAmount.setText(fixNumberFormat(String.valueOf((p.getAmount()))));

        Date theDate = thePurchase.getDate();
        buttonPickPurchaseDate.setText(Util.dateFormat.format(theDate));
        buttonPickPurchaseTime.setText(Util.hourFormat.format(theDate));
    }


    private void populateBoilerplate() {
        spinPlasticCard = (Spinner) findViewById(R.id.spinPlasticCard);
        spinStore = (Spinner) findViewById(R.id.spinStore);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        buttonPickPurchaseDate = (Button) findViewById(R.id.buttonPickPurchaseDate);
        buttonPickPurchaseTime = (Button) findViewById(R.id.buttonPickPurchaseTime);
    }

    private void populateStore(long selectedId) {
        List<Store> stores = Store.find(Store.class, null, null, null, "name", null);
        ArrayAdapter<Store> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stores);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStore.setAdapter(adapter);
        setStore(selectedId, stores);
    }

    private void setStore(long selectedId, List<Store> stores) {
        int counter = 0;
        for (Store s : stores) {
            if (s.getId() == selectedId) {
                spinStore.setSelection(counter);
                return;
            }
            counter++;
        }
    }

    private void populateCards(long selectedId) {
        List<Card> cards = Card.find(Card.class, null, null, null, "name", null);
        ArrayAdapter<Card> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, cards);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPlasticCard.setAdapter(adapter);
        setPlasticCard(selectedId, cards);

    }

    private void setPlasticCard(long selectedId, List<Card> cards) {
        int counter = 0;
        for (Card c : cards) {
            if (c.getId() == selectedId) {
                spinPlasticCard.setSelection(counter);
                return;
            }
            counter++;
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onDateSet(int year, int month, int day) {
      //  activity.onDateSet(year, month,day );
        // Do something with the date chosen by the user
    }

    public void onTimeSet(int hourOfDay, int minute) {
        // activity.onTimeSet(hourOfDay, minute);
    }
}
