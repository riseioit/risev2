package com.riselab.rise.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.riselab.rise.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SlideshowFragment extends Fragment {

        EditText componnt , price , quantity ;
        Button request , bought ;
    String username , emailid , loginname , phoneno;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final Vibrator vib = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("component");
        componnt = root.findViewById(R.id.componentet);
        price = root.findViewById(R.id.pricet);
        quantity = root.findViewById(R.id.quantity);
        request = root.findViewById(R.id.reqbut);
        bought = root.findViewById(R.id.boughtbut);
        Bundle bundle = getActivity().getIntent().getExtras();
        username = bundle.getString("username");
        emailid = bundle.getString("email");
        loginname = bundle.getString("loginname");
        phoneno = bundle.getString("phnno");
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Enter Total Price",Toast.LENGTH_LONG).show();
            }
        });


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comp = componnt.getText().toString();
                if (comp.equals("")){
                    vib.vibrate(200);
                    componnt.setError("Enter Component");
                    componnt.requestFocus();
                }

                else if (quantity.getText().toString().equals("")){
                    vib.vibrate(200);
                    quantity.setError("Enter Quantity");
                    quantity.requestFocus();
                }
                else if (price.getText().toString().equals("")){
                    vib.vibrate(200);
                    price.setError("Enter Price");
                    price.requestFocus();
                }
                else{
                    Toast.makeText(getContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :hh:mm:ss");
                    Date date = new Date();
                    String newdate = dateFormat.format(date).toString();
                    myRef.child("request").child(username).child(newdate).child("name").setValue(comp.trim());
                    myRef.child("request").child(username).child(newdate).child("quantity").setValue(quantity.getText().toString().trim());
                    myRef.child("request").child(username).child(newdate).child("price").setValue(price.getText().toString().trim());

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"riselabioit@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Requesting Component from: " + loginname);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "I, " + loginname + " \nEmail: " + emailid + " \nWould like to request " + quantity.getText().toString() + " Units of " + componnt.getText().toString().toUpperCase() + "."+ "\n\n\n\n\n\n\n\n\n\n\n\n\nFrom RISE App.");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    componnt.setText("");
                    price.setText("");
                    quantity.setText("");
                }

            }


        });

        bought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comp = componnt.getText().toString();
                if (comp.equals("")){
                    vib.vibrate(200);
                    componnt.setError("Enter Component");
                    componnt.requestFocus();
                }

                else if (quantity.getText().toString().equals("")){
                    vib.vibrate(200);
                    quantity.setError("Enter Quantity");
                    quantity.requestFocus();
                }
                else if (price.getText().toString().equals("")){
                    vib.vibrate(200);
                    price.setError("Enter Price");
                    price.requestFocus();
                }
                else{
                    Toast.makeText(getContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :hh:mm:ss");
                    Date date = new Date();
                    String newdate = dateFormat.format(date).toString();
                    myRef.child("bought").child(username).child(newdate).child("name").setValue(comp.trim());
                    myRef.child("bought").child(username).child(newdate).child("quantity").setValue(quantity.getText().toString().trim());
                    myRef.child("bought").child(username).child(newdate).child("price").setValue(price.getText().toString().trim());
                    vib.vibrate(200);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"riselabioit@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Component Bought from: " + loginname);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "I, " + loginname + " \nEmail: " + emailid + " \nWould like to inform about the component I've bought. \n" + quantity.getText().toString() + " Units of " + componnt.getText().toString().toUpperCase() + "."+ "\nTotal Price: "+ price.getText().toString().trim()+"\n\n\n\n\n\n\n\n\n\n\n\n\nFrom RISE App.");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    componnt.setText("");
                    price.setText("");
                    quantity.setText("");
                }
            }
        });

        return root;
    }
}