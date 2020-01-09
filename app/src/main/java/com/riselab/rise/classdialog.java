package com.riselab.rise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.riselab.rise.R;

public class classdialog extends AppCompatDialogFragment {
    EditText fpuser;
    EditText fppass;
    Button setpass;
    String user;
    String pass;
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
        fppass = view.findViewById(R.id.passfg);
        setpass = view.findViewById(R.id.resetpass);

        setpass.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                user = fpuser.getText().toString().trim();
                pass = fppass.getText().toString().trim();
                myRef.child(fpuser.getText().toString().trim()).child("password").setValue(fppass.getText().toString().trim());
                Toast.makeText(getContext(), "Password Changed",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getContext(),login.class);
                startActivity(i);
            }
        });
        return builder.create();
    }


}
