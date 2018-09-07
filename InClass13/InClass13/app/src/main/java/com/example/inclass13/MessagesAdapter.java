package com.example.inclass13;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by nalin on 4/24/2018.
 */

public class MessagesAdapter extends ArrayAdapter<Messages> {


  //  FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference();

    public MessagesAdapter(@NonNull Context context, int resource, @NonNull List<Messages> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Messages messages = getItem(position);

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inboxlayout, parent, false);
            viewHolder = new MessagesAdapter.ViewHolder();
            viewHolder.txtViewMessageDescription = convertView.findViewById(R.id.txtViewMessage);
            viewHolder.messageSender = convertView.findViewById(R.id.txtViewSenderName);
            viewHolder.messageDate = convertView.findViewById(R.id.txtViewDateTime);
            viewHolder.isMessageRead=convertView.findViewById(R.id.imgViewMessageRead);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (MessagesAdapter.ViewHolder) convertView.getTag();

        }

        viewHolder.txtViewMessageDescription.setText(messages.messagedescription);

        mref.child("Users").child(messages.messageSender).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                viewHolder.messageSender.setText(dataSnapshot.child("firstname").getValue(String.class)+" "+
                dataSnapshot.child("lastname").getValue(String.class));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        viewHolder.messageDate.setText(messages.dateTimeOfMessage);

        if(messages.isMessageRead==false)
        {
            viewHolder.isMessageRead.setImageResource(R.drawable.circle_blue);
        }
        else
        {
            viewHolder.isMessageRead.setImageResource(R.drawable.circle_grey);
        }







        return  convertView;


    }


    public static class ViewHolder
    {
        TextView txtViewMessageDescription;
        TextView messageSender;
        TextView messageDate;
        ImageView isMessageRead;
    }
}
