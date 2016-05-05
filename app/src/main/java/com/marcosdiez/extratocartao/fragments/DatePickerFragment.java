package com.marcosdiez.extratocartao.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.marcosdiez.extratocartao.activities.AddEditPurchase;

import java.util.Date;

/**
 * Created by Marcos on 2016-05-01.
 */
public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    AddEditPurchase activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        activity = (AddEditPurchase) getActivity();
        Date theDate = activity.getDate();

        return new DatePickerDialog(getActivity(), this, theDate.getYear(), theDate.getMonth(), theDate.getDay());
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        activity.onDateSet(year, month,day );
        // Do something with the date chosen by the user
    }
}
