package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class PortoSeguro2 extends BaseBank {
    // Porto Cartoes: Compra aprovada VISA final 9999 R¤ 99,90 em 26/11 as 13:13 PEIXE URBANO.
    protected String getRegEx() {
        return "Porto\\s+Cartoes:\\s+Compra\\s+aprovada\\s+(\\w+)\\s+final\\s+(\\d+)\\s+R.\\s+" + valor + "\\s+em\\s+(\\d+/\\d+)\\s+as\\s+(\\d+[h:]\\d+)\\.?\\s+(.+)(\\.)?";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "Porto Seguro";
        // String bandeiraCartao = lastMatch.group(1);
        String nomeCartao = lastMatch.group(2);
        String valor = lastMatch.group(3);


        String diaMes = lastMatch.group(4);
        String horaMinuto = lastMatch.group(5).replace("h", ":");
        String estabelecimentoAndCidade = lastMatch.group(6);

        int somePos = estabelecimentoAndCidade.indexOf(". ");
        if(somePos >0){
            estabelecimentoAndCidade = estabelecimentoAndCidade.substring(0, somePos);
        }

        int estabelecimentoAndCidadeLen = estabelecimentoAndCidade.length();
        if (estabelecimentoAndCidade.charAt(estabelecimentoAndCidadeLen - 1) == '.') {
            estabelecimentoAndCidade = estabelecimentoAndCidade.substring(0, estabelecimentoAndCidadeLen - 1);
        }

        estabelecimentoAndCidade = estabelecimentoAndCidade.trim();

        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());

        String data = diaMes + "/" + year + " " + horaMinuto;


        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }
}
