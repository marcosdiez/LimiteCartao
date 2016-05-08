package com.marcosdiez.extratocartao.export;

import android.support.annotation.NonNull;
import android.util.Log;

import com.marcosdiez.extratocartao.Util;
import com.marcosdiez.extratocartao.datamodel.Card;
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
public class Ofx {
    private static String TAG = "EC-CSV";

    public static File createOfxOfPurchases() {
        Log.d(TAG, "Export OFX");
        List<Purchase> pList = Purchase.find(Purchase.class, null, null, null, "id", null);
        try {

            String sufix = (new SimpleDateFormat("yyyy-MM-dd-HH-mm")).format(new Date());

            File outputFile = File.createTempFile("extrato_cartao_" + sufix + "_", ".ofx");
            outputFile.setReadable(true, false);
            FileWriter out = new FileWriter(outputFile);
            header(out);
            addCards(out);
            out.write("</OFX>\n");
            out.close();
            Log.d(TAG, "File Created: " + outputFile);
            return outputFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void header(FileWriter out) throws IOException {
        String now = now();

        out.write("OFXHEADER:100\n" +
                "DATA:OFXSGML\n" +
                "VERSION:102\n" +
                "SECURITY:NONE\n" +
                "ENCODING:USASCII\n" +
                "CHARSET:1252\n" +
                "COMPRESSION:NONE\n" +
                "OLDFILEUID:NONE\n" +
                "NEWFILEUID:NONE\n\n\n" +
                "<OFX>\n" +
                "\t<SIGNONMSGSRSV1>\n" +
                "\t\t<SONRS>\n" +
                "\t\t\t<STATUS>\n" +
                "\t\t\t\t<CODE>0\n" +
                "\t\t\t\t<SEVERITY>INFO\n" +
                "\t\t\t</STATUS>\n" +
                "\t\t\t<DTSERVER>" + now + "\n" +
                "\t\t\t<LANGUAGE>POR\n" +
                "\t\t</SONRS>\n" +
                "\t</SIGNONMSGSRSV1>\n");

    }

    @NonNull
    private static String now() {
        return Util.ofxDateFormat.format(new Date());
    }

    private static void addCards(FileWriter out) throws IOException {
        out.write("\t<CREDITCARDMSGSRSV1>\n");
        List<Card> cList = Card.find(Card.class, null, null, null, "id", null);
        for (Card aCard : cList) {
            List<Purchase> pList = Purchase.find(Purchase.class, "card = ?", new String[]{aCard.getId().toString()}, null, "id", null);
            out.write(
                    "\t\t<CCSTMTTRNRS>\n" +
                            "\t\t\t<TRNUID>1001\n" +
                            "\t\t\t<STATUS>\n" +
                            "\t\t\t\t<CODE>0\n" +
                            "\t\t\t\t<SEVERITY>INFO\n" +
                            "\t\t\t</STATUS>\n" +
                            "\t\t\t<CCSTMTRS>\n" +
                            "\t\t\t\t<CURDEF>BRL\n" +
                            "\t\t\t\t<CCACCTFROM>\n" +
                            "\t\t\t\t\t<ACCTID>" + aCard.getBank() + "-" + aCard.getName() + "\n" +
                            "\t\t\t\t</CCACCTFROM>\n" +
                            "\t\t\t<BANKTRANLIST>\n" +
                            "\t\t\t\t<DTSTART>19900101000001\n" +
                            "\t\t\t\t<DTEND>" + now() + "\n");

            double total = 0;
            for (Purchase aPurchase : pList) {
                total += aPurchase.getAmount();
                out.write(aPurchase.toOfxLine());
            }


            out.write(String.format("\t\t</BANKTRANLIST>\n" +
                    "\t\t\t<LEDGERBAL>\n" +
                    "\t\t\t\t<BALAMT>-%.2f\n" +
                    "\t\t\t\t<DTASOF>00000000\n" +
                    "\t\t\t</LEDGERBAL>\n" +
                    "\t\t\t</CCSTMTRS>\n" +
                    "\t\t</CCSTMTTRNRS>\n", total));

        }
        out.write("</CREDITCARDMSGSRSV1>\n");

    }

}
