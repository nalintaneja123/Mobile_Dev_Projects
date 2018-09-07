package com.example.group25hw03;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * Created by nalin on 2/15/2018.
 */

public class getTriviaQuestions extends AsyncTask<String,Void,ArrayList<Question>> {

     //ImageView iv;
    Bitmap bitmap;
    loadQuestions loadQuestions;


    public getTriviaQuestions(loadQuestions loadQuestions)
    {

        this.loadQuestions=loadQuestions;
    }

    @Override
    protected void onPreExecute() {
        loadQuestions.loadspinner();


    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {

      HttpURLConnection connection = null;
      StringBuilder stringBuilder=new StringBuilder();
       // bitmap = null;
        BufferedReader reader;
        ArrayList<Question> result=new ArrayList<Question>();

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);

                    String[] contentsOfLine=line.split(";");

                    String questionID=contentsOfLine[0];

                    String questionBody=contentsOfLine[1];
                    String urlofimage=null;

                    if(contentsOfLine[2].length()>0)
                    {
                        urlofimage=contentsOfLine[2];
                    }

                    ArrayList<String> listofOptions=new ArrayList<String>();

                    for(int i=3;i<contentsOfLine.length-1;i++)
                    {
                        listofOptions.add(contentsOfLine[i]);
                    }

                    String correctAnswer=contentsOfLine[contentsOfLine.length-1];

                    Question question=new Question(questionID,urlofimage,correctAnswer,questionBody,listofOptions);
                    result.add(question);

                }
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

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> s) {


        loadQuestions.loadQuestionsForTrivia(s);

    }



    public static interface loadQuestions
    {
        public void loadspinner();
        public void loadQuestionsForTrivia(ArrayList<Question> listofQuestions);
    }

}
