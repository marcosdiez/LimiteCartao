package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class Sicoob extends BaseBank {
    // String msg = "SICOOB informa, compra Credito aprovada com seu cartao MasterCard - DAFITI, em 10/02 as 07:10h, valor R$107,99";

    protected String getRegEx() {
        return "SICOOB\\s+informa,\\s+compra\\s+Credito\\s+aprovada\\s+com\\s+seu\\s+cartao\\s+(.+)\\s+-\\s+(.+),\\s+em\\s+(\\d+/\\d+)\\s+as\\s+(\\d+:\\d+)h,\\s+valor\\s+R\\$" + valor;
//        return "SICOOB informa, compra Credito aprovada com seu cartao (.+) - (.+), em (\\d+/\\d+) as (\\d+:\\d+)h, valor R$(\\d+[\\.\\d]?\\d*,\\d+)";
        //return "TRANSACAO\\s+APROVADA\\s+CARTAO\\s+FINAL\\s+(\\d+),\\s+(.+),\\s+(\\d+[\\.\\d]?\\d*,\\d+),\\s+(\\d+/\\d+/\\d+)\\s+AS\\s+(\\d+:\\d+):\\d+\\.";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "SICOOB";
        String nomeCartao = lastMatch.group(1);
        String estabelecimentoAndCidade = lastMatch.group(2);
        String diaMes = lastMatch.group(3);
        String horaMinuto = lastMatch.group(4);
        String valor = lastMatch.group(5);


        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());

        String data = diaMes + "/" + year + " " + horaMinuto;

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }
}
