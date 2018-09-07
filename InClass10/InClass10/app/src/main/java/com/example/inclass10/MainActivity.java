package com.example.inclass10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    public Gson gson = new Gson();

    EditText editTextEmail;
    EditText editTextPassword;

    Button btnLogin;

    Button btnSignUp;

    String token;
    String useremail,user_fname,user_lname,user_id;
    UserSignUp userSignUp=new UserSignUp();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignup);

        SharedPreferences sharedPreferences = getSharedPreferences("Chatroom", MODE_PRIVATE);

        token = sharedPreferences.getString("token", null);
        useremail=sharedPreferences.getString("user_email",null);
        user_fname=sharedPreferences.getString("user_fname",null);
        user_lname=sharedPreferences.getString("user_lname",null);
        user_id=sharedPreferences.getString("user_id",null);


        if(token!=null && useremail!=null)
        {

              sharedPreferences = getSharedPreferences("Chatroom", MODE_PRIVATE);
              editor = sharedPreferences.edit();
              editor.putString("token", token);
              editor.putString("user_fname",user_fname);
              editor.putString("user_lname",user_lname);
              editor.putString("user_id",user_id);
              editor.commit();
              Intent intent = new Intent(MainActivity.this, Threads.class);
              startActivity(intent);
        }


        Log.d("demo", "  " + token);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextEmail.length()!=0 && editTextPassword.length()!=0)
                {
                    performLogin(editTextEmail.getText().toString(),editTextPassword.getText().toString());
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter username and password fields",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);


            }
        });


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    public void performLogin(String email, String password) {
        final RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        final Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("demo", " " + String.valueOf(Thread.currentThread().getId()));
                        String str = null;
                        try {
                            str = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                         userSignUp = gson.fromJson(str, UserSignUp.class);

                        if(userSignUp.getStatus().equals("ok")) {
                            sharedPreferences = getSharedPreferences("Chatroom", MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("token", userSignUp.getToken().toString());
                            editor.putString("user_email",userSignUp.getUser_email().toString());
                            editor.putString("user_fname",userSignUp.getUser_fname().toString());
                            editor.putString("user_lname",userSignUp.getUser_lname().toString());
                            editor.putString("user_id",userSignUp.getUser_id().toString());
                            editor.commit();
                            Intent intent=new Intent(MainActivity.this,Threads.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}

