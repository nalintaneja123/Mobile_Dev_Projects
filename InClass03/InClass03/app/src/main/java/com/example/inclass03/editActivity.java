package com.example.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class editActivity extends AppCompatActivity {
    Student stu;
    String option;
    int valueForSeekbar;
    String rgValue;
    RadioButton def;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle(R.string.edit_act);
        if (getIntent()!=null && getIntent().getExtras()!=null){

            if (getIntent().getExtras().containsKey("Name")){

                EditText edtt=(EditText)findViewById(R.id.editTextEmail);
                edtt.setVisibility(View.INVISIBLE);


                //findViewById(R.id.editTextName).setVisibility(View.VISIBLE);
                String s1=getIntent().getExtras().getString("Name");
                EditText edt1=(EditText)findViewById(R.id.editTextName);
                edt1.setVisibility(View.VISIBLE);
                edt1.setText(s1);

            }
            if (getIntent().getExtras().containsKey("Email")){
                //findViewById(R.id.editTextName).setVisibility(View.VISIBLE);
                String s1=getIntent().getExtras().getString("Email");
                EditText edt1=(EditText)findViewById(R.id.editTextEmail);
                edt1.setVisibility(View.VISIBLE);
                edt1.setText(s1);

            }
            if (getIntent().getExtras().containsKey("Department")){
                TextView edt2=(TextView) findViewById(R.id.textView_dept);
                edt2.setVisibility(View.VISIBLE);
                RadioGroup edt1=(RadioGroup) findViewById(R.id.radioGroup);
                RadioButton rbu1 =(RadioButton)findViewById(R.id.rdbuttonSIS);
                RadioButton rbu2 =(RadioButton)findViewById(R.id.rdButtonBio);
                RadioButton rbu3 =(RadioButton)findViewById(R.id.rdbuttonCS);
                RadioButton rbu4 =(RadioButton)findViewById(R.id.rdButtonOthers);
                String s1=getIntent().getExtras().getString("Department");


                if(s1.equals("SIS"))
                {
                    rbu1.setChecked(true);
                    def = rbu1;
                }
                else if(s1.equals("BIO")){

                    rbu2.setChecked(true);
                    def = rbu2;
                }
                else if(s1.equals("CS"))
                {
                    rbu3.setChecked(true);
                    def = rbu3;
                }else {
                    rbu4.setChecked(true);
                    def = rbu4;
                }

                edt1.setVisibility(View.VISIBLE);
                edt1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton r1= (RadioButton) findViewById(i);
                        rgValue =r1.getText().toString();
                    }
                });

            }
            if (getIntent().getExtras().containsKey("Mood")){
                //findViewById(R.id.editTextName).setVisibility(View.VISIBLE);
                TextView seekbar=(TextView)findViewById(R.id.textView_seek);
                seekbar.setVisibility(View.VISIBLE);
                String s1=getIntent().getExtras().getString("Mood");
                SeekBar edt1=(SeekBar) findViewById(R.id.seekBarMood);
                edt1.setVisibility(View.VISIBLE);
                edt1.setProgress(Integer.parseInt(s1));
                edt1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        Log.d("demo", "onProgressChanged: " +i);
                        valueForSeekbar =i;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        }
        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent res = new Intent();
                if (getIntent().getExtras().containsKey("Name")){
                    EditText e1=(EditText)(findViewById(R.id.editTextName));
                    res.putExtra(displayActivity.Value_key,e1.getText().toString());
                    setResult(RESULT_OK,res);
                    finish();



                }
                if (getIntent().getExtras().containsKey("Email")){

                    EditText e1=(EditText)(findViewById(R.id.editTextEmail));
                    res.putExtra(displayActivity.Value_key,e1.getText().toString());
                    setResult(RESULT_OK,res);
                    finish();


                }
                if (getIntent().getExtras().containsKey("Department")){
                    if (rgValue == null){
                        rgValue =def.getText().toString();
                    }
                    res.putExtra(displayActivity.Value_key,rgValue);
                    setResult(RESULT_OK,res);
                    finish();


                }

                if (getIntent().getExtras().containsKey("Mood")){

                    res.putExtra(displayActivity.Value_key,String.valueOf(valueForSeekbar));
                    setResult(RESULT_OK,res);
                    SeekBar edt1=(SeekBar) findViewById(R.id.seekBarMood);
                    finish();


                }
            }
        });

    }

    public void invisible()
    {
        EditText edt1=(EditText)findViewById(R.id.editTextName);
        edt1.setVisibility(View.INVISIBLE);
        EditText edt2=(EditText)findViewById(R.id.editTextEmail);
        edt2.setVisibility(View.INVISIBLE);
        TextView dept=(TextView)findViewById(R.id.textView_dept);
        dept.setVisibility(View.INVISIBLE);
        RadioGroup rg=(RadioGroup) findViewById(R.id.radioGroup);
        rg.setVisibility(View.INVISIBLE);
        TextView seekbar=(TextView)findViewById(R.id.textView_seek);
        seekbar.setVisibility(View.INVISIBLE);
        SeekBar ss =(SeekBar) findViewById(R.id.seekBarMood);
        ss.setVisibility(View.INVISIBLE);


    }
}
