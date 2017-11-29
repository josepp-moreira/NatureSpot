package com.example.app.naturespot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlaceAutocompleteFragment placeAutoComplete;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                latLng = place.getLatLng();
                goToLocation(mMap, latLng);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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

        // Add a marker in Sydney and move the camera
        LatLng UA = new LatLng(40.630513, -8.657670);
        mMap.addMarker(new MarkerOptions().position(UA).title("Marker in UA"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.641007, -8.653769)).title("Unknown"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.336234, -8.561141)).title("Unknown"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.860460, -8.625972)).title("Unknown"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UA, 13));
    }

    public void goToLocation(GoogleMap googleMap, LatLng location) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
    }



}
