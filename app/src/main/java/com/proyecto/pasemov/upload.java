package com.proyecto.pasemov;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

public class upload extends AppCompatActivity {
    Button btnU, btnD;
    TextView clickUpload;
    PDFView pdfUpload;
    FirebaseStorage storage;
    Uri pdfUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        btnU = findViewById(R.id.btnUpload);
        btnU.setEnabled(false);
        btnD = findViewById(R.id.btnDelete);
        btnD.setEnabled(false);
        clickUpload = findViewById(R.id.txtClick);
        pdfUpload = findViewById(R.id.pdfViewUpload);
        storage = FirebaseStorage.getInstance();
        clickUpload.setOnClickListener(v ->
                mGetContent.launch("application/pdf")
        );
        btnU.setOnClickListener(v ->
                uploadPDF()
        );
        btnD.setOnClickListener(v -> {
            clickUpload.setVisibility(View.VISIBLE);
            btnU.setEnabled(false);
            btnU.setVisibility(View.GONE);
            btnD.setEnabled(false);
            btnD.setVisibility(View.GONE);
            clickUpload.setText("Presione aca para seleccionar su archivo");
            pdfUpload.setVisibility(View.GONE);
        });
    }

    private void uploadPDF() {
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Archivo cargando");
        progressDialog.show();
        if (pdfUri != null) {
            StorageReference reference = storage.getReference().child("pdf/pase" + UUID.randomUUID().toString()/*esto hay que cambiar para login id*/);
            reference.putFile(pdfUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(upload.this, "Pase subido exitosamente!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(upload.this, Pantalla1.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(upload.this, "Error al subir archivo", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progreso=(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Archivo "+(int)progreso+"% subido");
                }
            });
        }
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        clickUpload.setVisibility(View.GONE);
                        btnU.setEnabled(true);
                        btnD.setEnabled(true);
                        btnD.setVisibility(View.VISIBLE);
                        btnU.setVisibility(View.VISIBLE);
                        pdfUpload.setVisibility(View.VISIBLE);
                        pdfUpload.fromUri(result).load();
                        pdfUri = result;
                    }
                }
            });

}