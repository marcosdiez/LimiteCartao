package com.marcosdiez.extratocartao;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.marcosdiez.extratocartao.datamodel.Purchase;

/**
 * Created by Marcos on 2015-08-03.
 */
public class Gps implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static String TAG = "EC-Gps";
    private GoogleApiClient mGoogleApiClient;
    private Purchase thePurchase;


    public Gps(Context ctx) {
        buildGoogleApiClient(ctx);
    }

    public void setLocationWhenAvailable(Purchase thePurchase) {
        this.thePurchase = thePurchase;
    }

    protected synchronized void buildGoogleApiClient(Context ctx) {
        new GoogleApiClient.Builder(ctx)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            this.thePurchase.setLocation(mLastLocation);
        }
    }

}
