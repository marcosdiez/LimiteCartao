package com.marcosdiez.extratocartao.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.Bank;
import com.marcosdiez.extratocartao.datamodel.Card;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.datamodel.Store;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teste();
    }

    void teste() {
        Bank b = new Bank("Bradesco");
        b.save();

        List<Bank> listBnank = Bank.listAll(Bank.class);

        for(Bank oneBank : listBnank){
            Log.d("DD", oneBank.getName());
        }

        Card c = new Card("Final 5761", b);
        c.save();
        Store s = new Store("blah");
        s.save();

        Purchase p = new Purchase(c, s, 0, 10);

        p.save();


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
