package com.example.saipavanraju.hw2_group25;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {
    public  int hour_val;
    public int minute_val;
    public int year_val;
    public int month_val;
    public int day_val;
    EditText title;
    EditText date;
    EditText time;
    RadioGroup rg;
    RadioButton rb;

    private DatePickerDialog.OnDateSetListener datePicker;
    private TimePickerDialog.OnTimeSetListener timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.birthdaycake);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setTitle(R.string.create_task);





        title = findViewById(R.id.edit_title);
        date = findViewById(R.id.edit_date);
        date.setKeyListener(null);
        time = findViewById(R.id.edit_time);
        time.setKeyListener(null);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        CreateActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        datePicker,
                        year, month, day
                );

                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }
        });

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year_val = year;
                month_val = month;
                day_val = dayOfMonth;
                if (month_val<10&& day_val<10){
                    date.setText("0"+(month_val+1)+"/0"+day_val+"/"+year_val);

                }else if (month_val<10 ){
                    date.setText("0"+(month_val+1)+"/"+day_val+"/"+year_val);
                }else if (day_val<10){
                    date.setText(month_val+1+"/0"+day_val+"/"+year_val);

                }else{
                    date.setText(month_val+1+"/"+day_val+"/"+year_val);

                }
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal2 = Calendar.getInstance();
                int _hour = cal2.get(Calendar.HOUR);
                int _minute = cal2.get(Calendar.MINUTE);
                final TimePickerDialog timeDialog = new TimePickerDialog(
                        CreateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour_val = hourOfDay;
                                minute_val = minute;
                                int temp_time;
                                String format ;
                                if (hourOfDay > 12){
                                     temp_time = hour_val -12;
                                     format = "PM";
                                }else {
                                    temp_time = hour_val;
                                    format = "AM";
                                }
                                if (minute_val <10){
                                    time.setText(temp_time+": 0"+minute_val +" "+ format );

                                }else{
                                    time.setText(temp_time+":"+minute_val +" "+ format );

                                }
                            }
                        },
                        _hour, _minute,false
                );
                timeDialog.show();
            }
        });

        rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = findViewById(checkedId);
            }
        });


        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "onClick: "+title.getText().length());
                if ( title.getText().length() != 0 && date.getText().length()!=0 && time.getText().length()!= 0 && rg.getCheckedRadioButtonId() != -1){
                    long d = getDateTime(year_val,month_val,day_val,hour_val,minute_val);
                    Task newTask = new Task(title.getText().toString(),d,rb.getText().toString());
                    Intent i = new Intent();
                    i.putExtra("newTask",newTask);
                    setResult(RESULT_OK,i);
                    finish();
                }else{
                    if (title.getText().length() == 0){
                        Toast toast = Toast.makeText(CreateActivity.this, "Title Field Required",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }else
                    if (date.getText().length() == 0){
                        Toast toast = Toast.makeText(CreateActivity.this, "Date Field Required",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }else
                    if (time.getText().length() == 0){
                        Toast toast = Toast.makeText(CreateActivity.this, "Time Field Required",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }else
                    if (rg.getCheckedRadioButtonId() == -1){
                        Toast toast = Toast.makeText(CreateActivity.this, "Priority Field Required",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }
        });




    }
    public static long getDateTime(int year,int month, int day,int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month+1);
        cal.set(Calendar.YEAR, year);

        Date d = cal.getTime();
        long time = cal.getTimeInMillis();
        return time;
    }

}
