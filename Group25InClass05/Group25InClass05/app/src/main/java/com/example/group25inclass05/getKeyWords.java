package com.example.group25inclass05;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nalin on 2/13/2018.
 */

public class getKeyWords extends AsyncTask<String,Void,String> {


       public IKeyword iKeyword;

    public getKeyWords(IKeyword iKeyword) {
        this.iKeyword=iKeyword;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
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
    protected void onPostExecute(String s) {
        //      Log.d("demo", "onPostExecute: "+s);

        //iKeyword.handleDataforKeyWordBeforeLoading();
        iKeyword.handleData(s);
    }

    public static interface IKeyword
    {
       // public void handleDataforKeyWordBeforeLoading();
        public void handleData(String s);
    }
}


