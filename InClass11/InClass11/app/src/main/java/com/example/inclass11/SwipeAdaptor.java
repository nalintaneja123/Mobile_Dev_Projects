package com.example.inclass11;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nalin on 4/10/2018.
 */

public class SwipeAdaptor extends PagerAdapter {


    ArrayList<String> urlData=new ArrayList<>();

    //String[] dataUrl=urlData.toArray(new String[urlData.size()]);
    //private int[] images ={R.drawable.ic_add,R.drawable.ic_delete};

    private Context context;
    private LayoutInflater layoutInflater;
    public SwipeAdaptor(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return urlData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return  (view == (RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.imageviewswipe,container,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageViewDisplayImage);
       if(urlData!=null) {
           if (position == 0) {
               Picasso.with(context).load(urlData.get(position)).into(imageView);
               Toast.makeText(context, "This is the first image", Toast.LENGTH_SHORT).show();
           }
           else if(position==urlData.size()-1)
           {

               Picasso.with(context).load(urlData.get(position)).into(imageView);
               Toast.makeText(context, "Last image displayed", Toast.LENGTH_SHORT).show();
           }
           else
           {
               Picasso.with(context).load(urlData.get(position)).into(imageView);

           }



       }
       else
       {
           Toast.makeText(context,"No Images Displayed currently",Toast.LENGTH_SHORT).show();
       }

        container.addView(view);
        return view;
    }


    public void getURLArrayList(ArrayList<String> urldata,Context context)
    {
        this.urlData=urldata;
        this.context=context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
