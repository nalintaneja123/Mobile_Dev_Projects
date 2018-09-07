package com.example.a801004623_midterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by nalin on 3/12/2018.
 */

public class AppListViewAdapter  extends ArrayAdapter<App>{

    public AppListViewAdapter(@NonNull Context context, int resource, @NonNull List<App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);

        App apps=getItem(position);
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appdisplay,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.appname = convertView.findViewById(R.id.txtViewAppName);
            viewHolder.artistName = convertView.findViewById(R.id.txtViewArtist);
            viewHolder.imageViewApp=convertView.findViewById(R.id.imgOfApp);
            viewHolder.releaseDate = convertView.findViewById(R.id.txtViewReleaseDate);
            viewHolder.genere=convertView.findViewById(R.id.txtViewGenre);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.appname.setText(apps.appName);
        viewHolder.artistName.setText(apps.ArtistName);
        viewHolder.releaseDate.setText(apps.ReleaseDate);
        Picasso.with(getContext()).load(apps.appImage).into(viewHolder.imageViewApp);


        StringBuilder builder=new StringBuilder();
        for(int i=0;i<apps.genres.size();i++) {
            Collections.sort(apps.genres);
            builder.append(apps.genres.get(i)+",");
           // viewHolder.genere.setText(builder.append(apps.genres.get(i)+","));
        }
        builder.deleteCharAt(builder.length()-1);
        viewHolder.genere.setText(builder);

        return  convertView;

    }


    public static class ViewHolder
    {
        ImageView imageViewApp;
        TextView appname;
        TextView artistName;
        TextView releaseDate;
        TextView genere;
    }
}
