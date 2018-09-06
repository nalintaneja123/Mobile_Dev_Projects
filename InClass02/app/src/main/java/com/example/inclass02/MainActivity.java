package com.example.inclass02;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitybmi);


        Button btnCalculate=(Button)findViewById(R.id.btnCalculate);
        final   EditText editTextAge=(EditText)findViewById(R.id.editTextAge);
         final  EditText editTextWeight=(EditText)findViewById(R.id.editTextWeight);
        final EditText editTextfeet=(EditText)findViewById(R.id.editTextFeet);
        final EditText editTextInches=(EditText)findViewById(R.id.editTextInches);

        final TextView txtResult=(TextView)findViewById(R.id.textBMiResult);



        btnCalculate.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {



                if(TextUtils.isEmpty(editTextAge.getText().toString()) )
                {
                    Toast.makeText(MainActivity.this, "Age is required", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(editTextWeight.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Weight is required", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(editTextfeet.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Height1 is required", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(editTextInches.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, " Height2 is required", Toast.LENGTH_LONG).show();
                }

                else if(Float.parseFloat(editTextAge.getText().toString())<18)
                {
                    Toast.makeText(MainActivity.this, "The age should be 18 and above", Toast.LENGTH_LONG).show();
                }
                else
                {
                        float bmiValue;

                       float  weightInPounds=Float.parseFloat(editTextWeight.getText().toString());
                       float height1=Float.parseFloat(editTextfeet.getText().toString());
                       float height2=Float.parseFloat(editTextInches.getText().toString());
                       float final_height = height1*12 + height2;
                      bmiValue=703*(weightInPounds)/(final_height*final_height);

                    RelativeLayout l1=(RelativeLayout) findViewById(R.id.relativeLayout);
                    l1.setVisibility(View.VISIBLE);

                    String category;

                    if(bmiValue<18.5) {
                        TextView finalResult=(TextView)findViewById(R.id.final_result);

                        TextView txtFinalValueResult=(TextView)findViewById(R.id.txtFinalResult);
                        txtResult.setText("BMI Range=" + "" + Float.toString(bmiValue));
                        txtFinalValueResult.setTextColor(Color.RED);
                        txtFinalValueResult.setText("(Underweight)");

                        double temp=128.9-weightInPounds;
                        finalResult.setText("You will need to lose"+temp +"lbs to reach a BMI of 25");
                    }
                    else if(bmiValue>=18.5 && bmiValue<25)
                    {
                        txtResult.setText("BMI Range=" + "" + Float.toString(bmiValue) + "Normal");
                        TextView finalResult=(TextView)findViewById(R.id.final_result);

                        TextView txtFinalValueResult=(TextView)findViewById(R.id.txtFinalResult);
                        txtFinalValueResult.setTextColor(Color.GREEN);
                        txtFinalValueResult.setText("(Normal)");
                        finalResult.setText("Keep up the good work !!");


                    }
                    else if(bmiValue>=25 && bmiValue<30)

                    {
                        TextView finalResult=(TextView)findViewById(R.id.final_result);

                        txtResult.setText("BMI Range=" + "" + Float.toString(bmiValue) + "OverWeight");

                        TextView txtFinalValueResult=(TextView)findViewById(R.id.txtFinalResult);
                        txtFinalValueResult.setTextColor(Color.YELLOW);
                        txtFinalValueResult.setText("(OverWeight)");

                        double temp=weightInPounds - 174.2;
                        finalResult.setText("You will need to lose"+temp +"lbs to reach a BMI of 25");
                    }
                    else if(bmiValue>=30)
                    {
                        TextView finalResult=(TextView)findViewById(R.id.final_result);

                        txtResult.setText("BMI Range=" + "" + Float.toString(bmiValue) + "Obese");

                        TextView txtFinalValueResult=(TextView)findViewById(R.id.txtFinalResult);
                        txtFinalValueResult.setTextColor(Color.BLUE);
                        txtFinalValueResult.setText("(Obese)");
                    }


                }

            }
        });


    }
}
