package com.example.group25hw04;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements getDataAsync.IData{

    Button btnGo;
    TextView newsTitle;

    TextView publishedDate;
    TextView newsDescription;
    ImageView imageViewNewsImage;
    TextView showCategories;
    TextView totalNewsDisplayed;
    ImageView imgNext;
    ImageView imgPrevious;

    ArrayList<News> newsArrayList=new ArrayList<>();
    final  CharSequence[] items_category={"Top Stories","World","U.S.","Business","Politics","Technology","Health","Entertainment","Travel","Living","Most Recent"};
    int index=0;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGo=(Button)findViewById(R.id.btnGo);
        newsTitle=(TextView)findViewById(R.id.txtViewNewsTitle);
        publishedDate=(TextView)findViewById(R.id.txtViewPublishedAt);
        imageViewNewsImage=(ImageView)findViewById(R.id.imgViewDisplayNewsImage);
        newsDescription=(TextView)findViewById(R.id.txtViewNewsDescription);
        showCategories=(TextView)findViewById(R.id.txtViewShowCategories);

        totalNewsDisplayed=(TextView)findViewById(R.id.txtViewTotalImagesDisplayed);

        imgNext=(ImageView)findViewById(R.id.imgviewNext);
        imgPrevious=(ImageView)findViewById(R.id.imgViewprevious);

        newsTitle.setVisibility(View.INVISIBLE);
        publishedDate.setVisibility(View.INVISIBLE);
        newsDescription.setVisibility(View.INVISIBLE);
        totalNewsDisplayed.setVisibility(View.INVISIBLE);
        imageViewNewsImage.setVisibility(View.INVISIBLE);
        imgPrevious.setEnabled(false);
        imgNext.setEnabled(false);


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Category..");
                builder.setItems(items_category,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(isConnected())
                        {

                            value=id;
                            String item=items_category[id].toString().replaceAll("\\s","").toLowerCase();
                           // newsArrayList.clear();

                            switch (item)


                            {
                                case "topstories":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_" + item + ".rss");
                                    break;
                                case "world":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_" + item + ".rss");
                                    break;
                                case "u.s.":
                                     new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_us.rss");
                                    break;
                                case "technology":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
                                    break;
                                case "health":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_" + item + ".rss");
                                    break;
                                case "travel":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_" + item + ".rss");
                                    break;
                                case  "living":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_" + item + ".rss");
                                case "business":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/money_latest.rss");
                                    break;
                                case "politics":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_all" + item + ".rss");
                                    break;
                                case "entertainment":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_showbiz.rss");
                                    break;
                                case "mostrecent":
                                    new getDataAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_latest.rss");
                                    break;
                                   default:
                                       break;

                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"No internet Connection", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });


        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(index==newsArrayList.size()-1)
                {
                    index=0;
                  showNews(index);
                }
                else {
                    index++;
                    showNews(index);
                   }
            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(index<=0)
                {
                    index=newsArrayList.size()-1;
                    showNews(newsArrayList.size()-1);

                }
                else {
                    index--;
                    showNews(index);
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


    public void showNews(int index)
    {


        showCategories.setText(items_category[value]);

        if(newsArrayList.get(index).newsTitle!=null) {
            newsTitle.setText(newsArrayList.get(index).newsTitle);
        }
        else
        {
            newsTitle.setText("No news title found");
        }

        publishedDate.setText(newsArrayList.get(index).publishedat);


            Picasso.with(MainActivity.this).load(newsArrayList.get(index).newsImage).into(imageViewNewsImage);



        newsDescription.setText(newsArrayList.get(index).newsDescription);



                int temp = index + 1;
                totalNewsDisplayed.setText(temp + " out of "+ newsArrayList.size());


    }

    @Override
    public void handlePostExecute(ArrayList<News> news) {
        if(news.size()>1)
        {
            newsArrayList=news;

        index=0;
        showNews(index);
        newsTitle.setVisibility(View.VISIBLE);
        publishedDate.setVisibility(View.VISIBLE);
        newsDescription.setVisibility(View.VISIBLE);
        totalNewsDisplayed.setVisibility(View.VISIBLE);
        imageViewNewsImage.setVisibility(View.VISIBLE);
        imgPrevious.setEnabled(true);
        imgNext.setEnabled(true);
        }
        else if(news.size()==1)
        {
            newsArrayList=news;

            index=0;
            showNews(index);
            newsTitle.setVisibility(View.VISIBLE);
            publishedDate.setVisibility(View.VISIBLE);
            newsDescription.setVisibility(View.VISIBLE);
            totalNewsDisplayed.setVisibility(View.VISIBLE);
            imageViewNewsImage.setVisibility(View.VISIBLE);
            imgPrevious.setEnabled(false);
            imgNext.setEnabled(false);
        }
        else
        {
            Toast.makeText(MainActivity.this,"No news found",Toast.LENGTH_SHORT).show();

            newsTitle.setVisibility(View.INVISIBLE);
            publishedDate.setVisibility(View.INVISIBLE);
            newsDescription.setVisibility(View.INVISIBLE);
            totalNewsDisplayed.setVisibility(View.INVISIBLE);
            imageViewNewsImage.setVisibility(View.INVISIBLE);
            imgPrevious.setEnabled(false);
            imgNext.setEnabled(false);
        }
    }
}
