package com.riselab.rise.ui.share;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.riselab.rise.R;
import com.riselab.rise.RecyclerViewAdapter;
import com.riselab.rise.StudentDetails;

import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment implements SearchView.OnQueryTextListener{

    private ShareViewModel shareViewModel;
    List<StudentDetails> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerViewAdapter adapter ;

    ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    StudentDetails studentDetails;
    SearchView searchView;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> phonenos = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        progressDialog = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);



        searchView = root.findViewById(R.id.searchview);
        LoadData();

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
//        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Enter Name");


        return root;
    }

    private void LoadData() {

        progressDialog.setMessage("Loading Data!");
        progressDialog.show();
        list.clear();
        names.clear();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    String value = keynode.getKey();
                    String email = keynode.child("email").getValue().toString();
                    String name = keynode.child("name").getValue().toString();
                    String phnn = keynode.child("phoneno").getValue().toString();
                    String type = keynode.child("type").getValue().toString();
                    if(type.equals("admin")) {
                        if(!names.contains(name)) {
                            names.add(name);
                            emails.add(email);
                            phonenos.add(phnn);
                            values.add(value);
                            studentDetails = new StudentDetails(name, phnn, email, value);
                            list.add(studentDetails);
                        }
                    }
                }
                adapter = new RecyclerViewAdapter(getContext(), list);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });


    }

    @Override
    public boolean onQueryTextSubmit(final String query) {

        return true;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
            list.clear();
         {

            int count = 0;
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).toLowerCase().trim().contains(newText.toLowerCase().trim())) {
                    count++;
                    studentDetails = new StudentDetails(names.get(i), phonenos.get(i), emails.get(i), values.get(i));
                    list.add(studentDetails);
                }
            }
            if (count != 0){
                adapter = new RecyclerViewAdapter(getContext(), list);
                recyclerView.setAdapter(adapter);
            }

            if(count == 0 && newText.length() != 0) {
                Toast.makeText(getContext(),"No Such Member Found!",Toast.LENGTH_LONG).show();
            }
        }

        return true;
    }


}