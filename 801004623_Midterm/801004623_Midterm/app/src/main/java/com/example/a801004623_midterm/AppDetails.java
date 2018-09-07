package com.example.a801004623_midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;

public class AppDetails extends AppCompatActivity {


    TextView txtViewAppTitle;
    TextView txtViewReleaseDate;
    TextView generes;
    ImageView imgViewApp;
    TextView appArtist;
    TextView appCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);

        setTitle(R.string.titleof2ndactivity);

        txtViewAppTitle=(TextView)findViewById(R.id.txtViewAppName);
        txtViewReleaseDate=(TextView)findViewById(R.id.txtViewReleaseDate);
        generes=(TextView)findViewById(R.id.txtViewAppGenres);
        imgViewApp=(ImageView)findViewById(R.id.appImage);
        appArtist=(TextView)findViewById(R.id.txtViewArtistName);
        appCopyright=(TextView)findViewById(R.id.txtViewCopyRight);

        StringBuilder builder=new StringBuilder();

        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            App apps=(App)getIntent().getExtras().getSerializable(MainActivity.data_key);
            txtViewAppTitle.setText(apps.appName);
            txtViewReleaseDate.setText(apps.ReleaseDate);
            for(int i=0;i<apps.genres.size();i++) {
                Collections.sort(apps.genres);
                builder.append(apps.genres.get(i)+",");
            }
            builder.deleteCharAt(builder.length()-1);
            generes.setText(builder);
           // builder.deleteCharAt(builder.length()-1);

            Picasso.with(getApplicationContext()).load(apps.appImage).into(imgViewApp);
            appArtist.setText(apps.ArtistName);
            appCopyright.setText(apps.appCopyright);



        }




    }
}
