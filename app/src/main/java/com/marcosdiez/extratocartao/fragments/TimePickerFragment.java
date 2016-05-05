package com.marcosdiez.extratocartao.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.marcosdiez.extratocartao.activities.AddEditPurchase;

import java.util.Date;

/**
 * Created by Marcos on 2016-05-01.
 */
public  class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    AddEditPurchase activity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        activity = (AddEditPurchase) getActivity();
        Date theDate = activity.getDate();

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, theDate.getHours(), theDate.getMinutes(),
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        activity.onTimeSet(hourOfDay, minute);
    }
}
