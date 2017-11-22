package com.example.app.naturespot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by joseppmoreira on 04/11/2017.
 */

public class UploadUnknownSpecies extends AppCompatActivity{

    FirebaseDatabase database;
    DatabaseReference myRef, numOfChilds;
    int n;
    String image, name, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_unknown_species);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
    }

    public void uploadSpecies(View view){
        //Conectar a base de dados
        database = FirebaseDatabase.getInstance();

        //Verificar quantas childs o "UnknownSpecies" possui recebendo o valor de "NumberOfChilds"
        database.getReference("UnknownSpecies").child("NumberOfChildren").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                n = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //Referencia das nodes "Species" e "NumberOfChilds"
        myRef = database.getReference("UnknownSpecies").child("Species"+n);
        numOfChilds = database.getReference("UnknownSpecies").child("NumberOfChildren");

        //Criar um especie nova
        NewSpecies ns = new NewSpecies("image","unknown", "NY");

        //Enviar os valores para a base de dados
        myRef.setValue(ns);
        numOfChilds.setValue(n+1);
    }
}