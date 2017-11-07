package com.example.app.naturespot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by joseppmoreira on 24/10/2017.
 */

public class Menu extends AppCompatActivity {

    Button button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

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

        button = (Button) findViewById(R.id.logout);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

    }

    public void onClickPlusButton(View view)
    {
        Intent intent = new Intent(Menu.this, UploadUnknownSpecies.class);
        startActivity(intent);
    }

    public void onClickSearchButton(View view)
    {
        Intent intent = new Intent(Menu.this, SearchSpecies.class);
        startActivity(intent);
    }

    public void onClickMapButton(View view)
    {
        Intent intent = new Intent(Menu.this, MapActivity.class);
        startActivity(intent);
    }


}
