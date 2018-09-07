package com.example.saipavanraju.inclass07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(R.string.details_name);

        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
          News news = (News) getIntent().getExtras().getSerializable(NewsActivity.data_key);
            Log.d("demo", "onCreate: "+news.getTitle());
            TextView heading = findViewById(R.id.txtViewTitle);
            TextView subHeading = findViewById(R.id.txtViewPublishedAt);
            TextView description = findViewById(R.id.txtviewNewsDescription);
            ImageView img = findViewById(R.id.newsImage);
            heading.setText(news.title);
            subHeading.setText(news.publishedAt);
            description.setText(news.description);
            Picasso.with(getBaseContext()).load(news.urlToImage).into(img);

        }
    }
}
