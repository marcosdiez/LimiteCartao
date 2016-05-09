package com.marcosdiez.extratocartao.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.marcosdiez.extratocartao.activities.AddEditPurchase;

/**
 * Created by Marcos on 2016-05-01.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static String TAG = "DatePickerFragment";
    AddEditPurchase activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        activity = (AddEditPurchase) getActivity();

        Bundle theBundle = this.getArguments();
        int year = theBundle.getInt("YEAR");
        int month = theBundle.getInt("MONTH");
        int day = theBundle.getInt("DAY");

        Log.d(TAG, "Date -> " + year + " " + month + " " + day);


        return new DatePickerDialog(getActivity(), this,
                year,
                month,
                day
        );

//        // Create a new instance of DatePickerDialog and return it
//        return new DatePickerDialog(getActivity(), this,
//                this.getArguments().getInt("YEAR", 2016),
//                this.getArguments().getInt("MONTH", 3),
//                this.getArguments().getInt("DAY", 15)
//        );
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        activity.onDateSet(year, month, day);
        // Do something with the date chosen by the user
    }
}
