package com.riselab.rise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecyclerViewUploadAdapter extends RecyclerView.Adapter<RecyclerViewUploadAdapter.ViewHolder> {

    Context context;
    List<UploadDetails> MainImageUploadInfoList;
    private Bitmap my_image;
    StorageReference ref;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageRef;
    Vibrator vib;


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
        public TextView timedifference;

        public ViewHolder(final View itemView) {

            super(itemView);

            StudentNameTextView = (TextView) itemView.findViewById(R.id.ShowNameTextView);
            StudentProfile = itemView.findViewById(R.id.ShowProfile);
            StudentResponse = itemView.findViewById(R.id.response);
            ImageUploaded = itemView.findViewById(R.id.uploadimage);
            timedifference = itemView.findViewById(R.id.timediff);





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
        final UploadDetails uploadDetails = MainImageUploadInfoList.get(position);
        holder.ImageUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Intent intent = new Intent(itemView.getContext(),dialoghome.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("usernamedialog",StudentNameTextView.getText().toString());
//                    bundle.putString("timedialog",timedifference.getText().toString());
//                    bundle.putString("responsedialog",StudentResponse.getText().toString());
//                    intent.putExtras(bundle);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("usernamedialog",holder.StudentNameTextView.getText().toString());
                editor.putString("timedialog",uploadDetails.getTime().toString());
                editor.putString("responsedialog",holder.StudentResponse.getText().toString());
                editor.putString("timediffdialog",holder.timedifference.getText().toString());
                editor.apply();
                vib = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(200);
                dialoghome dialog = new dialoghome();
                dialog.show( ((AppCompatActivity) context).getSupportFragmentManager(),"Upload Dialog");
            }
        });

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :hh:mm:ss");
        Date date = new Date();
        String newdate = dateFormat.format(date).toString();
        Date endDate = null, startDate = null;
        try {
            endDate = dateFormat.parse(newdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            startDate = dateFormat.parse(uploadDetails.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long diffInDays = different / daysInMilli;
        different = different % daysInMilli;

        long diffInHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long diffInMin = different / minutesInMilli;
        different = different % minutesInMilli;

        long diffInSec = different / secondsInMilli;



        if (diffInDays == 0 && diffInHours !=0){
            holder.timedifference.setText((int) diffInHours+" h "+diffInMin+" m " );
        }
        else if (diffInDays ==0 && diffInHours == 0 && diffInMin !=0)
        {
            holder.timedifference.setText((int)diffInMin+" m "+diffInSec+" s " );
        }
        else if (diffInDays ==0 && diffInHours == 0 && diffInMin ==0){
            holder.timedifference.setText((int)diffInSec+" s " );
        }
        else if(diffInDays !=0){
            holder.timedifference.setText((int) diffInDays +" d ");
        }

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