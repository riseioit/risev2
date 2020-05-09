package com.riselab.rise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Vibrator;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<StudentDetails> MainImageUploadInfoList;
    private Bitmap my_image;
    StorageReference ref;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageRef;


    public RecyclerViewAdapter(Context context, List<StudentDetails> TempList) {
        storage = FirebaseStorage.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView StudentNameTextView;
        public TextView StudentNumberTextView;
        public TextView StudentEmailTextView;
        public ImageView StudentProfile;

        public ViewHolder(View itemView) {


            super(itemView);
            final Vibrator vib = (Vibrator) itemView.getContext().getSystemService(Context.VIBRATOR_SERVICE);

            StudentNameTextView = (TextView) itemView.findViewById(R.id.ShowStudentNameTextView);
            StudentNumberTextView = (TextView) itemView.findViewById(R.id.ShowStudentNumberTextView);
            StudentProfile = itemView.findViewById(R.id.ShowStudentProfile);
            StudentEmailTextView = itemView.findViewById(R.id.ShowStudentEmailId);

            StudentNumberTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vib.vibrate(200);
                    Toast.makeText(context,"Please Wait!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String no = StudentNumberTextView.getText().toString().replace("Phone No: ","+91 ");
                    intent.setData(Uri.parse("tel:" + no));
                    context.startActivity(intent);
                }
            });

            StudentEmailTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vib.vibrate(200);
                    Toast.makeText(context,"Please Wait!",Toast.LENGTH_SHORT).show();
                    String em = StudentEmailTextView.getText().toString().replace("Email Id: ","");
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",em, null));
                    context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        StudentDetails studentDetails = MainImageUploadInfoList.get(position);
        holder.StudentNameTextView.setText(studentDetails.getName());
        holder.StudentNumberTextView.setText("Phone No: " + studentDetails.getPhoneno());
        holder.StudentEmailTextView.setText("Email Id: " + studentDetails.getEmail());
//        holder.StudentProfile.setImageBitmap(studentDetails.getStudentProfile());
        String username = studentDetails.getUsername();

        ref = storageRef.child(username).child("pfp").child("image");

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finalLocalFile = localFile;
        ref.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        my_image = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
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