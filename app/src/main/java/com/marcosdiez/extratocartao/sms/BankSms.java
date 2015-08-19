package com.marcosdiez.extratocartao.sms;

/**
 * Created by Marcos on 2015-08-11.
 */
public class BankSms {
    // this data structure is only necessary because of unit tests

    public String nomeBanco="????";
    public String nomeCartao="????";
    public String timestamp="01/01/2000 00:00";
    public String amount="0,00";
    public String estabelecimentoAndCidade="????";

    public BankSms(String nomeBanco, String nomeCartao, String timestamp, String amount, String estabelecimentoAndCidade) {
        this.nomeBanco = nomeBanco;
        this.nomeCartao = nomeCartao;
        this.timestamp = timestamp;
        this.amount = amount;
        this.estabelecimentoAndCidade = estabelecimentoAndCidade;
    }
}
