package com.proyecto.pasemov.ui.gallery.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.proyecto.pasemov.R;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        EditText TitleInput=findViewById(R.id.tituloNotas);
        EditText descripcioninput=findViewById(R.id.descripcionput);
        MaterialButton saveBtn=findViewById(R.id.btnGuardarNotas);
        Realm.init(getApplication());
        Realm realm=Realm.getDefualtInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= TitleInput.getText().toString();
                String descripcion= descripcioninput.getText().toString();
                long createdTime= System.currentTimeMillis();

                realm.beginTransaction();
                Mensajes note= realm.createObject(Mensajes.class);
                note.setTitle(title);
                note.setDescription(descripcion);
                note.setCreatedTime(createdTime);
                realm.commitTransaction();
                Toast.makeText(getApplication(),"guardado la nota",Toast.LENGTH_LONG).show();
            finish();
            }
        });
    }
}