package com.proyecto.pasemov;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;


public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    StorageReference storageReference;
    FirebaseApp app;
    FirebaseStorage storage;
    PDFView pdfView;
    LinearLayout llnotes;
    FloatingActionButton fabMain, fabUpload, fabExit, fabApuntes;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        pdfView = findViewById(R.id.pdfPrincipal);
        storageReference = FirebaseStorage.getInstance().getReference();
        app = FirebaseApp.getInstance();
        storage = FirebaseStorage.getInstance(app);
        llnotes = findViewById(R.id.llnotes);
        fabMain = findViewById(R.id.fabMain);
        fabUpload = findViewById(R.id.fabUpload);
        fabExit = findViewById(R.id.fabExit);
        fabApuntes = findViewById(R.id.fabApuntes);
        fabExit.setVisibility(View.GONE);
        fabApuntes.setVisibility(View.GONE);
        fabUpload.setVisibility(View.GONE);


        storageReference = storage.getReference().child("pdf/" + auth.getCurrentUser().getUid() + "/pase.pdf");//

        storageReference.getStream().addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {
                pdfView.fromStream(taskSnapshot.getStream()).load();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "Error: No se encontro PDF.", Toast.LENGTH_SHORT).show();
            }
        });
        fabMain.setOnClickListener(v -> {
            if(fabApuntes.getVisibility()==View.GONE){
                fabExit.setVisibility(View.VISIBLE);
                fabApuntes.setVisibility(View.VISIBLE);
                fabUpload.setVisibility(View.VISIBLE);
                fabMain.setImageResource(R.drawable.ic_baseline_remove_24);
            }else{
                fabExit.setVisibility(View.GONE);
                fabApuntes.setVisibility(View.GONE);
                fabUpload.setVisibility(View.GONE);
                fabMain.setImageResource(R.drawable.ic_baseline_add_24);
            }

        });

        fabUpload.setOnClickListener(v -> {
            Intent intent = new Intent(this, upload.class);
            startActivity(intent);
        });
        fabApuntes.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, Mensajes.class);
            startActivity(intent2);
        });
        fabExit.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        });
        fabExit.setOnLongClickListener(v -> {
            AuthUI.getInstance()
                    .signOut(HomeActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>(){

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(HomeActivity.this, MainActivity.class));
                            finish();

                        }
                    });
                return true;}
        );
    }



  /*  @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:

                if(findViewById(R.id.llnotes).getVisibility()==View.VISIBLE){

            findViewById(R.id.llnotes).setVisibility(View.INVISIBLE);
//                findViewById(R.id.llupload).setVisibility(View.INVISIBLE);
                upload.setVisibility(View.INVISIBLE);
                exit.setVisibility(View.INVISIBLE);
                notepad.setVisibility(View.INVISIBLE);
                    ConstraintLayout.LayoutParams params = new  ConstraintLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 0, 0);
                    findViewById(R.id.RL).setLayoutParams(params);
                }
                else{
                    findViewById(R.id.llnotes).setVisibility(View.VISIBLE);
//                findViewById(R.id.llupload).setVisibility(View.INVISIBLE);
                    upload.setVisibility(View.VISIBLE);
                    exit.setVisibility(View.VISIBLE);
                    notepad.setVisibility(View.VISIBLE);
                    ConstraintLayout.LayoutParams params = new  ConstraintLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 0, -100);
                    findViewById(R.id.RL).setLayoutParams(params);
                }
                break;
        }

    }*/
}