package com.example.app.naturespot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by joseppmoreira on 04/11/2017.
 */

public class UploadUnknownSpecies extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef, numOfChilds;
    long n;
    String image, name, location;
    FirebaseUser uid;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView coords;

    private GoogleMap mMap;
    private PlaceAutocompleteFragment placeAutoComplete;
    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_unknown_species);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
        uid = FirebaseAuth.getInstance().getCurrentUser();

        //Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Localizaçao do telemovel
        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                latLng = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

    }

    //Items do drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void uploadSpecies(View view){
        //Conectar a base de dados
        database = FirebaseDatabase.getInstance();
        EditText speciesName = (EditText) findViewById(R.id.species_name);

        //Referencia das nodes "Species" e "NumberOfChilds"
        myRef = database.getReference();

        //Criar um especie nova
        NewSpecies ns = new NewSpecies("image", speciesName.getText().toString(), latLng.toString(), uid.getUid());

        //Enviar os valores para a base de dados
        myRef.child("UnknownSpecies").push().setValue(ns);
    }
}