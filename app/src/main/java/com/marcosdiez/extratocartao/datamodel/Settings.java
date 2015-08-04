package com.marcosdiez.extratocartao.datamodel;

import com.orm.SugarRecord;

/**
 * Created by Marcos on 2015-08-02.
 */
public class Settings extends SugarRecord<Settings> {
    int firstTime = 0;

    public Settings() {
    }

    public static boolean getFirstTime() {
        Settings s = getOrCreate();
        return s.firstTime == 0;
    }

    public static void setFirstTimeFalse() {
        Settings s = getOrCreate();
        s.firstTime = 1;
        s.save();
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
