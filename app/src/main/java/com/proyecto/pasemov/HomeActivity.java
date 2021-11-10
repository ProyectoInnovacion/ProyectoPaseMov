package com.proyecto.pasemov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth auth;
    StorageReference mmFirebaseStorageRef;
    FirebaseApp app;
    FirebaseStorage mFirebaseStorage;
    PDFView pdfView;
    ListView list;
    DatabaseReference databaseReference;
    List<putPDF> uploadedPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        pdfView = findViewById(R.id.pdfPrincipal);
        list=findViewById(R.id.listView);
        uploadedPDF=new ArrayList<>();

        retrievePDF();


     /*   storageRef = FirebaseStorage.getInstance().getReference();
        app = FirebaseApp.getInstance();
        storage = FirebaseStorage.getInstance(app);
        auth = FirebaseAuth.getInstance();
        storageRef = storage.getReference().child("pdf/pase" + auth.getCurrentUser().getUid() + ".pdf");

        storageRef.getStream().addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {

                pdfView.fromStream(taskSnapshot.getStream()).load();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "Fail :" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void retrievePDF() {
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(this, upload.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(this,apuntes.class);
                startActivity(intent2);
                return true;
            case R.id.item3:
                auth.signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
                return true;
            default:
                return false;
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }
}