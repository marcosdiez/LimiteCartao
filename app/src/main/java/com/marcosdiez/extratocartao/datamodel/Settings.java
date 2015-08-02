package com.marcosdiez.extratocartao.datamodel;

import com.orm.SugarRecord;

/**
 * Created by Marcos on 2015-08-02.
 */
public class Settings extends SugarRecord<Settings> {
    int lastAndroidSmsId = 0;

    public Settings() {
    }


    public static void setLastAndroidSmsId(int id){
        Settings s = getOrCreate();
        s.lastAndroidSmsId = id;
        s.save();
    }

    public static int getLastAndroidSmsId(){
        Settings s = getOrCreate();
        return s.lastAndroidSmsId;
    }



    private static Settings getOrCreate() {
        for (Settings s : Settings.listAll(Settings.class)) {
            return s;
        }
        // if we are here, no Settings were found.
        Settings b = new Settings();
        return b;
    }

}
