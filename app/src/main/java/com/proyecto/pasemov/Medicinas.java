package com.proyecto.pasemov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class Medicinas extends AppCompatActivity {

    MaterialButton addNoteBn;
    RecyclerView recyclerView;
    ImageButton contacto, alergia, medicina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinas);
        contacto = findViewById(R.id.contactos);
        alergia = findViewById(R.id.alergias);
        medicina = findViewById(R.id.medicina);
        addNoteBn = findViewById(R.id.addnewnotebtn3);
        addNoteBn.setOnClickListener(v ->
                startActivity(new Intent(this, AddNoteActivity.class))
        );
        contacto.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(this, Mensajes.class));
        });
        alergia.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(this, Alergias.class));
        });
        medicina.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Notes> notesList = realm.where(Notes.class).findAll().sort("createdTime", Sort.DESCENDING);
        recyclerView = findViewById(R.id.recyclerview3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapterMed myAdapter = new MyAdapterMed(getApplicationContext(), notesList);
        recyclerView.setAdapter(myAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<Notes>>() {
            @Override
            public void onChange(RealmResults<Notes> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}