package com.riselab.rise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class dialoghome extends AppCompatDialogFragment {

    TextView username, time , response;
    ImageView profile , uploadedimage;
    ProgressDialog progressDialog;

    private Bitmap my_image;
    Bitmap icon;
    StorageReference ref;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageRef;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.homedialog, null);
        builder.setView(view);
        username = view.findViewById(R.id.ShowNameTextViewdialog);
        time = view.findViewById(R.id.timediffdialog);
        response = view.findViewById(R.id.responsedialog);
        profile = view.findViewById(R.id.ShowProfiledialog);
        uploadedimage = view.findViewById(R.id.uploadimagedialog);
        progressDialog=new ProgressDialog(getContext() , R.style.MyAlertDialogStyle);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String userS = preferences.getString("usernamedialog", String.valueOf(0));
        String timeS = preferences.getString("timedialog", String.valueOf(1));
        String responseS = preferences.getString("responsedialog", String.valueOf(2));
        String timmdS = preferences.getString("timediffdialog", String.valueOf(3));

        uploadedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(200);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                        icon, "Title", null);
                Uri imageUri =  Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(share, "Select"));
            }
        });

        username.setText(userS);
        time.setText(timmdS);
        response.setText(responseS);

        storage = FirebaseStorage.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        ref = storageRef.child(timeS).child(userS);

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File finalLocalFile = localFile;
        final File finalLocalFile2 = finalLocalFile;

        ref.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        my_image = BitmapFactory.decodeFile(finalLocalFile2.getAbsolutePath());
                        uploadedimage.setImageBitmap(my_image);
                        icon = my_image;
                        my_image.equals(null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
                Toast.makeText(getContext(),"Failed to load image, Please try again! ",Toast.LENGTH_LONG).show();
            }

        });



        ref = storageRef.child(userS).child("pfp").child("image");

        localFile = null;
        try {
            localFile = File.createTempFile("images", "jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finalLocalFile = localFile;
        final File finalLocalFile1 = finalLocalFile;
        ref.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        my_image = BitmapFactory.decodeFile(finalLocalFile1.getAbsolutePath());
                        profile.setImageBitmap(my_image);
                        my_image.equals(null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...


            }

        });

        return builder.create();
    }
}
