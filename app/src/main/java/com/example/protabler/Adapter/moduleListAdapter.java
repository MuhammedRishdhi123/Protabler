package com.example.protabler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.protabler.Model.Module;
import com.example.protabler.R;
import com.example.protabler.Utils.LetterImageView;

import java.util.ArrayList;
import java.util.List;

public class moduleListAdapter extends ArrayAdapter{

    private int resource;
    private LayoutInflater layoutInflater;
     private ArrayList<Module> modules;

    public moduleListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Module> objects) {
        super(context, resource, objects);
        this.resource=resource;
        this.modules=objects;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null){
            holder= new ViewHolder();
            convertView=layoutInflater.inflate(resource,null);
            holder.letterImageView=(LetterImageView) convertView.findViewById(R.id.letterViewDayInfo);
            holder.moduleTitle=(TextView) convertView.findViewById(R.id.dayInfoItem);
            holder.time=(TextView) convertView.findViewById(R.id.dayInfoItem_time);
            convertView.setTag(holder);
        } else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.letterImageView.setOval(true);
        holder.letterImageView.setLetter(modules.get(position).getModuleTitle().charAt(0));
        holder.moduleTitle.setText(modules.get(position).getModuleTitle());
        holder.time.setText("12:00PM - 1:00PM");
        return convertView;
    }

    class ViewHolder{
        private LetterImageView letterImageView;
        private TextView moduleTitle;
        private TextView time;


    }

}
