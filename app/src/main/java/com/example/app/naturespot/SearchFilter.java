package com.example.app.naturespot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by joseppmoreira on 07/11/2017.
 */

public class SearchFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_species_filter);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
    }

}
