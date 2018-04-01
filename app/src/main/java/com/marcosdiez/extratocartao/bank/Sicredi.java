package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class Sicredi extends BaseBank {
    // String msg = "SEGURANCA: TRANSACAO APROVADA CARTAO FINAL 0110, PARC=102PagSegro Propag, 362,34, 07/02/2016 AS 18:13:21.DUVIDAS CONTATAR A CENTRAL DE ATENDIMENTO";
    //                          TRANSACAO APROVADA CARTAO FINAL 0110, PARC=102MERCADOPAGO*MLIVR, 199,00, 28/01/2016 AS 13:58:16.
    //               SEGURANCA: TRANSACAO APROVADA CARTAO FINAL 3113, REST HIKARI,                14,76, 24/01/2017 AS 12:43:14 SICREDI 3003-4770/0800-724-4770

    protected String getRegEx() {
        return "TRANSACAO\\s+APROVADA\\s+CARTAO\\s+FINAL\\s+(\\d+),\\s+(.+),\\s+" + valor + ",\\s+(\\d+/\\d+/\\d+)\\s+AS\\s+(\\d+:\\d+):\\d+";

//        return "(\\w+)\\s+CARTOES:\\s+COMPRA\\s+APROVADA\\s+NO\\s+CARTAO\\s+FINAL\\s+(\\d+)\\s+EM\\s+(\\d+/\\d+/\\d+\\s+\\d+:\\d+)\\.?\\s*(NO)?\\s+VALOR\\s+DE\\s+\\$?\\s+(\\d+[\\.\\d]?\\d*,\\d+)\\s+(EM\\s+\\d+\\s*X\\s*)?NO\\(A\\)\\s+(.+)\\.";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "SICREDI";
        String nomeCartao = lastMatch.group(1);
        String estabelecimentoAndCidade = lastMatch.group(2);
        String valor = lastMatch.group(3);

        String diaMesAno = lastMatch.group(4);
        String horaMinutoSegundo = lastMatch.group(5);
        String data = diaMesAno + " " + horaMinutoSegundo;

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }
}
