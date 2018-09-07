package com.example.saipavanraju.inclass07;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by saipavanraju on 2/26/18.
 */

public class getDataAsync extends AsyncTask<String,Void,ArrayList<News>> {


    public INews iNews;
    public getDataAsync(INews iNews)
    {
        this.iNews=iNews;
    }
    @Override
    protected void onPreExecute() {

        iNews.handlepreExecute();
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {
        HttpURLConnection connection = null;
        ArrayList<News> result = new ArrayList<>();
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
                    News article = new News();
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
    protected void onPostExecute(ArrayList<News> news) {

        iNews.handlepostExecute(news);

    }


    public static interface INews
    {
        public void handlepreExecute();
        public void handlepostExecute(ArrayList<News> newsarticles);
    }

}

