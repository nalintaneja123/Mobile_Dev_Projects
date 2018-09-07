package com.example.a801004623_midterm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements getDataAsync.IApp {


    ProgressDialog progressDialog;
    public static final String data_key = "data";
    ArrayList<App> appArrayList=new ArrayList<>();
    ListView listViewapps;
    AppListViewAdapter appListViewAdapter;
    Button btnfilter;
    AlertDialog.Builder builder;

    TextView genere;

    public String[] categories={"Adventure","Arcade","Card"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isConnected()) {

            new getDataAsync(MainActivity.this).execute("https://rss.itunes.apple.com/api/v1/us/ios-apps/top-grossing/all/50/explicit.json");
        }
        else
        {
            Toast.makeText(MainActivity.this,"No internet", Toast.LENGTH_SHORT).show();
        }


        btnfilter=(Button)findViewById(R.id.btnFilter);
        genere=(TextView)findViewById(R.id.txtViewGenreValue);

        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose genere");

                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Apps...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void handlepostExecute(ArrayList<App> apps) {

        progressDialog.dismiss();
        if(apps!=null)
        {
            appArrayList=apps;

            listViewapps=(ListView)findViewById(R.id.listviewApps);

            appListViewAdapter=new AppListViewAdapter(this,R.layout.appdisplay,appArrayList);
            listViewapps.setAdapter(appListViewAdapter);


            listViewapps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this,AppDetails.class);
                    intent.putExtra(data_key,appArrayList.get(i));
                    startActivity(intent);
                }
            });







        }

    }
}
