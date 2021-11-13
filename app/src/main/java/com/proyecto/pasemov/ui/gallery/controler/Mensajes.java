package com.proyecto.pasemov.ui.gallery.controler;

import static io.realm.Realm.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.proyecto.pasemov.MainActivity;
import com.proyecto.pasemov.R;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;


public class Mensajes extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialButton addNoteBn;
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    public Mensajes() {


    }

    public static Mensajes newInstance(String param1, String param2) {
        Mensajes fragment = new Mensajes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mensajes, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        addNoteBn.findViewById(R.id.addnewnotebtn);
        addNoteBn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        Realm.init(getApplicationContext());
        Realm realm=Realm.getDefaultInstance();

        RealmResults<Notes> notesList = realm.where(Notes.class).findAll().sort("createdTime", Sort.DESCENDING);
        recyclerView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyAdapter myAdapter= new MyAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(myAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<Notes>>() {
            @Override
            public void onChange(RealmResults<Notes> notes) {

                myAdapter.notifyDataSetChanged();
            }});
    }



}