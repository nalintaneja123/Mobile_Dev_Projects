package com.example.group25inclass05;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nalin on 2/13/2018.
 */

public class getImage extends AsyncTask<String,Void,Void> {
    ImageView iv;
    Bitmap bitmap;

    ProgressDialog progressDialog;
    public IImage image;
    int index;



    public getImage(ImageView iv,IImage image,int index) {
        this.iv=iv;
        this.image=image;
        this.index=index;
    }

    @Override
    protected void onPreExecute() {

       // if(index!=0)
        //{
            image.handleDataforImageBeforeLoading();
        //}

      //  image.handleDataforImageBeforeLoading();
    }

    @Override
    protected Void doInBackground(String...strings) {
        HttpURLConnection connection = null;
        bitmap = null;

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                //images.add(strings);
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
        return null;
        //return  images;
        //return bitmap;
    }

    @Override
    protected void onPostExecute(Void avoid) {

        /*if(iv!=null && bitmap!=null)
        {
            iv.setImageBitmap(bitmap);
        }*/
        image.handleDataForImage(iv,bitmap);
    }

    public static interface IImage
    {
        public void handleDataforImageBeforeLoading();
        public void handleDataForImage(ImageView iv,Bitmap bm);

    }
}

