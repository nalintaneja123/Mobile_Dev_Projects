package com.example.inclass12;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference myRef=firebaseDatabase.getReference();

    EditText editTextEmail;
    EditText editTextPassword;
    Button btnLogin;
    Button btnSignup;

    FirebaseUser currentUser;

    User user=new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup=(Button)findViewById(R.id.btnSignup);


        mAuth = FirebaseAuth.getInstance();




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextEmail.length()==0 || editTextPassword.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please provide email and password",Toast.LENGTH_SHORT).show();
                }
                else
                {


                mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(),editTextPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    currentUser=FirebaseAuth.getInstance().getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this, ThreadActivity.class);
                                  //  intent.putExtra("userobject", user);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                 }
            }
        });

       btnSignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(LoginActivity.this,SignUp.class);
               startActivity(intent);
           }
       });
    }



    @Override
    protected void onStart() {
        super.onStart();



        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null) {

            Intent intent = new Intent(LoginActivity.this, ThreadActivity.class);
         //   intent.putExtra("userobject", user);
            startActivity(intent);


        }


        }
    }






