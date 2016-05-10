package com.marcosdiez.extratocartao.export;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * Created by Marcos on 2016-05-09.
 */
public class DbFileProvider extends android.support.v4.content.FileProvider {
    // http://stackoverflow.com/questions/29376072/share-sqlite-database-from-android-app-without-intermediate-copy
    public static final String TAG = "DbFileProvider";

    private static String getMetaDataString(Context c, String name) {
        PackageManager pm = c.getPackageManager();
        String value = null;

        try {
            ApplicationInfo ai = pm.getApplicationInfo(c.getPackageName(), PackageManager.GET_META_DATA);
            value = ai.metaData.getString(name);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't find config value: " + name);
        }

        return value;
    }

    public Uri getDatabaseURI(Context c) {
        // https://developer.android.com/reference/android/support/v4/content/FileProvider.html

        String dbName = getMetaDataString(c, "DATABASE");
        String dbPath = c.getDatabasePath(dbName).getAbsolutePath();
        File exportFile = new File(dbPath);

        return getFileUri(c, exportFile);
    }

    public Uri getFileUri(Context c, File f) {
        return getUriForFile(c, "com.marcosdiez.extratocartao.fileprovider", f);
    }

}