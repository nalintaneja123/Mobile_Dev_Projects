package com.example.group25inclass05;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements getKeyWords.IKeyword,getUrlAsync.IUrl,getImage.IImage{


    ArrayList<String> result=new ArrayList<String>();
    int index=0;
    boolean showloading=false;

    ImageButton imgNextButton;
    ImageButton imgPreviousButton;
    ProgressDialog progressDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgNextButton=(ImageButton)findViewById(R.id.imgbtnNext);
        imgPreviousButton=(ImageButton)findViewById(R.id.imgbtnPrevious);

        imgNextButton.setEnabled(false);
        imgPreviousButton.setEnabled(false);
        imageView=(ImageView)findViewById(R.id.imageView);
      //  imageView.setVisibility(View.INVISIBLE);


        findViewById(R.id.btnGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected())
                {
                         result.clear();
                         index=0;
                        //  Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_SHORT).show();
                        new getKeyWords(MainActivity.this).execute("http://dev.theappsdr.com/apis/photos/keywords.php");
                        // imageView.setVisibility(View.VISIBLE);


                }
                else
                {
                    Toast.makeText(MainActivity.this,"is Not Connected",Toast.LENGTH_SHORT).show();
                }
            }
        });

              imgNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isConnected()) {

                    if(result.size()==1)
                    {
                      imgNextButton.setEnabled(false);
                      imgPreviousButton.setEnabled(false);

                    }
                    else {
                        imgNextButton.setEnabled(true);
                        imgPreviousButton.setEnabled(true);

                        if (index == result.size() - 1) {
                            new getImage((ImageView) findViewById(R.id.imageView), MainActivity.this,index).execute(result.get(0));
                            index = 0;
                        } else {
                            index++;
                            new getImage((ImageView) findViewById(R.id.imageView), MainActivity.this,index).execute(result.get(index));

                        }
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this,"is Not Connected",Toast.LENGTH_SHORT).show();
                }

            }
        });
            imgPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {

                    if(result.size()==1)
                    {
                        imgNextButton.setEnabled(false);
                        imgPreviousButton.setEnabled(false);
                    }
                    else {
                        imgNextButton.setEnabled(true);
                        imgPreviousButton.setEnabled(true);

                        if (index == 0) {
                            new getImage((ImageView) findViewById(R.id.imageView), MainActivity.this,index).execute(result.get(result.size() - 1));
                            index = result.size() - 1;
                        } else {
                            index--;
                            new getImage((ImageView) findViewById(R.id.imageView), MainActivity.this,index).execute(result.get(index));
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"is Not Connected",Toast.LENGTH_SHORT).show();
                }
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
    public void handleData(String s) {


        final AlertDialog.Builder builder=new AlertDialog.Builder(this);

        final String[] names = s.split(";");
        Log.d("demo", "onPostExecute: "+names[0]);
        builder.setTitle("Choose a keyword..");

        builder.setItems(names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                TextView t = (TextView)findViewById(R.id.txtKeyWord);
                t.setText(names[item]);

                new getUrlAsync(MainActivity.this).execute("http://dev.theappsdr.com/apis/photos/index.php?keyword="+names[item]);
                imgNextButton.setEnabled(true);
                imgPreviousButton.setEnabled(true);
                //new ma.GetURLS().execute("http://dev.theappsdr.com/apis/photos/index.php?keyword="+names[item]);


            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }


    @Override
    public void handlespinnerForPreExecute() {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Dictionary...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    @Override
    public void handleDataForUrl(ArrayList<String> s) {
        if(s.size()!=0) {
            result = s;
            //progressDialog.dismiss();
            new getImage((ImageView) findViewById(R.id.imageView), MainActivity.this,index).execute(result.get(0));
        }
        else
        {
            Toast.makeText(MainActivity.this,"No images found",Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(null);
            imgPreviousButton.setEnabled(false);
            imgNextButton.setEnabled(false);
        }
    }

    @Override
    public void handlespinnerForPostExecute() {

        progressDialog.dismiss();

    }

    public void handleDataforImageBeforeLoading() {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Photo...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void handleDataForImage(ImageView iv,Bitmap bitmap) {

        progressDialog.dismiss();

        if(iv!=null && bitmap!=null)
        {
            iv.setImageBitmap(bitmap);
        }
    }




}
