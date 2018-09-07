package com.example.saipavanraju.inclass07;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements getDataAsync.INews {
    public static final String data_key = "data";

    ProgressDialog progressDialog;
    ArrayList<News> newsList = new ArrayList<News>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            setTitle(getIntent().getExtras().getString(MainActivity.Option_key).toString());
            if (isConnected()){
                new getDataAsync(NewsActivity.this).execute("https://newsapi.org/v2/top-headlines?country=us&category="+getIntent().getExtras().getString(MainActivity.Option_key).toString()+"&apiKey=c70addbe500d4275b06317742fd05021");
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading News...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }else{
                Toast.makeText(NewsActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            }

        }
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public void handlepreExecute() {

    }

    @Override
    public void handlepostExecute(ArrayList<News> newsarticles) {
        progressDialog.dismiss();

        newsList = newsarticles;
        if (newsarticles!=null){
          ListView listView = findViewById(R.id.listView);
            final NewsAdapter newsAdapter = new NewsAdapter(this,R.layout.activity_news,newsarticles);
            listView.setAdapter(newsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(NewsActivity.this,DetailsActivity.class);
                    intent.putExtra(data_key,newsList.get(position));
                    startActivity(intent);
                    Log.d("demo", "onItemClick: "+newsList.get(position).title);
                }
            });
        }else{
            Toast.makeText(NewsActivity.this, R.string.no_news, Toast.LENGTH_SHORT).show();
        }
    }
}
