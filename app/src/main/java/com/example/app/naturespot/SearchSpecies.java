package com.example.app.naturespot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by joseppmoreira on 04/11/2017.
 */

public class SearchSpecies extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Drawer
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //Header
    ImageView pPic;
    TextView username, email;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    //ListView
    ListView listView;
    private FirebaseListAdapter<Species> mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_species);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
        final String uidExtra = intent.getStringExtra("uid");
        final String nameExtra = intent.getStringExtra("name");
        final String locationExtra = intent.getStringExtra("location");


        //ListView
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("UnknownSpecies");
        listView = (ListView) findViewById(R.id.listView);

        mAdapter = new FirebaseListAdapter<Species>(this, Species.class, R.layout.list_view_item, myRef) {
            @Override
            protected void populateView(View view, Species myObj, int position) {
                //Nome da especie e localizaçao
                ((TextView)view.findViewById(R.id.name)).setText(myObj.getName());
                ((TextView)view.findViewById(R.id.location)).setText(myObj.getLocation());
                //Ver a imagem
                try {
                    ((ImageView)view.findViewById(R.id.image)).setImageBitmap(decodeFromFirebaseBase64(myObj.getImage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        listView.setAdapter(mAdapter);
        listView.setDivider(null);

        //Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Header
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        pPic = (ImageView) header.findViewById(R.id.pPic);
        username = (TextView) header.findViewById(R.id.username);
        email = (TextView) header.findViewById(R.id.email);
        Picasso.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(pPic);
        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    public void logout(){
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MainActivity.class));
    }

    //Itens do drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_logout: {
                logout();
                break;
            }
            case R.id.nav_account: {
                Intent intent = new Intent(this, MySpots.class);
                intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(intent);
                break;
            }
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Items do drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
