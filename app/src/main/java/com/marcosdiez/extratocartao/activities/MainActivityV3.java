package com.marcosdiez.extratocartao.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.marcosdiez.extratocartao.ParsingSmsException;
import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.Util;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.glue.PurchaseListAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivityV3 extends AppCompatActivity {

    private static String TAG = "EC-Main";
    ListView purchaseListView;
    Context mySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_v3);
        mySelf = this;

        loadSmsAndSendErrorIfNecessary();
        initListView();
    }

    private void loadSmsAndSendErrorIfNecessary() {
        try {
            Util.loadStoredSmsData(this);
        } catch (ParsingSmsException e) {
            String baseMsg = "Olá ! Este é o SMS que travou meu programa. Por favor envie ele por email para mim para que eu o conserte!\n\nSMS: %s\n\nException: %s\n\n%s\n\nInner Exception: %s\n\n%s\n\n";
            String msg = String.format(baseMsg, e.msg, e.innerException.toString(), Util.stackTraceToString(e.innerException), e.toString(), Util.stackTraceToString(e));
            Log.d(TAG, msg);
            sendErrorPerMail(msg);
        }
    }


    private void initListView() {
        purchaseListView = (ListView) findViewById(R.id.purchase_list);

        List<Purchase> pList = Purchase.find(Purchase.class, null, null, null, "id desc", null);
        double total = 0;
        for (Purchase p : pList) {
            total += p.getAmount();
            p.setTotalAmount(total);
        }

        PurchaseListAdapter pla = new PurchaseListAdapter(this, pList);
        purchaseListView.setAdapter(pla);
        purchaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Purchase p = (Purchase) purchaseListView.getAdapter().getItem(position);
                Toast.makeText(mySelf, p.toString(), Toast.LENGTH_LONG).show();

                if (p.hasMap()) {
                    Util.openUrl(p, mySelf);
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_v3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_about:
                showDialog();
                return true;
            case R.id.action_export_csv:
                exportCsv();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void sendErrorPerMail(String msg) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Erro lendo SMS do Cartão de Crédito");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"erros_extrato_cartao@webdanfe.com.br"});
        intent.putExtra(Intent.EXTRA_TEXT, msg);

        this.startActivityForResult(Intent.createChooser(intent, "Erro lendo um SMS. Por favor envie para o autor!"), 42);
    }


    private void exportCsv() {
        File outputFile = createCsvOfPurchases();
        if (outputFile == null) {
            Toast.makeText(this, "Error gerando o arquivo CSV", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Extrato do Cartão de Crédito");
        Uri extratoURI = Uri.fromFile(outputFile);
        intent.putExtra(Intent.EXTRA_STREAM, extratoURI);
        this.startActivityForResult(Intent.createChooser(intent, "Enviar Arquivo CSV"), 42);
    }

    private File createCsvOfPurchases() {
        Log.d(TAG, "Export CSV");
        List<Purchase> pList = Purchase.find(Purchase.class, null, null, null, "id", null);
        try {

            String sufix = (new SimpleDateFormat("yyyy-MM-dd-HH-mm")).format(new Date());

            File outputFile = File.createTempFile("extrato_cartao_" + sufix + "_", ".csv");
            outputFile.setReadable(true, false);
            FileWriter out = new FileWriter(outputFile);
            out.write('\ufeff');
            out.write(Purchase.getCsvHeader());
            for (Purchase p : pList) {
                out.write(p.toCsvLine());
            }
            out.close();
            Log.d(TAG, "File Created: " + outputFile);
            return outputFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    void showDialog() {
        DialogFragment newFragment = AboutDialogFragment.newInstance(
                R.string.action_about);
        newFragment.show(getFragmentManager(), "dialog");
    }


}
