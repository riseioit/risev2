package com.riselab.rise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.riselab.rise.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class classdialog extends AppCompatDialogFragment {
    EditText fpuser;
    Button setpass;
    String emailid;
    String pass;
    ProgressDialog progressDialog;
    Vibrator vib ;
    ArrayList arrayList1 = new ArrayList();
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view);


        fpuser = view.findViewById(R.id.userfp);
        setpass = view.findViewById(R.id.resetpass);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(classdialog.this.getActivity(), R.style.MyAlertDialogStyle);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    String email = keynode.child("email").getValue().toString();
                    arrayList1.add(email);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailid = fpuser.getText().toString().trim();
                if (arrayList1.contains(emailid)) {
                    mAuth.sendPasswordResetEmail(emailid);
                    Toast.makeText(getContext(), "Reset Password Link sent", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(),login.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getContext(), "No User with Such Email-ID found!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return builder.create();
    }
}
