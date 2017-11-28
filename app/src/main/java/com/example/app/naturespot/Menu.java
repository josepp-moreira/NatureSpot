package com.example.app.naturespot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * Created by joseppmoreira on 24/10/2017.
 */

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Drawer
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    //Header
    ImageView pPic;
    TextView username, email;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser() == null){
                    Intent myIntent = new Intent(Menu.this, MainActivity.class);
                    Menu.this.startActivity(myIntent);
                }
            }
        };

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

    public void logout(){
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MainActivity.class));
    }

    //Abrir o drawer
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
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Ir para UploadUnknownSpecies
    public void onClickPlusButton(View view)
    {
        Intent intent = new Intent(Menu.this, UploadUnknownSpecies.class);
        startActivity(intent);
    }

    //Ir para SearchSpecies
    public void onClickSearchButton(View view)
    {
        Intent intent = new Intent(Menu.this, SearchSpecies.class);
        startActivity(intent);
    }

    //Ir para MapActivity
    public void onClickMapButton(View view)
    {
        Intent intent = new Intent(Menu.this, MapActivity.class);
        startActivity(intent);
    }
}
