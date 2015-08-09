package com.marcosdiez.extratocartao.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.Util;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.glue.PurchaseListAdapter;

import java.util.List;

public class MainActivityV3 extends AppCompatActivity {

    ListView purchaseListView;
    Context mySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_v3);
        mySelf = this;

        Util.loadStoredSmsData(this);
        initListView();
    }

    private void initListView() {
        purchaseListView = (ListView) findViewById(R.id.purchase_list);
        List<Purchase> pList = Purchase.listAll(Purchase.class);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
