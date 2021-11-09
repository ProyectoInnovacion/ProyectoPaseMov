package com.proyecto.pasemov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText correo, pass;
    Button btnLogin;
    FloatingActionButton google;
    FirebaseAuth auth;
    TextView txtRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        Entrada1= findViewById(R.id.btnRegular);
      Entrada2= findViewById(R.id.btnVisibilidad);
        Entrada1.setOnClickListener(v ->
        {
            Intent intent1 = new Intent(this, Pantalla1.class);
            ModoAccesibilidad =false;
            intent1.putExtra("Modo",ModoAccesibilidad);
            startActivity(intent1);
        } );
        Entrada2.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, Pantalla1.class);
            ModoAccesibilidad=true;
            intent2.putExtra("Modo",ModoAccesibilidad);
            startActivity(intent2);
        });*/
        correo = findViewById(R.id.editTxtLoginUser);
        pass = findViewById(R.id.editTxtLoginPass);
        auth = FirebaseAuth.getInstance();
        google = findViewById(R.id.fabGoogleLogin);
        btnLogin = findViewById(R.id.btnLogin);

        txtRegistrar = findViewById(R.id.txtRegistrar);

        txtRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(this, Registro.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            String userLogin, userPass;
            userLogin = correo.getText().toString();
            userPass = pass.getText().toString();

            if (userLogin.isEmpty()) {
                correo.setError("Ingrese su nombre por favor");
                correo.requestFocus();
            } else if (userPass.isEmpty()) {
                pass.setError("Ingrese su apellido por favor");
                pass.requestFocus();
            } else {
                auth.signInWithEmailAndPassword(userLogin, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, Pantalla1.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null){
            auth.signOut();
        }
    }
}