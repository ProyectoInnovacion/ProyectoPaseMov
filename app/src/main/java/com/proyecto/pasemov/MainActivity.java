package com.proyecto.pasemov;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    EditText correo, pass;
    Button btnLogin;
    FloatingActionButton google;
    FirebaseAuth auth;
    TextView txtRegistrar;

    private GoogleSignInClient mGoogleSignInClient;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


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
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        google.setOnClickListener(v -> {
            resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
        });
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();


                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                }
            }
        }
    });

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Exito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Ooops!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            auth.signOut();
        }
    }
}