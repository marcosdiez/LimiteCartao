package com.marcosdiez.extratocartao.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.marcosdiez.extratocartao.R;


public class AboutDialogFragment extends DialogFragment {

    public static AboutDialogFragment newInstance(int title) {
        AboutDialogFragment frag = new AboutDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView tv = ((TextView) getDialog().findViewById(android.R.id.message));

        tv.setAutoLinkMask(Linkify.ALL);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        int title = getArguments().getInt("title");


        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
    }

}
