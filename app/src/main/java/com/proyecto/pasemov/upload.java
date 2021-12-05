package com.proyecto.pasemov;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



public class upload extends AppCompatActivity {

    ImageButton clickUpload,btnU,btnD;
    PDFView pdfUpload;
    FirebaseStorage storage;
    Uri pdfUri;
    FirebaseAuth auth;
    ImageButton goback;

    StorageReference storageReference;
    DatabaseReference databaseReference;

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
        goback = findViewById(R.id.goBack);
        storage = FirebaseStorage.getInstance();
        auth= FirebaseAuth.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference(auth.getUid());
        clickUpload.setOnClickListener(v ->
                mGetContent.launch("application/pdf")
        );
        btnU.setOnClickListener(v ->
                uploadPDF()
        );
        btnD.setOnClickListener(v -> {
            clickUpload.setVisibility(View.VISIBLE);
            btnU.setEnabled(false);
            btnD.setEnabled(false);
            pdfUpload.setVisibility(View.INVISIBLE);
        });
        goback.setOnClickListener(v -> {
            startActivity(new Intent(this,HomeActivity.class));
        });


    }
    private void uploadPDF() {
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Archivo cargando");
        progressDialog.show();
        if (pdfUri != null) {
            StorageReference reference = storage.getReference().child("pdf/"+auth.getCurrentUser().getUid()+"/pase.pdf");

            reference.putFile(pdfUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        putPDF putPDF=new putPDF("pase"+auth.getCurrentUser().getUid(),pdfUri.toString());
                            databaseReference.setValue(putPDF);
                        Toast.makeText(upload.this, "Pase subido exitosamente!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(upload.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
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