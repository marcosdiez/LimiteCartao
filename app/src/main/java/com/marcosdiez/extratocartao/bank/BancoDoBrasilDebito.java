package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class BancoDoBrasilDebito extends BaseBank {

    // banco do brasil
    // String msg = "BB: compra RS  37,50 LANCH0S BURDOG cartao final 4689 em 06/06/16";

    protected String getRegEx() {
        //\s+(.*),\s+cartao\s+final\s+(\d+),\s
        return "BB.*:\\s+compra\\s++RS\\s+" + valor + "\\s+(.+)\\s+cartao\\s+final\\s+(\\d+)\\s+em\\s+(\\d\\d/\\d\\d)/(\\d+)";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "BANCO DO BRASIL DEBITO";
        // String nomePomposoDoCartao = m.group(1)


        String nomeCartao = lastMatch.group(3);
        String estabelecimento = lastMatch.group(2);
        String valor = lastMatch.group(1);

        String diaMes = lastMatch.group(4);
        String year = lastMatch.group(5);
        String horaMinuto = new SimpleDateFormat("hh:mm").format(lastSms.getDate());
        String data = diaMes + "/20" + year + " " + horaMinuto;

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

}
