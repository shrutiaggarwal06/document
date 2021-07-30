package com.example.doction;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
public class verificationpage extends AppCompatActivity {
    EditText et1,et2;
    Button btn;
    EditText et3;
    String name,fid;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationpage);
        et1=findViewById(R.id.name);
        et2=findViewById(R.id.fname);
        et3=findViewById(R.id.fid);
        btn=findViewById(R.id.btn);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }
    public void selectPDF(View view) {
        name = et1.getText().toString();
        fid = et3.getText().toString();
        putpdf obj = new putpdf(name, fid);
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference node = fdb.getReference("users");
        node.child(name).setValue(obj);
        AlertDialog.Builder builder = new AlertDialog.Builder(verificationpage.this);
        builder.setMessage("data updated in firebase")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
        public void upload(View view)
        {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
            //registerForActivityResult(Intent.createChooser(intent, "Select Picture"),12);
       startActivity(Intent.createChooser(intent ,"m k"));
       ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                       // if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            et2.setText(data.getDataString()
                                    .substring(data.getDataString().lastIndexOf("/")+1));
                    }
                });
    }
}