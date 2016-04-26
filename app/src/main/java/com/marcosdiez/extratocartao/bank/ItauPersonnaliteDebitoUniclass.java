package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class ItauPersonnaliteDebitoUniclass extends BaseBank {
    protected String getRegEx() {
        // ITAU PERSONNALITE
        // ITAU DEBITO
        // ITAU UNICLASS
        return "ITAU\\s+.+:\\s+Cartao\\s+final\\s+(....)\\s+COMPRA\\s+APROVADA\\s+(\\d+/\\d+)\\s+(\\d+:\\d+:\\d+)\\s+R\\$\\s+(\\d+[\\.\\d]?\\d*,\\d+)\\s+Local:\\s+([^\\.]+)\\.";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "ITAU";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = lastMatch.group(1);
        String estabelecimento = lastMatch.group(5);
        String valor = lastMatch.group(4);


        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());
        String diaMes = lastMatch.group(2);
        String horaMinutoSegundo = lastMatch.group(3);
        String data = diaMes + "/" + year + " " + horaMinutoSegundo;


//        Log.d(TAG, "B----");
        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }
}
