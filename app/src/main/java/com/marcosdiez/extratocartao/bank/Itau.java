package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class Itau extends BaseBank {

    /*
    // Compra aprovada no seu PERSON MUL VISA PLAT final 1976 - PALETERIA CAMPO BELO valor RS 9,00 em 01/08, as 13h59.
    sms send +1111 Compra aprovada no seu PERSON MUL VISA PLAT final 4242 - teste de sms valor RS 42,99 em 01/08, as 13h59.
    */

    protected String getRegEx() {
        return "Compra\\s+aprovada\\s+no\\s+seu\\s+([\\w\\s]+)\\s+final\\s+(\\d+)\\s+-\\s+(.+)\\s+valor\\s+RS\\s+(\\d+[\\.\\d]?\\d*,\\d+)\\s+em\\s+(\\d+/\\d+),\\s+as\\s+(\\d+h\\d+)";
    }

    public BankSms getBankSms() {
        String nomeBanco = "ITAU";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = lastMatch.group(2);
        String estabelecimento = lastMatch.group(3);
        String valor = lastMatch.group(4);

        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());
        String diaMes = lastMatch.group(5);
        String horaMinuto = lastMatch.group(6);
        String data = diaMes + "/" + year + " " + horaMinuto.replace("h", ":");

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }
}
