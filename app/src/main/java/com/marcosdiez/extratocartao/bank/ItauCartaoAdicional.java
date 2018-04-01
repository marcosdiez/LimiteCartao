package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class ItauCartaoAdicional extends BaseBank {

    /*
    Compra aprovada no CREDICARD EXCLUS MC p/ CAROLINE KISS - SALAO DA PATRICIA valor RS 15,00 em 02/02/2016 as 15h27.
    */

    protected String getRegEx() {
        return "Compra\\s+aprovada\\s+no\\s+([\\w\\s]+)\\s+p/\\s+(.+)\\s+-\\s+(.+)\\s+valor\\s+RS\\s+" + valor + "\\s+em\\s+(\\d+/\\d+/\\d+)\\s+as\\s+(\\d+h\\d+)\\.";
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
