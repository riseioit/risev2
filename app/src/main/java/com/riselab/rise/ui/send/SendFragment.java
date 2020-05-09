package com.riselab.rise.ui.send;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
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
    ImageView github , insta , whatsapp , email , linkedin , githubaman , telegram , instaaman , facebook;

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
        final Vibrator vib = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        insta = root.findViewById(R.id.instaimg);
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);
                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://www.instagram.com/rise_ioit?igshid=xk5crvagh4kc"));
                startActivity(httpIntent);

            }
        });

        whatsapp = root.findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);

                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91 7021211083"));
                startActivity(intent);
            }
        });

        email = root.findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);

                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","amankumar48765@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        linkedin = root.findViewById(R.id.linkedinaman);
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);

                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://www.linkedin.com/in/aman-kumar-52ab56192"));
                startActivity(httpIntent);
            }
        });
        telegram = root.findViewById(R.id.telegram);
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);

                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://t.me/Toshiro0_0"));
                startActivity(httpIntent);
            }
        });
        githubaman = root.findViewById(R.id.github);
        githubaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);

                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://www.github.com/shady48765"));
                startActivity(httpIntent);
            }
        });
        instaaman = root.findViewById(R.id.instagram);
        instaaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);

                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://www.instagram.com/shady48765?igshid=b2lmly77eohd"));
                startActivity(httpIntent);
            }
        });
        facebook = root.findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);

                Toast.makeText(getContext(),"Please Wait!",Toast.LENGTH_SHORT).show();
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://www.facebook.com/riseioit"));
                startActivity(httpIntent);
            }
        });
        return root;
    }
}