package com.example.inclass10;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Messages extends AppCompatActivity {



    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token;
    String threadTitle;
    String threadID;
    TextView threadname;
    String userID;

    ArrayList<ThreadMessages> messageslist=new ArrayList<>();
    ListView listViewmessage;
    ImageView imgViewLogoutOnMessagesscreen;

    MessagesAdapter messagesAdapter;
    EditText editTextAddMessageToThread;

    ThreadMessages threadMessages;
    Gson gson=new Gson();

    Button btnSend;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        listViewmessage=(ListView)findViewById(R.id.listViewMessages);
        btnSend=(Button)findViewById(R.id.btnSendMessage);
        editTextAddMessageToThread=(EditText)findViewById(R.id.editTextAddMessageToThread);
        sharedPreferences = getSharedPreferences("Chatroom", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        threadname=(TextView)findViewById(R.id.txtViewThreadTitle);
        imgViewLogoutOnMessagesscreen=(ImageView)findViewById(R.id.imageViewLogoutOnMessagescreen);
        token = sharedPreferences.getString("token", null);
        userID=sharedPreferences.getString("user_id",null);


        if(getIntent()!=null && getIntent().getExtras()!=null) {
            messageslist.clear();
            messageslist = (ArrayList<ThreadMessages>) getIntent().getExtras().getSerializable("messages");
            Collections.reverse(messageslist);
            threadTitle=getIntent().getExtras().getString("threadname");
            threadID=getIntent().getExtras().getString("threadID");

        }


        imgViewLogoutOnMessagesscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        threadname.setText(threadTitle);

        refreshListView();

//        messagesAdapter=new MessagesAdapter(this,R.layout.messageslayout,messageslist);
//        messagesAdapter.enabledeleteButtonValue(userID,token,getApplicationContext());
//        listViewmessage.setAdapter(messagesAdapter);
//        messagesAdapter.notifyDataSetChanged();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessage(editTextAddMessageToThread.getText().toString(),threadID,token);
                editTextAddMessageToThread.setText("");

            }
        });

    }

    public void sendMessage(String message, String threadID, final String token)
    {


        final RequestBody formBody = new FormBody.Builder()
                .add("message",message)
                .add("thread_id",threadID)
                .build();
        final Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/add")
                .addHeader("Authorization","BEARER"+"  "+token)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                final String responseBody=response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject root=new JSONObject(responseBody);
                            JSONObject messageObject=root.getJSONObject("message");
                            threadMessages = gson.fromJson(String.valueOf(messageObject), ThreadMessages.class);
                            messageslist.add(threadMessages);
                            messagesAdapter.notifyDataSetChanged();
                            messagesAdapter.enabledeleteButtonValue(userID,token,getApplicationContext(),messageslist);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }


    public void refreshListView()
    {
        messagesAdapter=new MessagesAdapter(this,R.layout.messageslayout,messageslist);
        messagesAdapter.enabledeleteButtonValue(userID,token,getApplicationContext(),messageslist);
        listViewmessage.setAdapter(messagesAdapter);
        messagesAdapter.notifyDataSetChanged();
    }


}
