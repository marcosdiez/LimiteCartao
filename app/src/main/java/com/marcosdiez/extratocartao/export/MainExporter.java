package com.marcosdiez.extratocartao.export;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Marcos on 2015-09-27.
 */

public class MainExporter extends AsyncTask<Void, Void, Intent> {

    ProgressDialog progress;
    Activity callerActivity;

    public MainExporter(Activity callerActivity) {
        this.callerActivity = callerActivity;
    }

    protected Intent doInBackground(Void... voided) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Extrato do Cartão de Crédito");
        String msg = "Extrato do Cartão de Crédito\n\nAbra o arquivo CSV com o Excel ou OpenOffice ou LibreOffice\nAbra o arquivo OFX com o Money, Quicken ou iBank\n";
        intent.putExtra(Intent.EXTRA_TEXT, msg);

        ArrayList<Uri> uris = new ArrayList<>();
        uris.add(Uri.fromFile(Csv.createCsvOfPurchases()));
        uris.add(Uri.fromFile(Ofx.createOfxOfPurchases()));
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

        return intent;
    }

    protected void onPreExecute() {
        progress = new ProgressDialog(callerActivity);
        progress.setTitle("Exportar");
        progress.setMessage("Gerando arquivos a serem exportados...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    protected void onPostExecute(Intent intent) {
        progress.dismiss();
        callerActivity.startActivityForResult(Intent.createChooser(intent, "Enviar Arquivo CSV/OFX"), 42);
    }

}

