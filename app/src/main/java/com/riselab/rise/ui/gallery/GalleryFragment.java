package com.riselab.rise.ui.gallery;

import android.content.Context;
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
import java.util.concurrent.TimeUnit;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("task");
    EditText data ;
    Button submit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getActivity().getIntent().getExtras();
        final String uname = bundle.getString("username");
        final String type = bundle.getString("type");
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        data = root.findViewById(R.id.taskEdittext);
        submit = root.findViewById(R.id.buttontask);

        final Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("admin")) {
                    if (data.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please enter Task done today!", Toast.LENGTH_LONG).show();
                        vib.vibrate(70);
                        try {
                            TimeUnit.MILLISECONDS.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        vib.vibrate(70);
                    } else {
                        vib.vibrate(75);
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :hh:mm:ss");
                        Date date = new Date();
                        String newdate = dateFormat.format(date).toString();
                        myRef.child(uname).child(newdate).setValue(data.getText().toString().trim());
                        data.setText("");
                        Toast.makeText(getContext(), "Response Submitted", Toast.LENGTH_LONG).show();
                        data.setHint("Add new Task");
                    }
                }
                else {
                    Toast.makeText(getContext(),"Only RISE Members can upload",Toast.LENGTH_LONG).show();
                    vib.vibrate(200);
                    try {
                        TimeUnit.MILLISECONDS.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    vib.vibrate(200);
                    data.setText("");
                }
            }
        });
        return root;
    }
}