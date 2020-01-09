package com.riselab.rise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class classsignup extends AppCompatActivity {
        EditText name , email ,password , phoneno, user;
        Button sgnup ;
        String usrtype;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classsignup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phoneno = findViewById(R.id.phnno);
        user = findViewById(R.id.username);
        name = findViewById(R.id.name);
        sgnup = findViewById(R.id.login);

        Bundle bundle = getIntent().getExtras();
        usrtype = bundle.getString("type");


        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    myRef.child(user.getText().toString().trim()).child("type").setValue(usrtype);
                    myRef.child(user.getText().toString().trim()).child("name").setValue(name.getText().toString().trim());
                    myRef.child(user.getText().toString().trim()).child("email").setValue(email.getText().toString().trim());
                    myRef.child(user.getText().toString().trim()).child("password").setValue(password.getText().toString().trim());
                    myRef.child(user.getText().toString().trim()).child("phoneno").setValue(phoneno.getText().toString().trim());
            }
        });
    }
}
