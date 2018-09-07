package com.example.saipavanraju.inclass10;

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

public class sign_up extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "onClick: ");
                EditText email = findViewById(R.id.editTextEmail);
                EditText f_name = findViewById(R.id.editTextFirstName);
                EditText l_name = findViewById(R.id.editTextLastName);
                EditText pass1 = findViewById(R.id.editTextChoosePassword);
                EditText pass2 = findViewById(R.id.editTextRepeatPassword);
                //if (email.getText().length()>0 && f_name.getText().length()>0&& l_name.getText().length()>0 && pass1.getText().length()>0 && pass2.getText().length()>0 ){
                  //  if (pass1.getText() == pass2.getText()){
                signup(f_name.getText().toString(),l_name.getText().toString(),email.getText().toString(),pass1.getText().toString());
                   // }
                //}

            }
        });

    }

    public void signup(String fname, String lname, String email, String pass){
        Log.d("demo", "signup: "+ fname);
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", pass)
                .add("fname", fname)
                .add("lname", lname)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/signup")
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

                }
            }
        });
    }


}
