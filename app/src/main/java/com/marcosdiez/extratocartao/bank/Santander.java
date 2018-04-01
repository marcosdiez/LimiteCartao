package com.marcosdiez.extratocartao.bank;

import com.marcosdiez.extratocartao.sms.BankSms;

/**
 * Created by Marcos on 2015-09-19.
 */
public class Santander extends BaseBank {


//    Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 16,90 aprovada em 08/08/15 as 16:13 SMARTCOOKING COM DE
//    Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 25,50 aprovada em 08/08/15 as 12:11 MC DONALDS MSJ
//    Santander Informa: Transacao Cartao VISA final 8304 de R$ 24,59 aprovada em 08/08/15 as 22:36 APL* ITUNES.COM/BILL
//    Santander Informa: Transacao Cartao Mastercard final 3031 de R$ 7,00 aprovada em 09/08/15 as 14:49 EST IGUATEMI CAMPINA
//
//    Santander Informa: Transacao Visa Electron cartao final 0211 de R$ 16,96 aprovada em 27/07/15 as 06:21 PANIFICADORA RIVE
//    Santander Informa: Transacao Visa Electron cartao final 0211 de R$ 33,00 aprovada em 03/08/15 as 17:42 AUTO POSTO NOVA V


    protected String getRegEx() {
        return "Santander\\s+Informa:\\s+Transacao\\s+.+\\s+final\\s+(\\d+)\\s+de\\s+R\\$\\s+" + valor + "\\s+aprovada\\s+em\\s+(\\d+/\\d+)/(\\d+)\\s+as\\s+(\\d+:\\d+)\\s+(.+)";
    }

    public BankSms getBankSmsHelper() {
        String nomeBanco = "SANTANDER";
        // String nomePomposoDoCartao = m.group(1)
        String nomeCartao = lastMatch.group(1);
        String valor = lastMatch.group(2);

        String diaMes = lastMatch.group(3);
        String year = lastMatch.group(4);
        String horaMinuto = lastMatch.group(5);
        String data = diaMes + "/20" + year + " " + horaMinuto;

        String estabelecimento = lastMatch.group(6);

        return new BankSms(nomeBanco, nomeCartao, data, valor, estabelecimento);
    }

}
