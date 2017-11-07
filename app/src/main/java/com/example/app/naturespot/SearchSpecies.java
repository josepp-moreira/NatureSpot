package com.example.app.naturespot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by joseppmoreira on 04/11/2017.
 */

public class SearchSpecies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_species);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
    }

    public void onClickFilterButton(View view)
    {
        Intent intent = new Intent(SearchSpecies.this, SearchFilter.class);
        startActivity(intent);
    }


}
