package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class UniCred extends BaseBank {
    //   String msg = "Unicred informa: compra credito aprovada no cartao MasterCard em 02/04, 13:57, de R$94,32. Local: BIG CAMBORIU 200.";
    //   String msg = "Unicred informa: compra credito aprovada no cartao MasterCard em 02/04, 17:51, de R$358,80. Local: PAGSEGURO*PagSegu.";

    protected String getRegEx() {
        return "Unicred\\s+informa:\\s+compra\\s+credito\\s+aprovada\\s+no\\s+cartao\\s+(\\w+)\\s+em\\s+(\\d+/\\d+),\\s+(\\d+:\\d+),\\s+de\\s+R\\$\\s*" + valor + ".\\s+Local:\\s+(.+).";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "Unicred";
        // String bandeiraCartao = lastMatch.group(1);
        String nomeCartao = lastMatch.group(1);
        String valor = lastMatch.group(4);


        String diaMes = lastMatch.group(2);
        String horaMinuto = lastMatch.group(3);
        String estabelecimentoAndCidade = lastMatch.group(5);


        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());

        String data = diaMes + "/" + year + " " + horaMinuto;


        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }
}
