package com.example.inclass13;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class InboxActivity extends AppCompatActivity {


    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference();


   private ArrayList<Messages> messagesArrayList=new ArrayList<Messages>();

    Messages messages;
    ListView listViewMessages;

    MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle(R.string.inbox);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_title_messagescreen);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        listViewMessages=(ListView)findViewById(R.id.listViewInbox);

        DatabaseReference mMessages=mref.child("MailBoxes").child(auth.getCurrentUser().getUid());

        if(getIntent().getExtras()!=null)
        {
            setArrayList();

        }


        mMessages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // ArrayList<Messages> testlist=new ArrayList<Messages>();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    messages=new Messages();
                    messages.messagedescription=snapshot.child("MessageDescription").getValue(String.class);
                    messages.messageSender=snapshot.child("MessageSender").getValue(String.class);
                    messages.dateTimeOfMessage=snapshot.child("MessageDate").getValue(String.class);
                    messages.isMessageRead=snapshot.child("IsRead").getValue(Boolean.class);
                    messages.messageKey=snapshot.getKey();
                    messagesArrayList.add(messages);
                }

                Collections.reverse(messagesArrayList);

                setArrayList();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        listViewMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Messages messages=messagesAdapter.getItem(i);


                Intent intent=new Intent(InboxActivity.this,ReadMessage.class);
                intent.putExtra("selectmessagedetail",messages);
             //   intent.putExtra("index",i);
                startActivity(intent);



            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void setArrayList()
    {

        messagesAdapter=new MessagesAdapter(this,R.layout.inboxlayout,messagesArrayList);
        messagesAdapter.notifyDataSetChanged();
        listViewMessages.setAdapter(messagesAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.compose_message_menu:

                Intent intent=new Intent(InboxActivity.this,ComposeMessage.class);

                startActivity(intent);

                return true;

            case R.id.logoutbutton:
                FirebaseAuth.getInstance().signOut();

                Intent intentlogin=new Intent(InboxActivity.this,LoginActivity.class);
                startActivity(intentlogin);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
