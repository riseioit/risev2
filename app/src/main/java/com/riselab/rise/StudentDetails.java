package com.riselab.rise;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class StudentDetails {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StudentDetails(String name, String phoneno, String email , String username) {
        this.name = name;
        this.phoneno = phoneno;
        this.email = email;
        this.username = username;
    }
    public StudentDetails (){

    }

    public String name;
    public String phoneno;
    public String email;
    public String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//    public Bitmap profile;

    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference ref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");


//    public Bitmap getStudentProfile() {
//
//        return profile;
//    }
//
//    public void setStudentProfile(){
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
//                    String value = keynode.getKey();
//                    String namedata = keynode.child("name").getValue().toString();
//                    if(namedata.equals(name)){
//                        ref = storageRef.child(value).child("pfp").child("image");
//                        File localFile = null;
//                        try {
//                            localFile = File.createTempFile("images", "jpeg");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        final File finalLocalFile = localFile;
//                        ref.getFile(localFile)
//                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
////                        progressDialog.setMessage("Please wait!");
////                        progressDialog.show();
//                                        profile = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
//
////                        progressDialog.cancel();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle failed download
//                                // ...
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//        this.profile = profile;
//    }
}
