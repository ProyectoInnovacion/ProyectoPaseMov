package com.proyecto.pasemov;

import static io.realm.Realm.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;


public class Mensajes extends AppCompatActivity {

MaterialButton addNoteBn;
RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mensajes);

        addNoteBn= findViewById(R.id.addnewnotebtn);
        addNoteBn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mensajes.this,AddNoteActivity.class));
            }
        });

        Realm.init(getApplicationContext());
        Realm realm=Realm.getDefaultInstance();

        RealmResults<Notes> notesList = realm.where(Notes.class).findAll().sort("createdTime", Sort.DESCENDING);
        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter= new MyAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(myAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<Notes>>() {
            @Override
            public void onChange(RealmResults<Notes> notes) {

                myAdapter.notifyDataSetChanged();
            }});
    }



}