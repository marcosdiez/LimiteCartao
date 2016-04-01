package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class Caixa extends BaseBank {
    // CAIXA Informa: Compra aprovada no(a) RAFAELA SUELEN, R$ 25,00, 01/04 as 17:55, cartao MASTERCARD final 1549.

    protected String getRegEx() {
        return "CAIXA\\s+Informa:\\s+Compra\\s+aprovada\\s+no\\(a\\)\\s+(.+),\\s+R\\$\\s+(\\d+[\\.\\d]?\\d*,\\d+),\\s+(\\d+/\\d+)\\s+as\\s+(\\d+:\\d+),\\s+cartao\\s+(.+)\\s+final\\s+(\\d+)\\.";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "CAIXA";
        // String bandeiraCartao = lastMatch.group(5);
        String nomeCartao = lastMatch.group(6);
        String valor = lastMatch.group(2);


        String diaMes = lastMatch.group(3);
        String horaMinuto = lastMatch.group(4);
        String estabelecimentoAndCidade = lastMatch.group(1);


        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());

        String data = diaMes + "/" + year + " " + horaMinuto;


        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }
}
