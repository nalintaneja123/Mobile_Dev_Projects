package com.example.inclass13;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadMessage extends AppCompatActivity {



    Messages messages;

    TextView txtViewSenderName;
    TextView txtViewMessageDescription;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference();


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(ReadMessage.this,InboxActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        txtViewSenderName=(TextView)findViewById(R.id.txtViewSenderName);
        txtViewMessageDescription=(TextView)findViewById(R.id.txtViewMessageDescription);

        setTitle("Read Message");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_title_messagescreen);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        if(getIntent().getExtras()!=null)
        {
            messages=(Messages) getIntent().getExtras().getSerializable("selectmessagedetail");
        }


        mref.child("Users").child(messages.messageSender).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                txtViewSenderName.setText(dataSnapshot.child("firstname").getValue(String.class)+" "+
                        dataSnapshot.child("lastname").getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        txtViewMessageDescription.setText(messages.messagedescription);


        mref.child("MailBoxes").child(auth.getCurrentUser().getUid()).child(messages.messageKey).
                child("IsRead").setValue(true);
       // messages.isMessageRead=true;








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.readmessagemenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()) {
            case R.id.delete_message_menu:

                mref.child("MailBoxes").child(auth.getCurrentUser().getUid()).child(messages.messageKey).removeValue();
                Intent intent=new Intent(ReadMessage.this,InboxActivity.class);
                startActivity(intent);

                 return  true;
             //  list.remove(position);

//                Toast.makeText(getApplicationContext(),"Delete message clilcked",Toast.LENGTH_SHORT).show();
//                return true;

            case R.id.go_to_composemessage:

                Intent intentForCompose=new Intent(ReadMessage.this,ComposeMessage.class);
                intentForCompose.putExtra("selectrepliername",txtViewSenderName.getText().toString());
                intentForCompose.putExtra("replierkey",messages.messageSender);
                startActivity(intentForCompose);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
