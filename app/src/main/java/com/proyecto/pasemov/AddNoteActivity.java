package com.proyecto.pasemov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {
Realm realm;
ImageButton goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        goback = findViewById(R.id.goBack2);
        EditText TitleInput=findViewById(R.id.tituloInput);
        EditText descripcionInput=findViewById(R.id.descripcionInput);
        MaterialButton saveBtn=findViewById(R.id.btnGuardarNotas);
        Realm.init(getApplicationContext());
        realm= Realm.getDefaultInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= TitleInput.getText().toString();
                String descripcion= descripcionInput.getText().toString();
                long createdTime= System.currentTimeMillis();

                realm.beginTransaction();
                Notes note= realm.createObject(Notes.class); //Mensajes.this , tenias Mensajes en vez de Notes tambien
                note.setTitle(title);
                note.setDescription(descripcion);
                note.setCreatedTime(createdTime);
                realm.commitTransaction();
                Toast.makeText(getApplication(),"nota guardada",Toast.LENGTH_LONG).show();
            finish();
            }
        });
        goback.setOnClickListener(v -> {
            startActivity(new Intent(this,HomeActivity.class));
        });
    }
}