package com.marcosdiez.extratocartao.datamodel;

import com.orm.SugarRecord;

/**
 * Created by Marcos on 2015-08-02.
 */
public class Card extends SugarRecord<Card> {
    Bank bank;
    String name;

    public Card() {
    }

    public Card(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
    }
}
