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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    Button btnSignup;
    Button btnCancel;

    EditText editTextFirstname;
    EditText editTextLastname;
    EditText editTextEmail;
    EditText editTextpassword;
    EditText editTextconfirmPassword;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();

    public DatabaseReference mref=database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        setTitle(R.string.signupScreen);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_title_messagescreen);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        editTextFirstname=(EditText)findViewById(R.id.editTextFirstName);
        editTextLastname=(EditText)findViewById(R.id.editTextLastName);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextpassword=(EditText)findViewById(R.id.editTextPassword);
        editTextconfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
        btnSignup=(Button)findViewById(R.id.btnSignUpOnSignUpPage);
        btnCancel=(Button)findViewById(R.id.btnCancel);




        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextFirstname.length()==0 || editTextLastname.length()==0 ||
                        editTextEmail.length()==0 || editTextpassword.length()==0 ||
                        editTextconfirmPassword.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please provide values for every field",Toast.LENGTH_SHORT).show();
                }
                else if(!editTextpassword.getText().toString().equals(editTextconfirmPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Passwords dont match",Toast.LENGTH_SHORT).show();
                }
                else if(editTextpassword.getText().toString().length()<6)
                {
                    Toast.makeText(getApplicationContext(),"Password should be atleast 6 characters",Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextpassword.getText().toString()).
                            addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        User user = new User(editTextFirstname.getText().toString(), editTextLastname.getText().toString());
                                        mref.child("Users").child(auth.getCurrentUser().getUid()).setValue(user);
                                        Toast.makeText(getApplicationContext(), "User Successfully Created",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SignupActivity.this, InboxActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authentication failure", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
