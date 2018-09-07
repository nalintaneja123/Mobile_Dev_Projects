package com.example.saipavanraju.hw2_group25;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private int CREATE_REQ = 1000;
    private int EDIT_REQ = 1001;
    LinkedList<Task> taskList = new LinkedList<Task>();
    TextView textTitle;
    TextView textDate;
    TextView textTime;
    TextView textPriority;
    TextView textNavInfo;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.birthdaycake);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setTitle(R.string.view_task);



        textTitle = findViewById(R.id.text_taskName);
        textDate = findViewById(R.id.text_date);
        textTime = findViewById(R.id.text_time);
        textPriority = findViewById(R.id.text_priority);
        textNavInfo = findViewById(R.id.text_nav);

        if (taskList.size() == 0) {
            textTitle.setText("No Tasks, Create A task");
            textDate.setText("NA");
            textTime.setText("NA");
            textPriority.setText("NA");
            textNavInfo.setText("NA");
        }

        findViewById(R.id.btn_addTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                intent.putExtra("Create", "Task");
                startActivityForResult(intent, CREATE_REQ);
            }
        });

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nxt = validator(taskList.size(), index, "Next");
                if (nxt) {
                    index = index + 1;
                    updateView(taskList, textTitle, textDate, textTime, textPriority, textNavInfo, index);
                }
            }
        });
        findViewById(R.id.btn_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean prev = validator(taskList.size(), index, "Prev");
                if (prev) {
                    index = index - 1;
                    updateView(taskList, textTitle, textDate, textTime, textPriority, textNavInfo, index);
                }
            }
        });
        findViewById(R.id.btn_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskList.size() >1){
                    index = 0;
                    updateView(taskList, textTitle, textDate, textTime, textPriority, textNavInfo, index);
                }else{
                    Toast toast = Toast.makeText(MainActivity.this, "Current Task is First Task", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        findViewById(R.id.btn_last).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskList.size() > 1 ) {
                    index = taskList.size() -1;
                    updateView(taskList, textTitle, textDate, textTime, textPriority, textNavInfo, index);
                }else {
                    Toast toast = Toast.makeText(MainActivity.this, "Current Task is Last Task", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskList.size() != 0 ) {
                    Intent intent = new Intent(getBaseContext(),EditActivity.class);
                    Task t = taskList.get(index);
                    intent.putExtra("index",index);
                    intent.putExtra("Edit",t);
                    startActivityForResult(intent,EDIT_REQ);
                }
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskList.size()>1){
                    taskList.remove(index);
                    index = 0;
                    updateView(taskList, textTitle, textDate, textTime, textPriority, textNavInfo, index);
                }else if (taskList.size() == 1){
                    taskList.remove(index);
                    index = 0;
                    textTitle.setText("No Tasks");
                    textDate.setText("-");
                    textTime.setText("-");
                    textPriority.setText("-");
                    textNavInfo.setText("-");
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_REQ) {
            if (resultCode == RESULT_OK) {
                // getIntent().getExtras().getString("newTask").toString();
                Task t = data.getExtras().getParcelable("newTask");
                taskList.add(t);

                if (taskList.size() == 1) {
                    index = 0;
                }
                updateView(taskList, textTitle, textDate, textTime, textPriority, textNavInfo, index);
                Log.d("demo", "onActivityResult: " + data.getExtras().getParcelable("newTask").toString());
            }
        }
        if (requestCode == EDIT_REQ) {
            if (resultCode == RESULT_OK) {
                // getIntent().getExtras().getString("newTask").toString();
                Task t = data.getExtras().getParcelable("editedTask");
                int i = data.getExtras().getInt("index");
                Log.d("demo", "onActivityResult: "+t.toString());
                taskList.set(i,t);
                int res = indexCal(taskList,t);
                Log.d("demo", "onActivityResult: "+taskList.size());
                Log.d("demo", "onActivityResult: "+i+" "+taskList.get(i));
                index = res;
                updateView(taskList, textTitle, textDate, textTime, textPriority, textNavInfo, res);
            }
        }
    }

    public static void updateView(LinkedList<Task> tasks, TextView textTitle, TextView textDate, TextView textTime, TextView textPriority, TextView textNavInfo, int index) {
        Collections.sort(tasks);
        Task task = tasks.get(index);
        textTitle.setText(task.getTitle().toString());
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(task.getDateTime());
        String date;
        if (cl.get(Calendar.DAY_OF_MONTH)<10&&  cl.get(Calendar.MONTH) <10){
             date = "0" + cl.get(Calendar.DAY_OF_MONTH) + "/0" + cl.get(Calendar.MONTH) + "/" + cl.get(Calendar.YEAR);

        }else if ( cl.get(Calendar.DAY_OF_MONTH) <10 ){
             date = "0" + cl.get(Calendar.DAY_OF_MONTH) + "/" + cl.get(Calendar.MONTH) + "/" + cl.get(Calendar.YEAR);
        }else if (cl.get(Calendar.MONTH)<10){
             date =  ""+ cl.get(Calendar.DAY_OF_MONTH) + "/0" + cl.get(Calendar.MONTH) + "/" + cl.get(Calendar.YEAR);

        }else{
             date = "" + cl.get(Calendar.DAY_OF_MONTH) + "/" + cl.get(Calendar.MONTH) + "/" + cl.get(Calendar.YEAR);

        }
        String format;
        int temp_time;
        if (cl.get(Calendar.AM_PM) ==0){
            format = "AM";
            temp_time = cl.get(Calendar.HOUR_OF_DAY);
        }else {
            format = "PM";
            temp_time = cl.get(Calendar.HOUR_OF_DAY)-12;
        }
        String time;
        if (cl.get(Calendar.MINUTE)<10){
             time = "" + temp_time + ":0" + cl.get(Calendar.MINUTE)+" "+format;

        }else{
             time = "" + temp_time + ":" + cl.get(Calendar.MINUTE)+" "+format;

        }
        textDate.setText(date);
        textTime.setText(time);
        Log.d("demo", "updateView: " + getDate(task.getDateTime(), "MMM/dd/yyyy"));
        textPriority.setText(task.getPrioity() + " Priority");
        int disp = index + 1;
        String Nav = "Task " + disp + " of " + tasks.size();
        textNavInfo.setText(Nav);

    }

    public static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public boolean validator(int size, int index, String option) {
        if (size == 0) {
            Toast toast = Toast.makeText(MainActivity.this, "No Tasks to Display",
                    Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else {
            if (option == "Next") {
                if (size == index + 1) {
                    Toast toast = Toast.makeText(MainActivity.this, "Current Task is Last Task",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                } else {
                    return true;
                }
            } else if (option == "Prev") {
                if (index == 0) {
                    Toast toast = Toast.makeText(MainActivity.this, "Current Task is First Task",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }


    public int indexCal(LinkedList<Task> taskList, Task t) {
        Collections.sort(taskList);
        int i =taskList.indexOf(t);
        return i;
    }
}