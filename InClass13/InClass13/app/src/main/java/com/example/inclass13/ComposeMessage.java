package com.example.inclass13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ComposeMessage extends AppCompatActivity {


    ImageView imgViewSelectReceivers;
    AlertDialog.Builder builder;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference();

    TextView txtViewSelectReceivers;
    TextView txtViewKeyOfReceiver;
    EditText editTextMessageDescription;
    Button btnSend;



  ArrayList<String> arrayListOfUsers=new ArrayList<>();
  ArrayList<String> keys=new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);


        setTitle("Compose Message");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_title_messagescreen);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        imgViewSelectReceivers=(ImageView)findViewById(R.id.imgViewSelectSenders);
        txtViewSelectReceivers=(TextView)findViewById(R.id.txtViewSenderName);
        txtViewKeyOfReceiver=(TextView)findViewById(R.id.txtViewkeyOfReceiver);
        editTextMessageDescription=(EditText) findViewById(R.id.editTextComposeMessage);
        btnSend=(Button)findViewById(R.id.btnSend);


        if(getIntent().getExtras()!=null)
        {
            final String receiverName=getIntent().getExtras().getString("selectrepliername");
            final String receiverkey=getIntent().getExtras().getString("replierkey");
            txtViewSelectReceivers.setText(receiverName);
            txtViewKeyOfReceiver.setText(receiverkey);
            imgViewSelectReceivers.setEnabled(false);
        }




        final DatabaseReference mrefUsers=reference.child("Users");

        mrefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    arrayListOfUsers.add(snapshot.child("firstname").getValue(String.class)+" "
                            +snapshot.child("lastname").getValue(String.class));

                    keys.add(snapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        imgViewSelectReceivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(ComposeMessage.this);
                builder.setTitle("Users");

                CharSequence[] users = (arrayListOfUsers.toArray(new String[arrayListOfUsers.size()]));

                 builder.setItems(users, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {

                         txtViewSelectReceivers.setText(arrayListOfUsers.get(i));
                         txtViewKeyOfReceiver.setText(keys.get(i));

                     }
                 });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextMessageDescription.length() >0 && txtViewSelectReceivers.length()>0){

                    String messagetoBeSent=editTextMessageDescription.getText().toString();

                    DatabaseReference mMailBoxes=reference.child("MailBoxes").child(txtViewKeyOfReceiver.getText().toString()).push();

                    mMailBoxes.child("MessageDescription").setValue(messagetoBeSent);
                    mMailBoxes.child("MessageSender").setValue(auth.getCurrentUser().getUid());

                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/dd/yy hh:mm a");
                    mMailBoxes.child("MessageDate").setValue(simpleDateFormat.format(new Date()));

                    mMailBoxes.child("IsRead").setValue(false);

                    Toast.makeText(getApplicationContext(),"Message sent successfully",Toast.LENGTH_SHORT).show();

                    Intent intent =new Intent(ComposeMessage.this,InboxActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Messages or receiver fields are empty",Toast.LENGTH_SHORT).show();

                }





            }
        });





    }
}
