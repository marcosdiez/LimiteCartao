package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class BancoDoBrasilDebito2 extends BaseBank {

    // banco do brasil debito 2
    // "BB: compra em POSTO MAXIMO IPIRANG, cartao de debito final 5137, RS 251,91, em 02/03/18. Saldo c/c: RS  2.939,91D.";

    protected String getRegEx() {
        //\s+(.*),\s+cartao\s+final\s+(\d+),\s
          return "BB.*:\\s+compra\\s+em\\s+(.+),\\s+cartao\\s+de\\s+debito\\s+final\\s+(\\d+),\\s+RS\\s+(\\d+[\\.\\d]?\\d*,\\d+),\\s+em\\s+(\\d\\d/\\d\\d)/(\\d+)\\.";
                //"\\s++RS\\s+(\\d+[\\.\\d]?\\d*,\\d+)\\s+(.+)\\s+cartao\\s+final\\s+(\\d+)\\s+em\\s+(\\d\\d/\\d\\d)/(\\d+)";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "BANCO DO BRASIL DEBITO";
        // String nomePomposoDoCartao = m.group(1)


        String nomeCartao = lastMatch.group(2);
        String estabelecimento = lastMatch.group(1);
        String valor = lastMatch.group(3);

        String diaMes = lastMatch.group(4);
        String year = lastMatch.group(5);
        String horaMinuto = new SimpleDateFormat("hh:mm").format(lastSms.getDate());
        String data = diaMes + "/20" + year + " " + horaMinuto;

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

}
