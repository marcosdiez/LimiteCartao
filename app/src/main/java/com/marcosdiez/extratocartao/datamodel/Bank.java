package com.marcosdiez.extratocartao.datamodel;

import com.orm.SugarRecord;

/**
 * Created by Marcos on 2015-08-02.
 */
public class Bank extends SugarRecord<Bank> {
    String name;

    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    public static Bank getOrCreate(String name) {
        for (Bank b : Bank.find(Bank.class, "name = ?", name)) {
            return b;
        }
        // if we are here, no Bank were found.
        Bank b = new Bank(name);
        b.save();
        return b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
//        return "Bank: [" + name + "]";
    }

}
