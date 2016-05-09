package com.marcosdiez.extratocartao.activities;

import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcosdiez.extratocartao.BuildConfig;
import com.marcosdiez.extratocartao.ParsingSmsException;
import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.Util;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.datamodel.StoreJoin;
import com.marcosdiez.extratocartao.export.MainExporter;
import com.marcosdiez.extratocartao.fragments.ManualSmsInputFragment;
import com.marcosdiez.extratocartao.glue.PurchaseListAdapter;
import com.marcosdiez.extratocartao.glue.StoreJoinListAdapter;

import java.util.List;


public class MainActivityV3 extends AppCompatActivity {

    private static String TAG = "EC-Main";
    ListView purchaseListView;
    LinearLayout listHeaderGroupPurhcase;
    LinearLayout listHeaderItemPurhcase;
    Context mySelf;

    boolean showing_expenses_per_store = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_v3);
        mySelf = this;

        loadSmsAndSendErrorIfNecessary();

        listHeaderGroupPurhcase = (LinearLayout) findViewById(R.id.list_header_group_purchase);
        listHeaderItemPurhcase = (LinearLayout) findViewById(R.id.list_header_item_purchase);

        initListView();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        } else {
            populateAllPurchases();
        }
    }


    private void doMySearch(String query_arg) {
        Log.d(TAG, "doMySearch(" + query_arg + ")");

        if (query_arg == null || query_arg == "") {
            populateAllPurchases();
            return;
        }
        query_arg = "%" + query_arg + "%";

        String query = "SELECT purchase.* FROM purchase\n" +
                "join store on store.id = purchase.store\n" +
                "where store.name like ? \n" +
                "order by purchase.timestamp desc";

        List<Purchase> pList =
                Purchase.findWithQuery(Purchase.class, query, query_arg);
        //Purchase.find(Purchase.class, null, query, null, "id desc", null);
        populatListView(pList);
    }

    private void loadSmsAndSendErrorIfNecessary() {
        try {
            Util.parseSmsAndList(this);
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
        registerForContextMenu(purchaseListView);


    }

    private void populateAllPurchases() {
        int num_purchases = show_all_purchases();
        showWarningMessageIfNecessary(num_purchases);
    }

    private void showWarningMessageIfNecessary(int num_purchases) {
        if (num_purchases == 0) {
            ((TextView) findViewById(R.id.warning_no_sms)).setVisibility(View.VISIBLE);
            showAboutDialog();
        }
    }

    private int show_all_purchases() {
        List<Purchase> pList = Purchase.find(Purchase.class, null, null, null, "timestamp desc", null);
        populatListView(pList);
        return pList.size();
    }

    private void populatListView(List<Purchase> pList) {
        showing_expenses_per_store = false;
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
                openPurchaseUrlIfPossible(p);
            }
        });
        listHeaderGroupPurhcase.setVisibility(View.GONE);
        listHeaderItemPurhcase.setVisibility(View.VISIBLE);
    }

    private void openPurchaseUrlIfPossible(Purchase p) {
        Toast.makeText(mySelf, p.toString(), Toast.LENGTH_LONG).show();

        if (p.hasMap()) {
            Util.openUrl(p, mySelf);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View theView,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (!showing_expenses_per_store) {
            if (theView.getId() == R.id.purchase_list) {
//            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                //          Purchase p = (Purchase) purchaseListView.getAdapter().getItem(info.position);
                getMenuInflater().inflate(R.menu.context_menu, menu);
            }
        }
    }


    private void show_expenses_per_store() {
        showing_expenses_per_store = true;
        List<StoreJoin> pList = StoreJoin.getList();

        StoreJoinListAdapter storeJoinListAdapter = new StoreJoinListAdapter(this, pList);
        purchaseListView.setAdapter(storeJoinListAdapter);
        purchaseListView.setOnItemClickListener(null);
        listHeaderGroupPurhcase.setVisibility(View.VISIBLE);
        listHeaderItemPurhcase.setVisibility(View.GONE);

        purchaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                StoreJoin storeJoin = (StoreJoin) purchaseListView.getAdapter().getItem(position);
                show_only_this_store(storeJoin.getId());
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo());
        Purchase thePurchase = (Purchase) purchaseListView.getAdapter().getItem(info.position);
        switch (item.getItemId()) {
            case R.id.action_open_in_map:
                openPurchaseUrlIfPossible(thePurchase);
                return true;
            case R.id.action_show_only_this_card:
                // edit stuff here
                show_only_this_credit_card(thePurchase);
                return true;
            case R.id.action_show_only_this_store:
                show_only_this_store(thePurchase);
                return true;
            case R.id.action_show_only_this_bank:
                show_only_this_bank(thePurchase);
                return true;
            case R.id.action_edit_entry:
                loadPurchaseEditScreen(thePurchase);
                return true;
            case R.id.action_share:
                sharePurchaseInfo(thePurchase);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void loadPurchaseEditScreen(Purchase thePurchase) {
        Intent intent = new Intent(this, AddEditPurchase.class);
        Bundle b = new Bundle();
        b.putLong("thePurchaseId", thePurchase.getId()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    private void show_only_this_bank(Purchase thePurchase) {
        String theQuery = "select PURCHASE.* \n" +
                "FROM PURCHASE \n" +
                "JOIN CARD ON CARD.ID = PURCHASE.CARD\n" +
                "JOIN BANK ON BANK.ID = CARD.BANK\n" +
                "WHERE BANK.ID = ? \n" +
                "ORDER BY ID DESC";

        String[] parameters = {thePurchase.getCard().getBank().getId().toString()};
        List<Purchase> pList3 = Purchase.findWithQuery(Purchase.class, theQuery, parameters);
        populatListView(pList3);


    }

    private void show_only_this_store(Purchase thePurchase) {
        show_only_this_store(thePurchase.getStore().getId());
    }

    private void show_only_this_store(long storeId) {
        String[] parameters2 = {storeId + ""};
        List<Purchase> pList2 = Purchase.find(Purchase.class,
                "store = ?", parameters2,
                null, "id desc", null);
        populatListView(pList2);
    }

    private void show_only_this_credit_card(Purchase thePurchase) {
        String[] parameters = {thePurchase.getCard().getId().toString()};
        List<Purchase> pList = Purchase.find(Purchase.class,
                "card = ?", parameters,
                null, "id desc", null);
        populatListView(pList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(
                R.menu.menu_main_activity_v3
                , menu);

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
                showAboutDialog();
                return true;
            case R.id.action_export_csv:
                exportCsv();
                return true;
            case R.id.action_show_expenses_per_store:
                show_expenses_per_store();
                return true;
            case R.id.action_show_all_expenses:
                show_all_purchases();
                return true;
            case R.id.action_search:
                onSearchRequested();
            case R.id.action_manual_sms_entry:
                showManualSmsInputDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sharePurchaseInfo(Purchase thePurchase) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, thePurchase.getStringToShare());
        startActivity(Intent.createChooser(sharingIntent, "Compartilhar Localização da Loja"));
    }

    private void showManualSmsInputDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ManualSmsInputFragment editNameDialog = new ManualSmsInputFragment();
        editNameDialog.show(fm, "fragment_manual_sms_input");
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
        new MainExporter(this).execute();
    }


    void showAboutDialog() {
        String app_name = getResources().getString(R.string.app_name);
        String title = String.format("%s %s", app_name, BuildConfig.VERSION_NAME);
        DialogFragment newFragment = AboutDialogFragment.newInstance(title);
        newFragment.show(getFragmentManager(), "dialog");
    }

}
