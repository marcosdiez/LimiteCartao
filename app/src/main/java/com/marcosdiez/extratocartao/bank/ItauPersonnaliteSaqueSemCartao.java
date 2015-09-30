package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class ItauPersonnaliteSaqueSemCartao extends BaseBank {
    protected String getRegEx() {
        // ITAU PERSONNALITE: SAQUE APROVADO 29/09 21:08:22 R$ 150,00 Local: CX ITAU AV NACOES UN.
        return "ITAU\\s+PERSONNALITE:\\s+SAQUE\\s+APROVADO\\s+(\\d+/\\d+)\\s+(\\d+:\\d+:\\d+)\\s+R\\$\\s+(\\d+[\\.\\d]?\\d*,\\d+)\\s+Local:\\s+(.+)\\.";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "ITAU";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = "0000";
        String estabelecimento = lastMatch.group(4);
        String valor = lastMatch.group(3);


        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());
        String diaMes = lastMatch.group(1);
        String horaMinutoSegundo = lastMatch.group(2);
        String data = diaMes + "/" + year + " " + horaMinutoSegundo;

//        Log.d(TAG, "B----");
        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }
}
