package com.example.group25hw03;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nalin on 2/17/2018.
 */

public class getImageForEachQuestion extends AsyncTask<String,Void,Bitmap> {


    Bitmap bitmap;
    IHandleForImage iHandleForImage;

    public getImageForEachQuestion(IHandleForImage iHandleForImage)
    {
        this.iHandleForImage=iHandleForImage;
    }


    @Override
    protected void onPreExecute() {
        iHandleForImage.handleDataForPreLoadingImage();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        HttpURLConnection connection = null;
        bitmap = null;

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                bitmap = BitmapFactory.decodeStream(connection.getInputStream());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        iHandleForImage.handleDataForPostExecuteImage(bitmap);
    }

    public static interface IHandleForImage
    {
        public void handleDataForPreLoadingImage();
        public void handleDataForPostExecuteImage(Bitmap bitmap);
    }

}
