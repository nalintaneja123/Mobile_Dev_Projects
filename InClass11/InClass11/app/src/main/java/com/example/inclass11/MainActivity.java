package com.example.inclass11;

import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{


    ViewPager viewPager;
    SwipeAdaptor swipAdapter;
    ArrayList<String> dataOfUrls=new ArrayList<>();
    Button btnClear;
     EditText searchString;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Images images = new Images();


        final OkHttpClient client = new OkHttpClient();

        searchString=(EditText)findViewById(R.id.editText_search);
        btnClear=(Button)findViewById(R.id.btnClear);


        findViewById(R.id.imageButton_Search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (searchString.length() == 0) {

                    Toast.makeText(getApplicationContext(),"Please fill in the text in the search bar",Toast.LENGTH_SHORT).show();

                } else {

                    progressDialog=new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading Images...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    final Request request = new Request.Builder()
                            .url("https://api.gettyimages.com/v3/search/images?fields=id,title,thumb,referral_destinations&sort_order=best&phrase=" + searchString.getText().toString())
                            .addHeader("Api-Key", "53ptk9j5ffcfk3vdsycc92jx")
                            .get()
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            final String responseBody = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    dataOfUrls.clear();
                                    progressDialog.dismiss();
                                    try {
                                        JSONObject root = new JSONObject(responseBody);
                                        JSONArray imagesArr = root.getJSONArray("images");
                                        for (int i = 0; i < imagesArr.length(); i++) {
                                            JSONObject element = imagesArr.getJSONObject(i);
                                            JSONArray target = element.getJSONArray("display_sizes");
                                            JSONObject getURL = target.getJSONObject(0);
                                            images.setUrls(getURL.getString("uri").toString());
                                        }

                                        dataOfUrls = images.getUrls();
                                        viewPager = (ViewPager) findViewById(R.id.view_pager);
                                        swipAdapter = new SwipeAdaptor(MainActivity.this);
                                        swipAdapter.getURLArrayList(dataOfUrls, getApplicationContext());
                                        viewPager.setAdapter(swipAdapter);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                        }

                    });
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!dataOfUrls.isEmpty()) {
                    searchString.setText("");
                    dataOfUrls.clear();
                    swipAdapter = new SwipeAdaptor(MainActivity.this);
                    swipAdapter.getURLArrayList(dataOfUrls, getApplicationContext());
                    viewPager.setAdapter(swipAdapter);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Images currently displayed",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }


}


