package com.marcosdiez.extratocartao.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marcosdiez.extratocartao.ParsingSmsException;
import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.Util;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.glue.SmsParser;
import com.marcosdiez.extratocartao.sms.SMSData;

/**
 * Created by Marcos on 2016-05-08.
 */
public class ManualSmsInputFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;

    public ManualSmsInputFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_sms_input, container);
        mEditText = (EditText) view.findViewById(R.id.txt_insert_sms);
        getDialog().setTitle("Inserção manual de SMS");

        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);


        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity

            String smsText = mEditText.getText().toString();
            String msg = processSmsText(smsText);

            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            this.dismiss();
            return true;
        }
        return false;
    }

    private String processSmsText(String smsText) {
        try {
            SMSData smsData = Util.mockSms(smsText);
            try {
                Purchase thePurchase = SmsParser.parseSmsPurchase(smsData);
                if (thePurchase != null) {
                    return thePurchase.toString();
                } else {
                    return "Erro 3: Este SMS não é um SMS de cartão de crédito.";
                }
            } catch (ParsingSmsException e) {
                return "Erro 2 lendo SMS" + e.toString();
            }
        } catch (java.text.ParseException e) {
            return "Erro 1 lendo SMS" + e.toString();
        }
    }

}

