package com.example.group25hw03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements getTriviaQuestions.loadQuestions{


    ProgressDialog progressDialog;

    ImageView imgViewLoadingTrivia;

    TextView txtViewTriviaReady;

    Button btnStartTrivia;

    Button btnExit;

    ArrayList<Question> questionsinlist=new ArrayList<Question>();


    //Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgViewLoadingTrivia=(ImageView)findViewById(R.id.imgViewTrivia);
        txtViewTriviaReady=(TextView)findViewById(R.id.txtViewTriviaReady);
        btnStartTrivia=(Button)findViewById(R.id.btnStartTrivia);
        btnExit=(Button)findViewById(R.id.btnExit);


        new getTriviaQuestions(MainActivity.this).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");

        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnStartTrivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //question=new Question(questionsinlist,options,images,correctAnswers);
                Intent intent=new Intent(MainActivity.this,Trivia.class);
                intent.putExtra("questionDetails",questionsinlist);
                startActivity(intent);

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               finishAffinity();
            }
        });


    }

    @Override
    public void loadspinner() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Trivia...");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    @Override
    public void loadQuestionsForTrivia(ArrayList<Question> questions) {


        if(questions.size()!=0) {
            questionsinlist=questions;
            txtViewTriviaReady.setVisibility(View.VISIBLE);
            imgViewLoadingTrivia.setVisibility(View.VISIBLE);
            btnStartTrivia.setEnabled(true);
            imgViewLoadingTrivia.setImageDrawable(getResources().getDrawable(R.drawable.trivia));
            progressDialog.dismiss();
        }
    }
}
