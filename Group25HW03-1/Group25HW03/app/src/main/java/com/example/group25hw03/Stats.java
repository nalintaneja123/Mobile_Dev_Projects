package com.example.group25hw03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Stats extends AppCompatActivity {


    ProgressBar progressBar;
    Button btnQuit;
    Button btnTryAgain;
    TextView textViewPercentageOfAnswersCorrect;
     ArrayList<Question> questionDetailList=new ArrayList<Question>();
    int percentageofQuestionsCorrect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        progressBar=(ProgressBar)findViewById(R.id.progressbaranswers);
        btnQuit=(Button)findViewById(R.id.btnQuit);
        btnTryAgain=(Button)findViewById(R.id.btnTryAgain);
        textViewPercentageOfAnswersCorrect=(TextView)findViewById(R.id.txtViewProgress);


          if(getIntent()!=null && getIntent().getExtras()!=null)
         {
              int correctAnswers=(Integer)getIntent().getExtras().get("allanswers");
            questionDetailList=(ArrayList<Question>)getIntent().getExtras().get("questionDetailList");

            if(correctAnswers==questionDetailList.size()-1)
            {
                percentageofQuestionsCorrect=100;
            }
            else {
                percentageofQuestionsCorrect= (correctAnswers) * 100 / (questionDetailList.size());
            }
            textViewPercentageOfAnswersCorrect.setText(percentageofQuestionsCorrect+"%");
            progressBar.setProgress(percentageofQuestionsCorrect);

         }

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(Stats.this,MainActivity.class);
                startActivity(intent);


            }
        });


        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Stats.this,Trivia.class);
                intent.putExtra("questionDetails",questionDetailList);
                startActivity(intent);

            }
        });

    }
}
