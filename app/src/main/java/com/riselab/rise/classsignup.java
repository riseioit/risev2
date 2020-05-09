package com.riselab.rise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class classsignup extends AppCompatActivity {
    EditText name , email ,password , phoneno, user;
    Button sgnup ;
    String usrtype;
    ProgressDialog progressDialog;
    Vibrator vib ;
    final ArrayList<String> arrayList1 = new ArrayList<>();
    final ArrayList<String> arrayList = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classsignup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phoneno = findViewById(R.id.phnno);
        user = findViewById(R.id.username);
        name = findViewById(R.id.name);
        sgnup = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        Bundle bundle = getIntent().getExtras();
        usrtype = bundle.getString("type");
        progressDialog=new ProgressDialog(this, R.style.MyAlertDialogStyle);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    String value = keynode.getKey();
                    String email = keynode.child("email").getValue().toString();
                    arrayList.add(value);
                    arrayList1.add(email);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupfn();
            }
        });


    }
    public void signupfn(){
        vib =  (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(200);
        String emailid = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String username = user.getText().toString().trim();
        if(emailid.isEmpty())
        {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
        {
            email.setError("Enter valid email");
            email.requestFocus();
            return;
        }
        if(arrayList.contains(username)){
            user.setError("User Already Exists");
            user.requestFocus();
            return;
        }
        if (arrayList1.contains(emailid)){
            email.setError("Email Already in use");
            email.requestFocus();
            return;
        }
        if(!(phoneno.length() == 10) ){
            phoneno.setError("Phone No. must be of 10 digits");
            phoneno.requestFocus();
            return;
        }
        if(!isNum(phoneno.getText().toString())){
            phoneno.setError("Please Enter a valid Phone Number");
            phoneno.requestFocus();
            return;
        }
        if(username.contains(" ")){
            user.setError("Enter a Username without Space");
            user.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            password.setError("Min length is 6");
            password.requestFocus();
            return;
        }


        progressDialog.setMessage("Registering! Please wait");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emailid,pass).addOnCompleteListener(classsignup.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser userf = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                    vib.vibrate(400);
                    myRef.child(user.getText().toString().trim()).child("type").setValue(usrtype);
                    myRef.child(user.getText().toString().trim()).child("name").setValue(name.getText().toString().trim());
                    myRef.child(user.getText().toString().trim()).child("email").setValue(email.getText().toString().trim());
                    myRef.child(user.getText().toString().trim()).child("phoneno").setValue(phoneno.getText().toString().trim());
                    progressDialog.cancel();
                    email.setText("");
                    name.setText("");
                    phoneno.setText("");
                    password.setText("");
                    user.setText("");
                }
                else {
                    vib.vibrate(400);
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    vib.vibrate(400);
                    Toast.makeText(getApplicationContext(),"Failed, Please try again",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }
        });

    }
    public static boolean isNum(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }



}
