package com.example.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class wordactivity extends ArrayAdapter<word> {
    private int col;

    public wordactivity(Activity context, ArrayList<word> words,int activitycolors){
        super(context,0,words);
        col=activitycolors;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        word temp=getItem(position);
        View list_item_view=convertView;
        if(list_item_view==null){
            list_item_view= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }
        TextView eng=(TextView)list_item_view.findViewById(R.id.englishid);
        eng.setText(temp.getenglish());
        TextView miw=(TextView)list_item_view.findViewById(R.id.miwokid);
        miw.setText(temp.getmiwok());
        ImageView img=(ImageView)list_item_view.findViewById(R.id.image);
        int found=temp.getimageid();
        if(found!=-1){
            img.setVisibility(View.VISIBLE);
            img.setImageResource(temp.getimageid());
        }
        else{
            img.setVisibility(View.GONE);
        }
        LinearLayout textcontainer=(LinearLayout)list_item_view.findViewById(R.id.colorchange);
        int color= ContextCompat.getColor(getContext(),col);
        textcontainer.setBackgroundColor(color);
        return list_item_view;
    }
}
