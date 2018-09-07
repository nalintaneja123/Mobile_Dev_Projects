package com.example.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class displayActivity extends AppCompatActivity {
     public  static final int name_req = 1000;
     public  static final int email_req = 1001;
     public  static final int depatment_req =1002;
     public  static final int mood_req = 1003;
     public static final String Option_key = "Option";
     public static final String Value_key = "Value";
     Student s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle(R.string.disp_activity);
        TextView txt_name = (TextView) findViewById(R.id.textView_name);
        TextView txt_email = (TextView)  findViewById(R.id.textView_email);
        TextView txt_dept = (TextView)  findViewById(R.id.textView_dept);
        TextView txt_mood = (TextView)  findViewById(R.id.textView_mood);




        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            s1= getIntent().getExtras().getParcelable(MainActivity.STUDENT_KEY);
            txt_name.setText("Name : " + s1.getName());
            txt_email.setText("Email : "+s1.getEmail());
            txt_dept.setText("Department : "+s1.getDepartment());
            txt_mood.setText("Mood : "+s1.getMood()+ " % Positive");
        }



        findViewById(R.id.button_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");

                intent.putExtra("Name", s1.getName());
                startActivityForResult(intent, name_req);
            }
        });
        findViewById(R.id.button_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");

                intent.putExtra("Email", s1.getEmail());
                startActivityForResult(intent,email_req);
            }
        });
        findViewById(R.id.button_dept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");

                intent.putExtra("Department", s1.getDepartment());
                startActivityForResult(intent,depatment_req);
            }
        });
        findViewById(R.id.button_mood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");

                intent.putExtra("Mood", s1.getMood());
                startActivityForResult(intent,mood_req);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == name_req){
            s1.setName(data.getExtras().getString(Value_key));
            TextView txt_name = (TextView) findViewById(R.id.textView_name);
            txt_name.setText("Name : " + data.getExtras().getString(Value_key));
        }
        if (requestCode == email_req){
            s1.setEmail(data.getExtras().getString(Value_key));
            TextView txt_name = (TextView) findViewById(R.id.textView_email);
            txt_name.setText("Email : " + data.getExtras().getString(Value_key));
        }
        if (requestCode == depatment_req){
            s1.setDepartment(data.getExtras().getString(Value_key));
            TextView txt_name = (TextView) findViewById(R.id.textView_dept);
            txt_name.setText("Department : " + data.getExtras().getString(Value_key));
        }
        if (requestCode == mood_req){
            s1.setMood(data.getExtras().getString(Value_key));
            TextView txt_name = (TextView) findViewById(R.id.textView_mood);
            txt_name.setText("Mood : " + data.getExtras().getString(Value_key)+" % Positive");
        }

    }
}
