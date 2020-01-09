package com.riselab.rise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class login extends AppCompatActivity {
    TextView signup , frgtpass ;
    EditText sname , pass;
    Button slogin;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    final ArrayList<String> arrayList1 = new ArrayList<>();
    final ArrayList<String> arrayList = new ArrayList<>();
    final ArrayList<String> arrayList2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        signup = findViewById(R.id.signup);
        frgtpass = findViewById(R.id.frgtpass);
        sname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        slogin = findViewById(R.id.login);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    String value = keynode.getKey();
                    String keypass = keynode.child("password").getValue().toString();
                    String type = keynode.child("type").getValue().toString();
                    arrayList.add(value);
                    arrayList1.add(keypass);
                    arrayList2.add(type);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String newuser = getcolortext("New User?", "#FFFFFF");
        String sgnup = getcolortext("Sign Up" , "#28b78d");
        signup.setTextSize(15);
        signup.setText(Html.fromHtml(newuser+ " " + sgnup));


        slogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        slogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sname.getText().toString().trim();
                String password = pass.getText().toString().trim();
                int tdex = 0;
                if(arrayList.contains(name)){
                   tdex = arrayList.indexOf(name);
                   if(arrayList2.get(tdex).equals("admin")&&arrayList1.get(tdex).equals(password)) {
                       Toast.makeText(getApplicationContext(), "Welcome admin",Toast.LENGTH_LONG).show();
                   }
                   else if (arrayList2.get(tdex).equals("student")&&arrayList1.get(tdex).equals(password))
                       Toast.makeText(getApplicationContext(), "Welcome",Toast.LENGTH_LONG).show();
                   else if (!arrayList1.get(tdex).equals(password) &&(arrayList2.get(tdex).equals("student")||arrayList2.get(tdex).equals("admin")))
                    Toast.makeText(getApplicationContext(), "Invalid Password",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Invalid Credentials",Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(),afterlogin.class);
                startActivity(i);
            }
        });

        signup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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
                Intent i = new Intent(getApplicationContext(),classsignup.class);
                Bundle bundle = new Bundle();
                bundle.putString("type","student");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

    }

    private String getcolortext (String text, String color){
        String input = "<font color=" + color + ">"  + text + "</font>";
        return input;
    }

    public void frgtpass(View view) {
        openDialog();
    }

    public void openDialog(){
        classdialog dialog = new classdialog();
        dialog.show(getSupportFragmentManager(),"Reset Password");
    }
}
