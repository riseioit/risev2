package com.riselab.rise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {
    TextView signup , frgtpass ;
    EditText sname , pass;
    Button slogin;
    Vibrator vib ;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    final ArrayList<String> arrayList1 = new ArrayList<>();
    final ArrayList<String> arrayList = new ArrayList<>();
    final ArrayList<String> arrayList2 = new ArrayList<>();
    final ArrayList<String> arrayList3 = new ArrayList<>();
    final ArrayList<String> arrayList4 = new ArrayList<>();
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(200);
        signup = findViewById(R.id.signup);
        frgtpass = findViewById(R.id.frgtpass);
        sname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        slogin = findViewById(R.id.login);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        String newuser = getcolortext("New User?", "#FFFFFF");
        String sgnup = getcolortext("Sign Up" , "#28b78d");
        signup.setTextSize(15);
        signup.setText(Html.fromHtml(newuser+ " " + sgnup));


//        slogin.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    String value = keynode.getKey();
                    String email = keynode.child("email").getValue().toString();
                    String type = keynode.child("type").getValue().toString();
                    String name = keynode.child("name").getValue().toString();
                    String phnn = keynode.child("phoneno").getValue().toString();
                    arrayList.add(value);
                    arrayList1.add(email);
                    arrayList2.add(type);
                    arrayList3.add(name);
                    arrayList4.add(phnn);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        slogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Auth();
                vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(200);
            }
        });

        signup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(200);

                Intent i = new Intent(getApplicationContext(),classsignup.class);
                Bundle bundle = new Bundle();
                bundle.putString("type","admin");
                i.putExtras(bundle);
                startActivity(i);
                return false;
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(200);
                Intent i = new Intent(getApplicationContext(),classsignup.class);
                Bundle bundle = new Bundle();
                bundle.putString("type","student");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    private void Auth() {
        final String name = sname.getText().toString().trim();
        String password = pass.getText().toString().trim();
        int tdex = 0;
        if (arrayList.contains(name)) {
            tdex = arrayList.indexOf(name);
            final String emailid = arrayList1.get(tdex);
            final String loginname = arrayList3.get(tdex);
            final String phoneno = arrayList4.get(tdex);
            progressDialog.setMessage("Please wait!");

            progressDialog.show();
            final int finalTdex = tdex;
            firebaseAuth.signInWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(200);
                        progressDialog.cancel();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent i = new Intent(getApplicationContext(), afterlogin.class); //
                        Bundle bundle = new Bundle();
                        bundle.putString("email",emailid);
                        bundle.putString("username",name);
                        bundle.putString("loginname",loginname);
                        bundle.putString("phnno",phoneno);
                        bundle.putString("type",arrayList2.get(finalTdex));
//                        bundle.putStringArrayList("usernames",arrayList);
                        bundle.putStringArrayList("emailids",arrayList1);
                        bundle.putStringArrayList("types",arrayList2);
                        bundle.putStringArrayList("names",arrayList3);
                        bundle.putStringArrayList("phnnos",arrayList4);
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(200);
                        try {
                            TimeUnit.MILLISECONDS.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        vib.vibrate(200);
                        progressDialog.cancel();
                    }
                }
            });

//                   if(arrayList2.get(tdex).equals("admin")&&arrayList1.get(tdex).equals(password)) {
//                       Toast.makeText(getApplicationContext(), "Welcome admin",Toast.LENGTH_LONG).show();
//                   }
//                   else if (arrayList2.get(tdex).equals("student")&&arrayList1.get(tdex).equals(password))
//                       Toast.makeText(getApplicationContext(), "Welcome",Toast.LENGTH_LONG).show();
//                   else if (!arrayList1.get(tdex).equals(password) &&(arrayList2.get(tdex).equals("student")||arrayList2.get(tdex).equals("admin")))
//                    Toast.makeText(getApplicationContext(), "Invalid Password",Toast.LENGTH_LONG).show();
        } else {

            if ( arrayList.isEmpty()){
                Toast.makeText(getApplicationContext(),"Seems like Internet is slow",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(200);
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                vib.vibrate(200);
            }
        }
    }

    private String getcolortext (String text, String color){
        String input = "<font color=" + color + ">"  + text + "</font>";
        return input;
    }

    public void frgtpass(View view) {
        openDialog();
    }

    public void openDialog(){
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(200);
        classdialog dialog = new classdialog();
        dialog.show(getSupportFragmentManager(),"Reset Password");
    }


}
