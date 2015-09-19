package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class BancoDoBrasil extends BaseBank {

    // banco do brasil
    // BB informa: compra no(a) xxxxx cartao de credito final 1234, valor RS 56,78, em 20/10/14, as 12:33.

    protected String getRegEx() {
        return "BB\\s+informa:\\s+compra\\s+no\\(a\\)\\s+(.*)\\s+cartao\\s+de\\s+credito\\s+final\\s+(\\d+),\\s+valor\\s+RS\\s+(\\d+[\\.\\d]?\\d*,\\d+),\\s+em\\s+(\\d+/\\d+)/(\\d+),\\s+as\\s+(\\d+:\\d+)";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "BANCO DO BRASIL";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = lastMatch.group(2);
        String estabelecimento = lastMatch.group(1);
        String valor = lastMatch.group(3);

        String diaMesAno = lastMatch.group(4);
        String year = lastMatch.group(5);
        String horaMinuto = lastMatch.group(6);
        String data = diaMesAno + "/20" + year + " " + horaMinuto;

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

}
