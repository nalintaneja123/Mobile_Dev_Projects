package com.example.saipavanraju.inclass06;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    int index = 0;
    ArrayList<Articles> selectedArticle = new ArrayList<Articles>();
    String[] categories;
    ProgressDialog progressDialog;
    ImageView pic;
    TextView nav;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         categories = new String[]{"business", "entertainment", "general", "health", "science", "sports", "technology"};
        pic = findViewById(R.id.newsImage);
        nav = findViewById(R.id.nav);


        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedArticle.size()-1 > index){
                    index++;
                    setview(selectedArticle.get(index));
                }


            }
        });

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (index > 0){
                    index--;
                    setview(selectedArticle.get(index));
                }

            }
        });

        findViewById(R.id.btnGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Choose Category..");
                        builder.setItems(categories,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                TextView selectedOption = findViewById(R.id.textView);
                                selectedOption.setText(categories[id]);
                                new GetDataAsync().execute("https://newsapi.org/v2/top-headlines?country=us&category="+categories[id]+"&apiKey=c70addbe500d4275b06317742fd05021");
                            }
                        });

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void setview(Articles input){
        if (selectedArticle.size()-1 == index){
            findViewById(R.id.imageView2).setEnabled(false);
            findViewById(R.id.imageView).setEnabled(true);
        }else if (index > 0){
            findViewById(R.id.imageView).setEnabled(true);
        }
        if (index == 0){

            findViewById(R.id.imageView).setEnabled(false);
            findViewById(R.id.imageView2).setEnabled(true);

        }else if (selectedArticle.size()-1 < index){
            findViewById(R.id.imageView2).setEnabled(true);
        }
        TextView head = findViewById(R.id.txtViewTitle);
        head.setText(input.getTitle());
        head.setVisibility(View.VISIBLE);
        TextView published = findViewById(R.id.txtViewPublishedAt );

        published.setText(input.getPublishedAt());
        published.setVisibility(View.VISIBLE);
        TextView des = findViewById(R.id.txtviewNewsDescription);
        if(input.getDescription().equals("null")) {

              des.setText(R.string.nodes);

        }
        else
        {
            des.setText(input.getDescription());
            des.setVisibility(View.VISIBLE);

        }
        pic.setVisibility(View.VISIBLE);

        int temp =index+1;
        nav.setText(temp + " out of " + selectedArticle.size());
        nav.setVisibility(View.VISIBLE);

        Picasso.with(MainActivity.this).load(input.getUrlToImage()).into(pic);

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

    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<Articles>> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading News...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected ArrayList<Articles> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Articles> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray articles = root.getJSONArray("articles");
                    for (int i=0;i<articles.length();i++) {
                        JSONObject articleJson = articles.getJSONObject(i);
                        Articles article = new Articles();
                        article.title = articleJson.getString("title");
                        article.publishedAt = articleJson.getString("publishedAt");
                        article.description = articleJson.getString("description");
                        article.urlToImage = articleJson.getString("urlToImage");
                        result.add(article);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Articles> result) {
            progressDialog.dismiss();
            if (result.size() > 0) {
                selectedArticle = result;
                index = 0;
                setview(result.get(index));
                Log.d("demo", result.toString());
            } else {
                Toast.makeText(MainActivity.this, "No News Found", Toast.LENGTH_SHORT).show();

                Log.d("demo", "empty result");
                pic.setVisibility(View.INVISIBLE);
                TextView head = findViewById(R.id.txtViewTitle);
                head.setVisibility(View.INVISIBLE);
                TextView published = findViewById(R.id.txtViewPublishedAt );
                published.setVisibility(View.INVISIBLE);
                TextView des = findViewById(R.id.txtviewNewsDescription);
                des.setVisibility(View.INVISIBLE);
                pic.setVisibility(View.INVISIBLE);
            }
        }
    }


}
