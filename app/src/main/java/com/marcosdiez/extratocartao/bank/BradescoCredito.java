package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class BradescoCredito extends BaseBank {

    // BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 5761 EM 30/07/2015 21:02. VALOR DE $ 41,00 NO(A) PANINI PIZZA.             SAO PAULO.
    // BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 2561 EM 29/03/2018 07:01 NO VALOR DE R$ 1.069,14 EM 6 X NO(A) DUFRY LOJAS FRANCAS LT   GUARULHOS.";
    protected String getRegEx() {
        return "(\\w+)\\s+CARTOES:\\s+COMPRA\\s+APROVADA\\s+NO\\s+CARTAO\\s+FINAL\\s+(\\d+)\\s+EM\\s+" +
                diaMesAnoHoraMinuto + "\\.?\\s*(NO)?\\s+VALOR\\s+DE\\s+R?\\$?\\s*" + valor + "\\s+(EM\\s+\\d+\\s*X\\s*)?NO\\(A\\)\\s+(.+)\\.";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = lastMatch.group(1);
        String nomeCartao = lastMatch.group(2);
        String data = lastMatch.group(3);
        // NO = m.group(4) // "no" de "NO" VALOR DE
        String valor = lastMatch.group(5);
        // (EM 10 X)

        String estabelecimentoAndCidade = lastMatch.group(7);
        int pos = estabelecimentoAndCidade.indexOf("  ");
        if (pos > 0) {
            estabelecimentoAndCidade = estabelecimentoAndCidade.substring(0, pos);
        }

//        Log.d(TAG, "B----");
        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }
}
