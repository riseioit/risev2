package com.riselab.rise.ui.send;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.riselab.rise.R;
import com.riselab.rise.browser;
import com.riselab.rise.classdialog;
import com.riselab.rise.classsignup;
import com.riselab.rise.login;
import com.riselab.rise.splash;

public class SendFragment extends Fragment {
    ImageView github;

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        github = root.findViewById(R.id.githubimg);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://www.github.com/orgs/riseioit"));
                startActivity(httpIntent);

            }
        });
        return root;
    }
}