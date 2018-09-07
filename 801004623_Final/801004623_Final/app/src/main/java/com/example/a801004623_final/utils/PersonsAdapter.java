package com.example.a801004623_final.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.a801004623_final.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by mshehab on 5/6/18.
 */

public class PersonsAdapter extends ArrayAdapter<Person>{

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference();


    public PersonsAdapter(Context context, int resource, List<Person> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Person person = getItem(position);
        final ViewHolder viewHolder;

        if(convertView == null){ //if no view to re-use then inflate a new one
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.person_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            viewHolder.textViewPriceBudget = (TextView) convertView.findViewById(R.id.textViewPriceBudget);
            viewHolder.textViewGifts = (TextView) convertView.findViewById(R.id.textViewGifts);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //set the data from the person object




       viewHolder.textViewName.setText(person.name);



        viewHolder.textViewPriceBudget.setText("$"+String.valueOf(person.totalBought)+"/"+"$"+String.valueOf(person.totalBudget)); //setup the text and colors

        viewHolder.textViewGifts.setText(""); //setup the text


        return convertView;
    }

    //View Holder to cache the views
    private static class ViewHolder{
        TextView textViewName;
        TextView textViewPriceBudget;
        TextView textViewGifts;

    }
}
