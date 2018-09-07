package com.example.inclass12;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //FirebaseUser user = auth.getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public  DatabaseReference mref=database.getReference();

    public DatabaseReference mUsers;
    EditText editTextEmail;
    EditText editTextRepeatPassword;
    EditText editTextUserFirstName;
    EditText editTextUserLastName;
    EditText editTextChoosePassword;
    Button btnCancel;
    Button btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        setTitle(R.string.SignUp);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        editTextRepeatPassword = (EditText) findViewById(R.id.editTextRepeatPassword);
        editTextUserFirstName=(EditText)findViewById(R.id.editTextFirstName);
        editTextUserLastName=(EditText)findViewById(R.id.editTextLastName);
        btnSignUp=(Button)findViewById(R.id.btnSignUpOnSignUpPage);
        editTextChoosePassword=(EditText)findViewById(R.id.editTextChoosePassword);
        btnCancel=(Button)findViewById(R.id.btnCancel);

        mAuth=FirebaseAuth.getInstance();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(editTextUserFirstName.length()==0 || editTextUserLastName.length()==0 ||
                        editTextEmail.length()==0 || editTextChoosePassword.length()==0 ||
                        editTextRepeatPassword.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please provide values for every field",Toast.LENGTH_SHORT).show();
                }
                else if(!editTextChoosePassword.getText().toString().equals(editTextRepeatPassword.getText().toString()))
                {
                   Toast.makeText(getApplicationContext(),
                           "CHoose password and repeat password should be same",Toast.LENGTH_SHORT).show();
                }else if(editTextRepeatPassword.getText().toString().length()<6)
                {
                    Toast.makeText(getApplicationContext(),"Password should be atleast 6 characters",Toast.LENGTH_SHORT).show();
                }
                else {


                    mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextRepeatPassword.getText().toString())
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    if (task.isSuccessful()) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    mdatabase = FirebaseDatabase.getInstance().getReference();

                                        User user = new User(editTextUserFirstName.getText().toString(), editTextUserLastName.getText().toString());
                                        mref.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                                        Toast.makeText(getApplicationContext(), "User Successfully Created",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SignUp.this, ThreadActivity.class);
                                        // intent.putExtra("userobject",user);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
