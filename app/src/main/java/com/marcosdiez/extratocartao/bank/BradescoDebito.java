package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class BradescoDebito extends BaseBank {

    // String msg = "25/09/15 09:39 BRADESCO Maikon: Compra cartao deb. final 8108 de 4,30 realizada no estab. FAMILIA PEPERON.";
    protected String getRegEx() {
        return "(\\d+/\\d+/\\d+\\s+\\d+:\\d+)\\s+BRADESCO\\s+(\\w+):\\s+Compra\\s+cartao\\s+deb.\\s+final\\s+(\\d\\d\\d\\d)\\s+de\\s+(\\d+[\\.\\d]?\\d*,\\d+)\\s+realizada\\s+no\\s+estab\\.\\s+(.+)\\.";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "BRADESCO";
        String data = lastMatch.group(1);
        String nomeCartao = lastMatch.group(3);
        String valor = lastMatch.group(4);

        String estabelecimento = lastMatch.group(5);

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }
}
