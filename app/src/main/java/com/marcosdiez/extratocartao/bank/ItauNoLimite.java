package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class ItauNoLimite extends BaseBank {

    /*
  "Compra aprovada no CREDICARD EXCLUS MC final 4897 - MOVIDA RENT A CAR - RS 12.000,00 em 13/08/2016 as 17h20. Utilizando 80% do limite.";
    */

    protected String getRegEx() {
        return "Compra\\s+aprovada\\s+no\\s+([\\w\\s]+)\\s+final\\s+(\\d+)\\s+-\\s+(.+)\\s+-\\s+RS\\s+" + valor + "\\s+em\\s+(\\d+/\\d+/\\d+)\\s+as\\s+(\\d+h\\d+)";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "ITAU";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = lastMatch.group(2);
        String estabelecimento = lastMatch.group(3);
        String valor = lastMatch.group(4);

        String diaMesAno = lastMatch.group(5);
        String horaMinuto = lastMatch.group(6);
        String data = diaMesAno + " " + horaMinuto.replace("h", ":");

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }
}
