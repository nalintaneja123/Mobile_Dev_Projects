package com.example.inclass13;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {



    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser currentUser;
    Button btnSignup;
    Button btnLogin;

    EditText editTextEmail;
    EditText editTextPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        setTitle(R.string.logingscreen);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_title_messagescreen);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        btnSignup=(Button)findViewById(R.id.btnSignup);
        btnLogin=(Button)findViewById(R.id.btnLogin);

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextEmail.length() > 0 && editTextPassword.length()>0){

                    auth.signInWithEmailAndPassword(editTextEmail.getText().toString(),editTextPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful())
                                    {
                                        Intent intent=new Intent(LoginActivity.this,InboxActivity.class);
                                        startActivity(intent);
                                    }

                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                }else {
                    Toast.makeText(getApplicationContext(),"Enter all credentials",Toast.LENGTH_SHORT).show();

                }



            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });






    }

    @Override
    protected void onStart() {

        super.onStart();
        currentUser=FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser!=null)
        {
            Intent intent=new Intent(LoginActivity.this,InboxActivity.class);
            startActivity(intent);
        }

    }
}
