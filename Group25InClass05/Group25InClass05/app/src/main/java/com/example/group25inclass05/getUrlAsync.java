package com.example.group25inclass05;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nalin on 2/13/2018.
 */

public class getUrlAsync extends AsyncTask<String, Void, ArrayList<String>> {

   // ProgressDialog progressDialog;
    //MainActivity mainActivity;
    IUrl iUrl;

    public getUrlAsync(IUrl iUrl)
    {
        this.iUrl=iUrl;

    }


    @Override
    protected void onPreExecute() {

        iUrl.handlespinnerForPreExecute();
       /* progressDialog = new ProgressDialog();
        progressDialog.setMessage("Loading Dictionaries...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();*/
    }



    @Override
    protected void onProgressUpdate(Void... values) {
        //super.onProgressUpdate(values);

    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        ArrayList<String> result=new ArrayList<String>();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    result.add(line);

                }
                //   result = stringBuilder.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Close open connections and reader
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    @Override
    protected void onPostExecute(ArrayList<String> s) {

       // progressDialog.dismiss();
        iUrl.handlespinnerForPostExecute();
        iUrl.handleDataForUrl(s);
    }


    public static interface IUrl
    {
        public void handlespinnerForPreExecute();
        public void handlespinnerForPostExecute();
        public void handleDataForUrl(ArrayList<String> s);

    }
}

