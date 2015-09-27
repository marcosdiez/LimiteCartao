package com.marcosdiez.extratocartao.export;

import android.util.Log;

import com.marcosdiez.extratocartao.datamodel.Purchase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcos on 2015-09-27.
 */
public class Csv {
    private static String TAG = "EC-CSV";


    public static File createCsvOfPurchases() {
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
}
