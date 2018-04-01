package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class BancoDoBrasilCredito extends BaseBank {

    // banco do brasil
    // BB informa: compra no(a) xxxxx cartao de credito final 1234, valor RS 56,78, em 20/10/14, as 12:33.
    // BB avisa: compra PANIFICACAO URC, cartao final 5597, RS 39,94 - 11/10-16:18. Responda BL5597 se quiser bloquear cartao. Lim disp RS 9.534

    protected String getRegEx() {
        return "BB.*:\\s+compra\\s+(.*),\\s+cartao\\s+final\\s+(\\d+),\\s+RS\\s+" + valor + "\\s+\\-\\s+(\\d+/\\d+)\\s*-\\s*(\\d+:\\d+).";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "BANCO DO BRASIL";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = lastMatch.group(2);
        String estabelecimento = lastMatch.group(1);
        String valor = lastMatch.group(3);

        String diaMes = lastMatch.group(4);
        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());
        String horaMinuto = lastMatch.group(5);
        String data = diaMes + "/" + year + " " + horaMinuto;

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

}
