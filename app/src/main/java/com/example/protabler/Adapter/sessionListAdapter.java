package com.example.protabler.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.protabler.Dto.SessionDTO;
import com.example.protabler.Entities.Module;
import com.example.protabler.Entities.Session;
import com.example.protabler.R;
import com.example.protabler.Utils.LetterImageView;

import java.util.ArrayList;
import java.util.List;

public class sessionListAdapter extends ArrayAdapter{

    private int resource;
    private LayoutInflater layoutInflater;
    private List<SessionDTO> sessions;

    public sessionListAdapter(@NonNull Context context, int resource, @NonNull List<SessionDTO> objects) {
        super(context, resource, objects);
        this.resource=resource;
        this.sessions=objects;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null){
            holder= new ViewHolder();
            convertView=layoutInflater.inflate(resource,null);
            holder.letterImageView=(LetterImageView) convertView.findViewById(R.id.letterViewDayInfo);
            holder.sessionModule=(TextView) convertView.findViewById(R.id.sessionModule);
            holder.sessionLecturer=(TextView) convertView.findViewById(R.id.sessionLecturer);
            holder.sessionClass=(TextView) convertView.findViewById(R.id.sessionClass);
            holder.sessionTime=(TextView) convertView.findViewById(R.id.sessionTime);
            convertView.setTag(holder);
        } else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.letterImageView.setOval(true);
        holder.letterImageView.setLetter(sessions.get(position).getModuleTitle().charAt(0));
        holder.sessionModule.setText(sessions.get(position).getModuleTitle());
        holder.sessionLecturer.setText(sessions.get(position).getLecturerName());
        holder.sessionClass.setText(sessions.get(position).getRoomName());
        holder.sessionTime.setText(sessions.get(position).getLectureTime());
        return convertView;
    }

    class ViewHolder{
        private LetterImageView letterImageView;
        private TextView sessionModule;
        private TextView sessionLecturer;
        private TextView sessionClass;
        private TextView sessionTime;


    }

}
