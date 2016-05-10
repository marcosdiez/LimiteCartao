package com.marcosdiez.extratocartao.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.Purchase;

import java.util.List;

public class ShowStores extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ShowStores self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.self = this;
        setContentView(R.layout.activity_show_stores);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private float getColorFromPurchaseValue(double purchaseValue) {
        if (purchaseValue < 10) {
            return BitmapDescriptorFactory.HUE_BLUE;
        }
        if (purchaseValue < 50) {
            return BitmapDescriptorFactory.HUE_AZURE;
        }
        if (purchaseValue < 100) {
            return BitmapDescriptorFactory.HUE_GREEN;
        }
        if (purchaseValue < 500) {
            return BitmapDescriptorFactory.HUE_YELLOW;
        }
        return BitmapDescriptorFactory.HUE_RED;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        LatLng initialPos = null;

        List<Purchase> pList = Purchase.find(Purchase.class, null, null, null, "timestamp desc", null);
        for (Purchase p : pList) {

            if (p.getLatitude() != 0 && p.getLongitude() != 0) {
                LatLng pos = new LatLng(p.getLatitude(), p.getLongitude());
                if (initialPos == null) {
                    initialPos = pos;
                }
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(p.getStore().getName())
                        .snippet(p.toMapString())
                        .icon(BitmapDescriptorFactory.defaultMarker(getColorFromPurchaseValue(p.getAmount())))
                );
            }
        }

        if(initialPos != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPos, 12));
        }
        // http://stackoverflow.com/questions/13904651/android-google-maps-v2-how-to-add-marker-with-multiline-snippet
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            // this makes the snippets multi line

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(self);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(self);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(self);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }
}
