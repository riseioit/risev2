package com.riselab.rise.ui.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.riselab.rise.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageView userimage;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageRef;
    String username , emailid , loginname , phoneno;
    StorageReference ref;
    TextView loginuser , loginemail , loginphn;
     ArrayList<String> arrayList1 = new ArrayList<>();
     ArrayList<String> arrayList2 = new ArrayList<>();
     ArrayList<String> arrayList3 = new ArrayList<>();
     ArrayList<String> arrayList4 = new ArrayList<>();
    private Bitmap my_image;

    private final int PICK_IMAGE_REQUEST = 22;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        storage = FirebaseStorage.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        Bundle bundle = getActivity().getIntent().getExtras();

        username = bundle.getString("username");
        emailid = bundle.getString("email");
        loginname = bundle.getString("loginname");
        phoneno = bundle.getString("phnno");
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        int no = 0;
        arrayList1 = bundle.getStringArrayList("emailids");
        arrayList2 = bundle.getStringArrayList("types");
        arrayList3 = bundle.getStringArrayList("names");
        arrayList4 = bundle.getStringArrayList("phnnos");
        for (int i =  0 ; i <arrayList1.size();i++){
            if (arrayList2.get(i).equals("admin")){
                no ++;
            }
        }
        Toast.makeText(getContext(),String.valueOf(no),Toast.LENGTH_LONG).show();
        userimage = root.findViewById(R.id.userimg);
        loginuser = root.findViewById(R.id.loginusername);
        loginemail = root.findViewById(R.id.loginemail);
        loginphn = root.findViewById(R.id.loginphoneno);
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
                        userimage.setImageBitmap(my_image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

        userimage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)  {
                SelectImage();
                return false;
            }
        });
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Long click to change your profile picture",Toast.LENGTH_LONG).show();
            }
        });
        String uname ="Name: " + loginname;
        String email = "Email ID: " + emailid;
        String phn = "Phone No: +91 " + phoneno;
        loginuser.setText(uname);
        loginemail.setText(email);
        loginphn.setText(phn);

        return root;
    }

    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);

                //Setting image to ImageView
                userimage.setImageBitmap(bitmap);

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Uploading");
                progressDialog.show();
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}

