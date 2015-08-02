package com.marcosdiez.extratocartao.datamodel;

import com.orm.SugarRecord;

/**
 * Created by Marcos on 2015-08-02.
 */
public class Store extends SugarRecord<Store> {
    String name;

    public Store() {
    }

    public Store(String name) {
        this.name = name;
    }
}
