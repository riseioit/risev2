package com.riselab.rise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;

public class dialogupload extends AppCompatDialogFragment {

    EditText uploadedittext;
    Button upload, submit;
    private final int PICK_IMAGE_REQUEST = 22;
    Uri filePath;
    StorageReference ref;
    FirebaseStorage storage;
    StorageReference storageRef;
    Bundle bundle;
    String username;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("upload");
    Vibrator vib;
    TextView status;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        storage = FirebaseStorage.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        bundle = getActivity().getIntent().getExtras();
        username = bundle.getString("username");
        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.uploaddialog, null);
        builder.setView(view);
        uploadedittext = view.findViewById(R.id.edittextupload);
        upload = view.findViewById(R.id.uploadbutton);
        submit = view.findViewById(R.id.submitbutton);
        status = view.findViewById(R.id.taskstatus);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    String time = keynode.getKey();
                    String doneby = keynode.child("doneby").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);
                SelectImage();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);
                Submitupload();
            }
        });

        return builder.create();
    }

    public void Submitupload() {
        if(filePath!=null && !(uploadedittext.getText().toString().equals(""))) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :hh:mm:ss");
            Date date = new Date();
            String newdate = dateFormat.format(date).toString();
            ref = storageRef.child(newdate).child(username);
            myRef.child(newdate).child("doneby").setValue(username);
            myRef.child(newdate).child("response").setValue(uploadedittext.getText().toString());
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Uploading");
            progressDialog.show();
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    });
            uploadedittext.setText("");
            vib.vibrate(200);
            filePath = null;
            status.setText("");
        }
        else if(filePath == null && !(uploadedittext.getText().toString().equals(""))){

            Toast.makeText(getContext(),"Please Select an image to upload",Toast.LENGTH_LONG).show();

            vib.vibrate(200);
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vib.vibrate(200);
        }
        else if (uploadedittext.getText().toString().equals("") && filePath!=null){
            Toast.makeText(getContext(),"Please Enter your Experience",Toast.LENGTH_LONG).show();

            vib.vibrate(200);
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vib.vibrate(200);
        }
        else {
            Toast.makeText(getContext(),"Please Enter your Experience and Select an Image",Toast.LENGTH_LONG).show();

            vib.vibrate(200);
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vib.vibrate(200);
        }

    }


    public void SelectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            status.setText("Uploaded");

        }
    }
}