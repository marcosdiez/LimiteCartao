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

    public static Store getOrCreate(String name) {
        for (Store s : Store.find(Store.class, "name = ?", name)) {
            return s;
        }
        // if we are here, no Store were found.
        Store s = new Store(name);
        s.save();
        return s;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
//        return "Store: [" + name + "]";
    }
}
