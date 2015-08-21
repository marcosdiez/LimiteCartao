package com.marcosdiez.extratocartao.glue;

import android.util.Log;

import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.sms.BankSms;
import com.marcosdiez.extratocartao.sms.SMSData;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marcos on 2015-08-02.
 */
public class SmsParser {


//    Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 16,90 aprovada em 08/08/15 as 16:13 SMARTCOOKING COM DE
//    Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 25,50 aprovada em 08/08/15 as 12:11 MC DONALDS MSJ
//    Santander Informa: Transacao Cartao VISA final 8304 de R$ 24,59 aprovada em 08/08/15 as 22:36 APL* ITUNES.COM/BILL
//    Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 7,00 aprovada em 09/08/15 as 14:49 EST IGUATEMI CAMPINA
//
//    Santander Informa: Transacao Visa Electron cartao final 0211 de R$ 16,96 aprovada em 27/07/15 as 06:21 PANIFICADORA RIVE
//    Santander Informa: Transacao Visa Electron cartao final 0211 de R$ 33,00 aprovada em 03/08/15 as 17:42 AUTO POSTO NOVA V


    // BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 30/07/2015 21:02. VALOR DE $ 41,00 NO(A) PANINI PIZZA.             SAO PAULO.


    // itau
    // Compra aprovada no seu PERSON MUL VISA PLAT final 1976 - PALETERIA CAMPO BELO valor RS 9,00 em 01/08, as 13h59.

    // banco do brasil
    // BB informa: compra no(a) xxxxx cartao de credito final 1234, valor RS 56,78, em 20/10/14, as 12:33.


    /*


    sms send +1111 Compra aprovada no seu PERSON MUL VISA PLAT final 4242 - teste de sms valor RS 42,99 em 01/08, as 13h59.

     */
    private static final String TAG = "EC-SmsParser";
    private static final String bradescoRegEx = "(\\w+)\\s+CARTOES:\\s+COMPRA\\s+APROVADA\\s+NO\\s+CARTAO\\s+FINAL\\s+(\\d+)\\s+EM\\s+(\\d+/\\d+/\\d+\\s+\\d+:\\d+)\\.?\\s*(NO)?\\s+VALOR\\s+DE\\s+\\$?\\s+(\\d+,\\d+)\\s+(EM\\s+\\d+\\s*X\\s*)?NO\\(A\\)\\s+(.+)\\.";
    private static final String itauRegEx = "Compra\\s+aprovada\\s+no\\s+seu\\s+([\\w\\s]+)\\s+final\\s+(\\d+)\\s+-\\s+(.+)\\s+valor\\s+RS\\s+(\\d+,\\d+)\\s+em\\s+(\\d+/\\d+),\\s+as\\s+(\\d+h\\d+)";
    private static final String bancoBrasilRegEx = "BB\\s+informa:\\s+compra\\s+no\\(a\\)\\s+(.*)\\s+cartao\\s+de\\s+credito\\s+final\\s+(\\d+),\\s+valor\\s+RS\\s+(\\d+,\\d+),\\s+em\\s+(\\d+/\\d+)/(\\d+),\\s+as\\s+(\\d+:\\d+)";

    // String msg = "Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 16,90 aprovada em 05/08/15 as 16:13 SMARTCOOKING COM DE";
    private static final String santanderRegEx = "Santander\\s+Informa:\\s+Transacao\\s+.+\\s+final\\s+(\\d+)\\s+de\\s+R\\$\\s+(\\d+,\\d+)\\s+aprovada\\s+em\\s+(\\d+/\\d+)/(\\d+)\\s+as\\s+(\\d+:\\d+)\\s+(.+)";


//    private static final String

    private static final Pattern bradescoPattern = Pattern.compile(bradescoRegEx, Pattern.CASE_INSENSITIVE);
    private static final Pattern itauPattern = Pattern.compile(itauRegEx, Pattern.CASE_INSENSITIVE);
    private static final Pattern bancoDoBrasilPattern = Pattern.compile(bancoBrasilRegEx, Pattern.CASE_INSENSITIVE);
    private static final Pattern santanderPattern = Pattern.compile(santanderRegEx, Pattern.CASE_INSENSITIVE);


    public static Purchase parseSmsPurchase(SMSData theSms) {
        BankSms theBankSms = parseSms(theSms);
        if (theBankSms != null) {
            return new Purchase(theBankSms);
        }
        return null;
    }


    public static BankSms parseSms(SMSData theSms) {
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

        Log.d(TAG, "Checking if SMS is from Banco do Brasil");
        m = bancoDoBrasilPattern.matcher(myBody);
        if (m.find()) {
            return bancoDoBrasilMatcher(m);
        }

        Log.d(TAG, "Checking if SMS is from Santander");
        m = santanderPattern.matcher(myBody);
        if (m.find()) {
            return santanderMatcher(m);
        }

        Log.d(TAG, "Unknown SMS");
        return null;
    }


    private static BankSms santanderMatcher(Matcher m) {
        String nomeBanco = "SANTANDER";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = m.group(1);
        String valor = m.group(2);

        String diaMes = m.group(3);
        String year = m.group(4);
        String horaMinuto = m.group(5);
        String data = diaMes + "/20" + year + " " + horaMinuto;


        String estabelecimento = m.group(6);

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

    private static BankSms bancoDoBrasilMatcher(Matcher m) {
        String nomeBanco = "BANCO DO BRASIL";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = m.group(2);
        String estabelecimento = m.group(1);
        String valor = m.group(3);


        String diaMesAno = m.group(4);
        String year = m.group(5);
        String horaMinuto = m.group(6);
        String data = diaMesAno + "/20" + year + " " + horaMinuto;

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }


    private static BankSms itauMatcher(Matcher m, SMSData theSms) {
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
        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

    private static BankSms bradescoMatcher(Matcher m) {
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
        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }


}


