package com.example.group25hw04;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nalin on 2/22/2018.
 */

public class getDataAsync extends AsyncTask<String,Void,ArrayList<News>> {



    IData iData;
    public getDataAsync(IData idata)
    {
      iData=idata;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected ArrayList<News> doInBackground(String... strings) {

        HttpURLConnection connection = null;

        //   ArrayList<String> result=new ArrayList<String>();

        ArrayList<News> news=null;
        //String result="";
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                news=NewsParser.NewsPullParser.parseNews(connection.getInputStream());
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }
        return news;
    }


    @Override
    protected void onPostExecute(ArrayList<News> news) {
        //super.onPostExecute(news);

        iData.handlePostExecute(news);
    }


    public interface IData
    {
        //public void handlePreExecute();
        public void handlePostExecute(ArrayList<News> news);
    }

}
