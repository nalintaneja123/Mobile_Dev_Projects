package com.example.group25hw03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Trivia extends AppCompatActivity implements getImageForEachQuestion.IHandleForImage {

    //Question question;

    ProgressDialog progressDialog;
    TextView questionTime;
    TextView questionNumber;
    TextView questionDetail;
    RadioGroup rdioChoices;
    //CountDownTimer timer;
    ImageView imageForQuestionDisplayed;
    Button btnNext;
    Button btnQuit;
    int selectedRadioButton=-1;

    ArrayList<Question> questionDetailList=new ArrayList<Question>();


    int currentIndex=0;
    int correctAnswer=0;

    CountDownTimer countDownTimer;

    //LinkedList<String> values=new LinkedList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        questionTime=(TextView)findViewById(R.id.txtViewTimer);
        questionNumber=(TextView)findViewById(R.id.txtViewQuestionNumber);
        questionDetail=(TextView)findViewById(R.id.txtViewQuestionContent);
        rdioChoices=(RadioGroup)findViewById(R.id.rdioGroupQuestionChoices);
        btnNext=(Button)findViewById(R.id.btnNext);
        btnQuit=(Button)findViewById(R.id.btnQuit);
        imageForQuestionDisplayed=(ImageView)findViewById(R.id.imgViewQuestionDisplayed);

        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            questionDetailList=(ArrayList<Question>)getIntent().getSerializableExtra("questionDetails");
            updateViewOnDisplayingNextQuestion(currentIndex);
        }

        countDownTimer= new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                questionTime.setText("Time remaining: " + millisUntilFinished / 1000+"seconds");
            }


            public void onFinish() {

               /* if (selectedRadioButton == Integer.valueOf(questionDetailList.get(currentIndex).getCorrectanswer())) {
                    correctAnswer++;
                }*/
                if(selectedRadioButton == Integer.valueOf(questionDetailList.get(currentIndex).getCorrectanswer()))
                {
                    Intent intent=new Intent(Trivia.this,Stats.class);
                    intent.putExtra("allanswers",++correctAnswer);
                    intent.putExtra("questionDetailList",questionDetailList);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(Trivia.this,Stats.class);
                    intent.putExtra("allanswers",correctAnswer);
                    intent.putExtra("questionDetailList",questionDetailList);
                    startActivity(intent);
                }



            }
        }.start();

             rdioChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(RadioGroup radioGroup, int i) {

                     RadioButton radioButton=(RadioButton)findViewById(i);
                     selectedRadioButton=rdioChoices.getCheckedRadioButtonId();


                 }
             });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(currentIndex==questionDetailList.size()-1)
                {
                    Intent intent=new Intent(Trivia.this,Stats.class);
                    intent.putExtra("allanswers",correctAnswer);
                    intent.putExtra("questionDetailList",questionDetailList);
                    startActivity(intent);
                    countDownTimer.cancel();


                }
                else {
                    rdioChoices.removeAllViews();

                    if (selectedRadioButton == Integer.valueOf(questionDetailList.get(currentIndex).getCorrectanswer())) {
                        correctAnswer++;
                    }
                    rdioChoices.clearCheck();
                    currentIndex = currentIndex + 1;
                    updateViewOnDisplayingNextQuestion(currentIndex);


                }
            }
        });


        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    public void updateViewOnDisplayingNextQuestion(int index)
    {

        int a=index+1;
        if(a==questionDetailList.size())
        {
            questionNumber.setText("Q16");

        }
        else {
            questionNumber.setText("Q" + questionDetailList.get(a).getQuestionID());
        }
        questionDetail.setText(questionDetailList.get(index).getQuestions());
        if(questionDetailList.get(index).getURL()!=null) {
            new getImageForEachQuestion(Trivia.this).execute(questionDetailList.get(index).getURL());

            for (int i = 0; i < questionDetailList.get(index).getChoices().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setId(i);
                radioButton.setText(questionDetailList.get(index).getChoices().get(i).toString());
                rdioChoices.addView(radioButton);
            }

        }
        else
        {
            imageForQuestionDisplayed.setImageBitmap(null);
            for (int i = 0; i < questionDetailList.get(index).getChoices().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setId(i);
                radioButton.setText(questionDetailList.get(index).getChoices().get(i).toString());
                rdioChoices.addView(radioButton);
            }
        }


    }

    @Override
    public void handleDataForPreLoadingImage() {

        progressDialog = new ProgressDialog(Trivia.this);
        progressDialog.setMessage("Loading Image...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void handleDataForPostExecuteImage(Bitmap bitmap) {

        progressDialog.dismiss();
        if(imageForQuestionDisplayed!=null && bitmap!=null)
        {
            imageForQuestionDisplayed.setImageBitmap(bitmap);
        }
    }
}
