package com.example.a801004623_midterm;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by nalin on 3/5/2018.
 */

public class getDataAsync extends AsyncTask<String,Void,ArrayList<App>> {


    public IApp iapp;
    public getDataAsync(IApp iapp)
    {
        this.iapp=iapp;
    }
    @Override
    protected void onPreExecute() {

        iapp.handlepreExecute();
    }

    @Override
    protected ArrayList<App> doInBackground(String... params) {
        HttpURLConnection connection = null;
        ArrayList<App> result = new ArrayList<>();

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                JSONObject root = new JSONObject(json);
                JSONObject appsjsonarray = root.getJSONObject("feed");
                JSONArray appsjsonarray1 = appsjsonarray.getJSONArray("results");

                for (int i=0;i<appsjsonarray1.length();i++) {
                    JSONObject appsJson = appsjsonarray1.getJSONObject(i);
                    App appobject = new App();
                    appobject.appName=appsJson.getString("name");
                    appobject.appImage=appsJson.getString("artworkUrl100");
                    appobject.ArtistName=appsJson.getString("artistName");
                    appobject.ReleaseDate=appsJson.getString("releaseDate");
                    appobject.appCopyright=appsJson.getString("copyright");
                  //  JSONObject genres= root.getJSONObject("genres");
                    JSONArray generesarray=appsJson.getJSONArray("genres");
                    for(int j=0;j<generesarray.length();j++) {
                        JSONObject genereslist = generesarray.getJSONObject(j);
                        String genre=genereslist.getString("name");
                        appobject.genres.add(genre);
                    }


                    result.add(appobject);
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
    protected void onPostExecute(ArrayList<App> apps) {

        iapp.handlepostExecute(apps);

    }


    public static interface IApp
    {
        public void handlepreExecute();
        public void handlepostExecute(ArrayList<App> apps);
    }

}
