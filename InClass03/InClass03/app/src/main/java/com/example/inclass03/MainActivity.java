package com.example.inclass03;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static String STUDENT_KEY="Student";
    String value;
    int valueForSeekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText editTextName=(EditText)findViewById(R.id.editTextName);
        final EditText editTextEmail=(EditText)findViewById(R.id.editTextEmail);

        final RadioGroup rdGroup=(RadioGroup)findViewById(R.id.radioGroup);

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                RadioButton r1= (RadioButton) findViewById(i);
                value =r1.getText().toString();
            }
        });
        final SeekBar seekBar=(SeekBar)(findViewById(R.id.seekBarMood));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                valueForSeekbar=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value == null){
                    RadioButton r1= (RadioButton) findViewById(R.id.rdbuttonSIS);
                    value =r1.getText().toString();
                }
                if(editTextName.length()==0 || editTextEmail.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Please enter the following fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this,displayActivity.class);
                    Student stu = new Student(editTextName.getText().toString(),editTextEmail.getText().toString(),value,String.valueOf(valueForSeekbar));
                    intent.putExtra(STUDENT_KEY,stu);
                    startActivity(intent);

                }

            }
        });










    }
}
