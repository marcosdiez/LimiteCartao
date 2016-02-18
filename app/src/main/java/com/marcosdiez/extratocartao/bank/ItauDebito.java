package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class ItauDebito extends BaseBank {

    /*
    // Compra aprovada no seu PERSON MUL VISA PLAT final 1976 - PALETERIA CAMPO BELO valor RS 9,00 em 01/08, as 13h59.
    sms send +1111 Compra aprovada no seu PERSON MUL VISA PLAT final 4242 - teste de sms valor RS 42,99 em 01/08, as 13h59.
    */

    // ITAU DEBITO: Cartao final 4607 COMPRA APROVADA 16/02 18:21:18 R$ 8,25 Local: DENTNHO&apos;.
    protected String getRegEx() {
        return "ITAU\\s+DEBITO:\\s+Cartao\\s+final\\s+(\\d+)\\s+COMPRA\\s+APROVADA\\s+(\\d+/\\d+)\\s+(\\d+:\\d+):\\d+\\s+R\\$\\s+(\\d+[\\.\\d]?\\d*,\\d+)\\s+Local:\\s+(.*)\\.";
                // ([\\w\\s]+)\\s+final\\s+(\\d+)\\s+-\\s+(.+)\\s+valor\\s+RS\\s+(\\d+[\\.\\d]?\\d*,\\d+)\\s+em\\s+(\\d+/\\d+),\\s+as\\s+(\\d+h\\d+)";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "ITAU";
        String nomeCartao = lastMatch.group(1);
        String diaMes = lastMatch.group(2);
        String horaMinuto = lastMatch.group(3);
        String valor = lastMatch.group(4);
        String estabelecimento = lastMatch.group(5);


        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());
        String data = diaMes + "/" + year + " " + horaMinuto;

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }
}
