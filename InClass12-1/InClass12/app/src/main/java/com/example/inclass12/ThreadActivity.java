package com.example.inclass12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThreadActivity extends AppCompatActivity {

    ImageView imgViewLogout;
    ImageView imgViewAddThread;
    EditText editTextAddThread;

    TextView txtViewUserName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


   ArrayList<ThreadFromFireBase> threadTitlesList=new ArrayList<>();
   DatabaseReference myRef=database.getReference();
    DatabaseReference myRef_threads;

    ThreadFromFireBase threadFromFireBase;

    ThreadsListAdapter threadsListAdapter;
    ListView listView;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);


        setTitle(R.string.TitleOfThreadScreen);


        imgViewLogout=(ImageView)findViewById(R.id.imgViewLogoutOnThreadScreen);
        imgViewAddThread=(ImageView)findViewById(R.id.imgViewAddThread);
        editTextAddThread=(EditText)findViewById(R.id.editTextAddThread);
        txtViewUserName=(TextView)findViewById(R.id.txtViewUserName);
        listView=(ListView)findViewById(R.id.listView);

        firebaseUser=mAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null)
        {
            DatabaseReference useronSignup=myRef.child("Users").child(firebaseUser.getUid());

            useronSignup.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user=dataSnapshot.getValue(User.class);
                    txtViewUserName.setText(user.firstname+"  "+user.lastname);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

       }





        imgViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        imgViewAddThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextAddThread.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please enter value of Thread",Toast.LENGTH_SHORT).show();
                }
                else {

                    myRef_threads = myRef.child("Threads").push();
                    myRef_threads.child("UserID").setValue(firebaseUser.getUid());

                    myRef_threads.child("title").setValue(editTextAddThread.getText().toString());


                    editTextAddThread.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextAddThread.getWindowToken(), 0);


                    threadsListAdapter.notifyDataSetChanged();
                }
            }
        });


        DatabaseReference mref1=myRef.child("Threads");

        mref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              threadTitlesList.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                        threadFromFireBase = new ThreadFromFireBase();
                        threadFromFireBase.title = snapshot.child("title").getValue(String.class);
                        threadFromFireBase.user_id=snapshot.child("UserID").getValue(String.class);
                        threadFromFireBase.key=snapshot.getKey();
                        threadTitlesList.add(threadFromFireBase);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        threadsListAdapter=new ThreadsListAdapter(this,R.layout.threadslayout,threadTitlesList);
        threadsListAdapter.enableDeleteButton(threadTitlesList);
        listView.setAdapter(threadsListAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ThreadFromFireBase threadFromFireBase=threadsListAdapter.getItem(i);
                getMessagesOnRowClick(threadFromFireBase,firebaseUser.getUid());

            }
        });

    }

    public void getMessagesOnRowClick(ThreadFromFireBase threadFromFireBase,String uuid) {

        Intent intent=new Intent(ThreadActivity.this,MessagesActivity.class);
        intent.putExtra("threadfromfirebaseobject",threadFromFireBase);
        startActivity(intent);
    }


}
