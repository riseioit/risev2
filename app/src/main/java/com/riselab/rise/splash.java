package com.riselab.rise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class splash extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    final ArrayList<String> arrayList1 = new ArrayList<>();
    final ArrayList<String> arrayList = new ArrayList<>();
    final ArrayList<String> arrayList2 = new ArrayList<>();
    final ArrayList<String> arrayList3 = new ArrayList<>();
    final ArrayList<String> arrayList4 = new ArrayList<>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final int[] count = {0};
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    count[0]++;
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
                    if (count[0]==dataSnapshot.getChildrenCount()){
                        Loginuser();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void Loginuser() {
        if (arrayList1.contains(email)){
            int index = arrayList1.indexOf(email);
            Intent i = new Intent(getApplicationContext(), afterlogin.class); //
            Bundle bundle = new Bundle();
            bundle.putString("email",arrayList1.get(index));
            bundle.putString("username",arrayList.get(index));
            bundle.putString("loginname",arrayList3.get(index));
            bundle.putString("phnno",arrayList4.get(index));
            bundle.putString("type",arrayList2.get(index));
//                        bundle.putStringArrayList("usernames",arrayList);
            bundle.putStringArrayList("emailids",arrayList1);
            bundle.putStringArrayList("types",arrayList2);
            bundle.putStringArrayList("names",arrayList3);
            bundle.putStringArrayList("phnnos",arrayList4);
            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(200);
            i.putExtras(bundle);
            startActivity(i);
            finish();
        }
        else
        {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
            Intent i = new Intent(splash.this, login.class);
//                    startActivity(i);
//                    finish();
//                }
//            },2500);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("emailids",arrayList1);
            bundle.putStringArrayList("types",arrayList2);
            bundle.putStringArrayList("names",arrayList3);
            bundle.putStringArrayList("phnnos",arrayList4);
            bundle.putStringArrayList("username",arrayList);
            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(200);
            i.putExtras(bundle);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user!=null){
            email = user.getEmail();

        }

    }

}
