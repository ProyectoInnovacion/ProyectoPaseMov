package com.proyecto.pasemov;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Registro extends AppCompatActivity {

    EditText nombre, apellido, correo, pass1, pass2;
    Button btn;
    TextView devolver;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.usuario);
        apellido = findViewById(R.id.apellido);
        correo = findViewById(R.id.correo);
        pass1 = findViewById(R.id.contrasena1);
        pass2 = findViewById(R.id.contrasena2);
        btn = findViewById(R.id.btnRegistrase);
        devolver = findViewById(R.id.txtDevolverse);

        auth = FirebaseAuth.getInstance();
        devolver.setOnClickListener(v -> {
            Intent intent6 = new Intent(this, MainActivity.class);
            startActivity(intent6);
        });
        btn.setOnClickListener(v -> {
            String emailID, pass11, pass22, usuarioNombre, usuarioApellido;
            usuarioNombre = nombre.getText().toString();
            usuarioApellido = apellido.getText().toString();
            pass11 = pass1.getText().toString();
            pass22 = pass2.getText().toString();
            emailID = correo.getText().toString();

            if (usuarioNombre.isEmpty()) {
                nombre.setError("Ingrese su nombre por favor");
                nombre.requestFocus();
            } else if (usuarioApellido.isEmpty()) {
                apellido.setError("Ingrese su apellido por favor");
                apellido.requestFocus();
            } else if (emailID.isEmpty()) {
                correo.setError("Ingrese su correo por favor");
                correo.requestFocus();
            } else if (pass11.isEmpty()) {
                pass1.setError("Ingrese su contraseña por favor");
                pass1.requestFocus();
            } else if (pass22.isEmpty()) {
                pass2.setError("Ingrese su contraseña de nuevo por favor");
                pass2.requestFocus();
            } else if (!pass11.equals(pass22)) {
                Toast.makeText(this, "Contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(emailID, pass11).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Registro.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Registro.this, "Oops!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Registro.this, MainActivity.class));
            finish();
        }
    }


}