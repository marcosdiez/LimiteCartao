package com.marcosdiez.extratocartao.glue;

import android.util.Log;

import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.sms.SMSData;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marcos on 2015-08-02.
 */
public class SmsParser {
    // BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 30/07/2015 21:02. VALOR DE $ 41,00 NO(A) PANINI PIZZA.             SAO PAULO.
    // Compra aprovada no seu PERSON MUL VISA PLAT final 1976 - PALETERIA CAMPO BELO valor RS 9,00 em 01/08, as 13h59.

    private static final String TAG = "EC-SmsParser";
    private static final String bradescoRegEx = "(\\w+) CARTOES: COMPRA APROVADA NO CARTAO FINAL (\\d+) EM (\\d+/\\d+/\\d+ \\d+:\\d+)\\. VALOR DE \\$ (\\d+,\\d+) NO\\(A\\) (.+)\\.";
    private static final String itauRegEx = "Compra aprovada no seu ([\\w\\s]+) final (\\d+) - (.+) valor RS (\\d+,\\d+) em (\\d+/\\d+), as (\\d+h\\d+)";
    private static final Pattern bradescoPattern = Pattern.compile(bradescoRegEx);
    private static final Pattern itauPattern = Pattern.compile(itauRegEx);


    public static Purchase parseSms(SMSData theSms) {
        String myBody = theSms.getBody();
        Matcher m;
//        Log.d(TAG, "A----");
//        Log.d(TAG, "groupCount:" + m.groupCount());
//            for (int i = 0; i <= m.groupCount(); i++) {
//                Log.d(TAG, "i: " + i + " " + m.group(i));
//            }

        m = bradescoPattern.matcher(myBody);
        Log.d(TAG,"Checking if SMS is from Bradesco");
        if (m.find()) { // Find each match in turn; String can't do this.
            return bradescoMatcher(m);
        }

        Log.d(TAG,"Checking if SMS is from Itau");
        m = itauPattern.matcher(myBody);
        if (m.find()) {
            return itauMatcher(m);
        }


        Log.d(TAG,"Unknown SMS");
        return null;
    }

    private static Purchase itauMatcher(Matcher m) {
        String nomeBanco = "ITAU";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = m.group(2);
        String estabelecimento = m.group(3);
        String valor = m.group(4);

        int year = Calendar.getInstance().get(Calendar.YEAR); // get year the SMS was received
        String diaMes = m.group(5);
        String hora = m.group(6);
        String data = diaMes + "/" + year + " " + hora.replace("h", ",");


//        Log.d(TAG, "B----");
        return new Purchase(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

    private static Purchase bradescoMatcher(Matcher m) {
        String nomeBanco = m.group(1);
        String nomeCartao = m.group(2);
        String data = m.group(3);
        String valor = m.group(4);
        String estabelecimentoAndCidade = m.group(5);
        int pos = estabelecimentoAndCidade.indexOf("  ");
        if (pos > 0) {
            estabelecimentoAndCidade = estabelecimentoAndCidade.substring(0, pos);
        }

//        Log.d(TAG, "B----");
        return new Purchase(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }


}


