package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

import java.text.SimpleDateFormat;

/**
 * Created by Marcos on 2015-09-19.
 */
public class PortoSeguro extends BaseBank {
    // Porto Cartoes: Compra aprovada no cartao VISA final 1110 no valor de R$ 96,00 em 06/03 as 20h05. E C S H LTDA
    // Porto Cartoes: Compra aprovada no cartao VISA final 4117 no valor de R$ 161,52 em 20/05 as 20:07 DALEN SUPERMERCADOS.";

    protected String getRegEx() {
        return "Porto\\s+Cartoes:\\s+Compra\\s+aprovada\\s+no\\s+cartao\\s+(\\w+)\\s+final\\s+(\\d+)\\s+no\\s+valor\\s+de\\s+R.\\s+" + valor + "\\s+em\\s+(\\d+/\\d+)\\s+AS\\s+(\\d+[h:]\\d+)\\.?\\s+(.+)\\.?";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "Porto Seguro";
        // String bandeiraCartao = lastMatch.group(1);
        String nomeCartao = lastMatch.group(2);
        String valor = lastMatch.group(3);


        String diaMes = lastMatch.group(4);
        String horaMinuto = lastMatch.group(5).replace("h", ":");
        String estabelecimentoAndCidade = lastMatch.group(6);
        int estabelecimentoAndCidadeLen = estabelecimentoAndCidade.length();
        if (estabelecimentoAndCidade.charAt(estabelecimentoAndCidadeLen - 1) == '.') {
            estabelecimentoAndCidade = estabelecimentoAndCidade.substring(0, estabelecimentoAndCidadeLen - 1);
        }


        String year = new SimpleDateFormat("yyyy").format(lastSms.getDate());

        String data = diaMes + "/" + year + " " + horaMinuto;


        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimentoAndCidade);
    }
}
