package com.example.saipavanraju.inclass10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.editTextEmail);
                EditText pass = findViewById(R.id.editTextPassword);
                ulogin(email.getText().toString(),pass.getText().toString());
            }
        });

        findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, sign_up.class);
                startActivity(i);
            }
        });

    }

    public void ulogin( String email, String pass){
   ;
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", pass)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String res = response.body().string();
                Log.d("demo", "onResponse: "+ res);
                Gson gson = new Gson();

                UserSignUp userSignUp = gson.fromJson(res, UserSignUp.class);

                Log.d("demo", "onResponse: "+ userSignUp.getStatus());
                if (userSignUp.getStatus().equals("ok")){
                    Log.d("demo", "onResponse:innn ");
                    SharedPreferences.Editor editor = getSharedPreferences("Chatroom", MODE_PRIVATE).edit();
                    editor.putString("token", userSignUp.getToken().toString());
                    editor.apply();

                    Intent i = new Intent(login.this, Messages.class);
                    startActivity(i);

                }
            }
        });
    }

}
