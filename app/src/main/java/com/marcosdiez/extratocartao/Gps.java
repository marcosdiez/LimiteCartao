package com.marcosdiez.extratocartao;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.marcosdiez.extratocartao.datamodel.Purchase;
import com.marcosdiez.extratocartao.sms.IncomingSms;

/**
 * Created by Marcos on 2015-08-03.
 */
public class Gps implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final int maximumPrecisionTolerated = 200; // meters

    private static String TAG = "EC-Gps";
    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private Purchase thePurchase;
    private IncomingSms incomingSms;
    private Context ctx;
    private LocationRequest mLocationRequest;

    public Gps(Context ctx, IncomingSms incomingSms) {
        this.ctx = ctx;
        this.incomingSms = incomingSms;
    }

    public void setLocationWhenAvailable(Purchase thePurchase) {
        this.thePurchase = thePurchase;
        Log.d(TAG, "Initializing Google Client");
        buildGoogleApiClient(ctx);
        Log.d(TAG, "Attempting to connect to it");
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient(Context ctx) {
        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
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
        Log.d(TAG, "onConnectionSuspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "Connected to Google Client");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (locationIsAcceptable()) {
            Log.d(TAG, "Initial Location is Acceptable");
            saveLocationAndStopRequestingUpdates();
            return;
        }
        // else
        startLocationUpdates();
    }

    private void saveLocationAndStopRequestingUpdates() {
        Log.d(TAG, "Location is good enough. Saving it.");
        thePurchase.setLocation(mLastLocation);
        incomingSms.removeGpsFromList(this); // this should deallocate me
        stopLocationUpdates();
    }

    private boolean locationIsAcceptable() {
        return mLastLocation != null && mLastLocation.getAccuracy() < maximumPrecisionTolerated;
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;
        Log.d(TAG, "Received new location.");
        if (locationIsAcceptable()) {
            saveLocationAndStopRequestingUpdates();
        }
    }

    protected void startLocationUpdates() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(10000);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

}
