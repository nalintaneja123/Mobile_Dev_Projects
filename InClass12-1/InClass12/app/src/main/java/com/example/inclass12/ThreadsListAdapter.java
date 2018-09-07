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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nalin on 4/17/2018.
 */

public class ThreadsListAdapter extends ArrayAdapter<ThreadFromFireBase> {


    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();

    ArrayList<ThreadFromFireBase> list=new ArrayList<>();


    public ThreadsListAdapter(@NonNull Context context, int resource, @NonNull List<ThreadFromFireBase> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);


        final ThreadFromFireBase threadFromFireBase=getItem(position);

        ViewHolder viewHolder;

        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.threadslayout,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.txtViewThreadName=convertView.findViewById(R.id.txtViewThreadName);
            viewHolder.imgViewDelete=convertView.findViewById(R.id.imgViewDelete);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }


        viewHolder.txtViewThreadName.setText(threadFromFireBase.title);

        if(threadFromFireBase.user_id.equals(auth.getCurrentUser().getUid()))
        {
            viewHolder.imgViewDelete.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.imgViewDelete.setVisibility(View.INVISIBLE);
        }

        viewHolder.imgViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                myRef.child("Threads").child(threadFromFireBase.getKey()).removeValue();
                list.remove(position);
                notifyDataSetChanged();


            }
        });

        return  convertView;

    }


    public void enableDeleteButton( ArrayList<ThreadFromFireBase> list)
    {


        this.list=list;


    }


    public static class ViewHolder
    {
        TextView txtViewThreadName;
        ImageView imgViewDelete;
    }
}
