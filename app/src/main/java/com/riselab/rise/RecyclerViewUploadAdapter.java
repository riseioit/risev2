package com.riselab.rise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecyclerViewUploadAdapter extends RecyclerView.Adapter<RecyclerViewUploadAdapter.ViewHolder> {

    Context context;
    List<UploadDetails> MainImageUploadInfoList;
    private Bitmap my_image;
    StorageReference ref;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageRef;


    public RecyclerViewUploadAdapter(Context context, List<UploadDetails> TempList) {
        storage = FirebaseStorage.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        this.MainImageUploadInfoList = TempList;


        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView StudentNameTextView;
        public TextView StudentResponse;
        public ImageView StudentProfile;
        public ImageView ImageUploaded;

        public ViewHolder(View itemView) {

            super(itemView);

            StudentNameTextView = (TextView) itemView.findViewById(R.id.ShowNameTextView);
            StudentProfile = itemView.findViewById(R.id.ShowProfile);
            StudentResponse = itemView.findViewById(R.id.response);
            ImageUploaded = itemView.findViewById(R.id.uploadimage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_upload, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        UploadDetails uploadDetails = MainImageUploadInfoList.get(position);
        holder.StudentNameTextView.setText(uploadDetails.getDoneby());
        String username = uploadDetails.getDoneby();
        String time = uploadDetails.getTime();
        ref = storageRef.child(time).child(username);
        holder.StudentResponse.setText(uploadDetails.getResponse());
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
                        holder.ImageUploaded.setImageBitmap(my_image);
                        my_image.equals(null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }

        });


        ref = storageRef.child(username).child("pfp").child("image");

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
                        holder.StudentProfile.setImageBitmap(my_image);
                        my_image.equals(null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }

        });


    }

    @Override
    public int getItemCount() {
        return MainImageUploadInfoList.size();
    }


}