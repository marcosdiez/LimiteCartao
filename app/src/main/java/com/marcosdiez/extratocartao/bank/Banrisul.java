package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class Banrisul extends BaseBank {


    protected String getRegEx() {
        // Banrisul informa: APROVADA TRANSACAO CARTAO DE CREDITO DE 150,00 AS 13:19:31 DE 05/05/2016 CARTAO FINAL 2123 FARMA LEVES

        return "Banrisul\\s+informa:\\s+APROVADA\\s+TRANSACAO\\s+CARTAO\\s+DE\\s+CREDITO\\s+DE\\s+(\\d+[\\.\\d]?\\d*,\\d+)" +
                "\\s+AS\\s+(\\d+:\\d+:\\d+)\\s+DE\\s+(\\d+/\\d+/\\d+)\\s+CARTAO\\s+FINAL\\s+(\\d+)\\s+(.+)";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "BANRISUL";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = lastMatch.group(4);
        String valor = lastMatch.group(1);

        String diaMesAno = lastMatch.group(3);
        String horaMinutoSegundo = lastMatch.group(2);
        String data = diaMesAno + " " + horaMinutoSegundo;

        String estabelecimento = lastMatch.group(5);

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

}
