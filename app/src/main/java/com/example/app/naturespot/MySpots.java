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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Ribeiro on 28/11/2017.
 */

public class MySpots extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Drawer
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //Header
    ImageView pPic;
    TextView username, email;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser uid;

    //ListView
    ListView listView;
    private FirebaseListAdapter<Species> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myspots);

        Intent intent = getIntent();
        final String uidExtra = intent.getStringExtra("uid");

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

        //ListView
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("UnknownSpecies");
        listView = (ListView) findViewById(R.id.listView);
        uid = FirebaseAuth.getInstance().getCurrentUser();

        mAdapter = new FirebaseListAdapter<Species>(this, Species.class, R.layout.list_view_item, myRef) {
            @Override
            protected void populateView(View view, Species myObj, int position) {
                if(myObj.getUid().equals(uidExtra)){
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
            }
        };
        listView.setAdapter(mAdapter);
        listView.setDivider(null);
    }

    public Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    //Items do drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                break;
            }
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(){
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MainActivity.class));
    }
}
