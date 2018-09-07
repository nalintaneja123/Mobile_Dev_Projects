package com.example.inclass12;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by nalin on 4/18/2018.
 */

public class MessagesAdapter extends ArrayAdapter<Messages> {



   FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
    ArrayList<Messages> list=new ArrayList<>();
    String threadKey;

    public MessagesAdapter(@NonNull Context context, int resource, @NonNull List<Messages> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final Messages threadMessages = getItem(position);

          final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.messageslayout, parent, false);
            viewHolder = new MessagesAdapter.ViewHolder();
            viewHolder.txtViewMessageDescription = convertView.findViewById(R.id.txtViewMessageDescription);
            viewHolder.messagesPostedBy = convertView.findViewById(R.id.txtViewMessagePostedBy);
            viewHolder.messageDate = convertView.findViewById(R.id.txtViewMessagePostedDate);
            viewHolder.imgMessageDelete = convertView.findViewById(R.id.imageViewMessageDelete);
            convertView.setTag(viewHolder);


        } else {
            viewHolder = (MessagesAdapter.ViewHolder) convertView.getTag();

        }



        final DatabaseReference mMessagesDisplay=myRef.child("Threads").child(threadKey).child("Messages");
        final DatabaseReference mUsersDisplay=myRef.child("Users");

        mMessagesDisplay.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                viewHolder.txtViewMessageDescription.setText(dataSnapshot.child(threadMessages.getMessageKey()).
                        child("Message").getValue(String.class));

                if(threadMessages.user_id.equals(auth.getCurrentUser().getUid()))
         {
             viewHolder.messagesPostedBy.setText("me");
         }
         else
         {
             mUsersDisplay.child(threadMessages.user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {

                     User user=dataSnapshot.getValue(User.class);
                     viewHolder.messagesPostedBy.setText(user.firstname+" "+user.lastname);

                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

         }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if(threadMessages.user_id.equals(auth.getCurrentUser().getUid()))
        {
            viewHolder.imgMessageDelete.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.imgMessageDelete.setVisibility(View.INVISIBLE);
        }

        if(threadMessages.created_at!=null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                Date date = simpleDateFormat.parse(threadMessages.created_at);

                PrettyTime prettyTime = new PrettyTime();

                viewHolder.messageDate.setText(prettyTime.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        viewHolder.imgMessageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.child("Threads").child(threadKey).child("Messages").child(threadMessages.getMessageKey()).removeValue();
                list.remove(position);
                notifyDataSetChanged();

            }
        });

        return convertView;
    }

    public void enableDeleteButtonOnMessages(ArrayList<Messages> messagesArrayList,String key)
    {

     this.list=messagesArrayList;
     this.threadKey=key;
    }


    public static class ViewHolder
    {
        TextView txtViewMessageDescription;
        TextView messagesPostedBy;
        TextView messageDate;
        ImageView imgMessageDelete;
    }
}
