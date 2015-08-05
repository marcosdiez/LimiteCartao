package com.marcosdiez.extratocartao.glue;

import android.util.Log;

import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.sms.SMSData;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marcos on 2015-08-02.
 */
public class SmsParser {
    // BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 30/07/2015 21:02. VALOR DE $ 41,00 NO(A) PANINI PIZZA.             SAO PAULO.
    // Compra aprovada no seu PERSON MUL VISA PLAT final 1976 - PALETERIA CAMPO BELO valor RS 9,00 em 01/08, as 13h59.
/*

sms send +1111 Compra aprovada no seu PERSON MUL VISA PLAT final 4242 - teste de sms valor RS 42,99 em 01/08, as 13h59.

 */
    private static final String TAG = "EC-SmsParser";
    private static final String bradescoRegEx = "(\\w+)\\s+CARTOES:\\s+COMPRA\\s+APROVADA\\s+NO\\s+CARTAO\\s+FINAL\\s+(\\d+)\\s+EM\\s+(\\d+/\\d+/\\d+\\s+\\d+:\\d+)\\.?\\s*(NO)?\\s+VALOR\\s+DE\\s+\\$?\\s+(\\d+,\\d+)\\s+(EM\\s+\\d+\\s*X\\s*)?NO\\(A\\)\\s+(.+)\\.";
    private static final String itauRegEx = "Compra\\s+aprovada\\s+no\\s+seu\\s+([\\w\\s]+)\\s+final\\s+(\\d+)\\s+-\\s+(.+)\\s+valor\\s+RS\\s+(\\d+,\\d+)\\s+em\\s+(\\d+/\\d+),\\s+as\\s+(\\d+h\\d+)";
    private static final Pattern bradescoPattern = Pattern.compile(bradescoRegEx, Pattern.CASE_INSENSITIVE);
    private static final Pattern itauPattern = Pattern.compile(itauRegEx, Pattern.CASE_INSENSITIVE);


    public static Purchase parseSms(SMSData theSms) {
        String myBody = theSms.getBody();
        Matcher m;

        m = bradescoPattern.matcher(myBody);
        Log.d(TAG, "Checking if SMS is from Bradesco");
        if (m.find()) { // Find each match in turn; String can't do this.
            return bradescoMatcher(m);
        }

        Log.d(TAG, "Checking if SMS is from Itau");
        m = itauPattern.matcher(myBody);
        if (m.find()) {
            return itauMatcher(m, theSms);
        }


        Log.d(TAG, "Unknown SMS");
        return null;
    }

    private static Purchase itauMatcher(Matcher m, SMSData theSms) {
        String nomeBanco = "ITAU";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = m.group(2);
        String estabelecimento = m.group(3);
        String valor = m.group(4);


        String year = new SimpleDateFormat("yyyy").format(theSms.getDate());
        String diaMes = m.group(5);
        String horaMinuto = m.group(6);
        String data = diaMes + "/" + year + " " + horaMinuto.replace("h", ":");


//        Log.d(TAG, "B----");
        return new Purchase(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

    private static Purchase bradescoMatcher(Matcher m) {
        String nomeBanco = m.group(1);
        String nomeCartao = m.group(2);
        String data = m.group(3);
        // NO = m.group(4) // "no" de "NO" VALOR DE
        String valor = m.group(5);
        // (EM 10 X)

        String estabelecimentoAndCidade = m.group(7);
        int pos = estabelecimentoAndCidade.indexOf("  ");
        if (pos > 0) {
            estabelecimentoAndCidade = estabelecimentoAndCidade.substring(0, pos);
        }

//        Log.d(TAG, "B----");
        return new Purchase(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }


}


