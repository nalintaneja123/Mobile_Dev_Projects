package com.example.saipavanraju.inclass07;

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

import java.util.List;

/**
 * Created by saipavanraju on 2/26/18.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_news,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.img = convertView.findViewById(R.id.imageView);
            viewHolder.heading = convertView.findViewById(R.id.textViewHeader);
            viewHolder.subHeading = convertView.findViewById(R.id.textViewSub);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
         }
        viewHolder.heading.setText(news.title);
        viewHolder.subHeading.setText(news.publishedAt);
        Picasso.with(getContext()).load(news.urlToImage).into(viewHolder.img);

        return convertView;
    }

    public static class ViewHolder{
        ImageView img;
        TextView heading;
        TextView subHeading;
    }
}
