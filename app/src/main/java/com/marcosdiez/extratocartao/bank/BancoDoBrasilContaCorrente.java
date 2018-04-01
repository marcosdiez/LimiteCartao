package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class BancoDoBrasilContaCorrente extends BaseBank {

    // banco do brasil debito 2
    // "BB: debito referente transferencia em 07/03/18, as 13:02, RS  265,00, canal Internet. Responda NR0292 para bloquear suas senhas.";

    protected String getRegEx() {
        return "BB:\\s+debito\\s+referente\\s+transferencia\\s+em\\s+(\\d\\d/\\d\\d)/(\\d+),\\s+as\\s+(\\d+:\\d+),\\s+RS\\s+" + valor + ",\\s+canal\\s+([\\w\\s\\d]+)\\.";
    }
    public BankSms getBankSmsHelper() {
        String nomeBanco = "BANCO DO BRASIL";
//
        String nomeCartao = "Conta Corrente";
        String estabelecimento = lastMatch.group(5);
        String valor = lastMatch.group(4);
//
        String diaMes = lastMatch.group(1);
        String year = lastMatch.group(2);
        String horaMinuto = lastMatch.group(3);
        String data = diaMes + "/20" + year + " " + horaMinuto;
//
        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

}
