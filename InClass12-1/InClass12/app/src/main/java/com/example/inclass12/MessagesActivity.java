package com.example.inclass12;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class MessagesActivity extends AppCompatActivity {


    TextView txtViewThreadTitle;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    ThreadFromFireBase threadFromFireBase;

    MessagesAdapter messagesAdapter;

    Messages messages;
    ImageView imgLogOutOnMessagesScreen;
    ArrayList<Messages> messageslist = new ArrayList<>();
    EditText editTextAddMessageTothread;
    ListView listViewMessages;

    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        setTitle(R.string.app_name);

        txtViewThreadTitle = (TextView) findViewById(R.id.txtViewThreadTitle);
        listViewMessages = (ListView) findViewById(R.id.listViewMessages);
        btnSend = (Button) findViewById(R.id.btnSendMessage);
        editTextAddMessageTothread = (EditText) findViewById(R.id.editTextAddMessageToThread);
        imgLogOutOnMessagesScreen=(ImageView)findViewById(R.id.imageViewLogoutOnMessagescreen);

        if (getIntent().getExtras() != null) {
            threadFromFireBase = (ThreadFromFireBase) getIntent().getExtras().getSerializable("threadfromfirebaseobject");
          //  uuid = getIntent().getExtras().getString("UUID");


        }

        txtViewThreadTitle.setText(threadFromFireBase.getTitle());
        refreshListView();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextAddMessageTothread.length() == 0 || editTextAddMessageTothread.length()==1) {

                    Toast.makeText(getApplicationContext(), "Please enter message", Toast.LENGTH_SHORT).show();
                } else
                    {

                    DatabaseReference mRef1 = myRef.child("Threads").child(threadFromFireBase.getKey())
                            .child("Messages").push();

                    mRef1.child("UserID").setValue(auth.getCurrentUser().getUid());
                    mRef1.child("Message").setValue(editTextAddMessageTothread.getText().toString());

                    //  mRef1.child("FirstName").setValue(user.firstname);


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    mRef1.child("Date").setValue(simpleDateFormat.format(new Date()));
                    editTextAddMessageTothread.setText(" ");


                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextAddMessageTothread.getWindowToken(), 0);
                    messagesAdapter.notifyDataSetChanged();

                }
            }
        });


        DatabaseReference mMessagesDisplayForSpecificThread = myRef.
                child("Threads").
                child(threadFromFireBase.getKey()).
                child("Messages");

       // final DatabaseReference mUsers=myRef.child("Users").child(uuid);


        mMessagesDisplayForSpecificThread.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot bbSnapshot) {

                messageslist.clear();
                for (DataSnapshot snapshot : bbSnapshot.getChildren()) {
                    messages = new Messages();
                    messages.messageTitle = snapshot.child("Message").getValue(String.class);
                    messages.created_at = snapshot.child("Date").getValue(String.class);
                    messages.user_id = snapshot.child("UserID").getValue(String.class);
                    messages.messageKey = snapshot.getKey();
                    messageslist.add(messages);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        imgLogOutOnMessagesScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    public void refreshListView() {

        messagesAdapter = new MessagesAdapter(this, R.layout.messageslayout, messageslist);
        messagesAdapter.enableDeleteButtonOnMessages(messageslist, threadFromFireBase.getKey());
        listViewMessages.setAdapter(messagesAdapter);

    }
}

