package com.example.saipavanraju.inclass10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("Chatroom", MODE_PRIVATE);
        String tk = prefs.getString("token","no name");
        Log.d("demo", "onCreate: "+tk);
        if (tk.length() > 0){
            Intent i = new Intent(this, Messages.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this, login.class);
            startActivity(i);
        }
//        SharedPreferences prefs = getSharedPreferences("Chatroom", MODE_PRIVATE);

//        Log.d("demo", "onResponse: "+ prefs.getString("name","no name"));





    }




}
