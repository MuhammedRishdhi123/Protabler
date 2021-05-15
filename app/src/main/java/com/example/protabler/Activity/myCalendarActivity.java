package com.example.protabler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.protabler.Entities.Reminder;
import com.example.protabler.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class myCalendarActivity extends AppCompatActivity {

    private EditText reminderText;
    private CalendarView calendarView;
    private String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        reminderText=findViewById(R.id.et_reminder);
        calendarView=findViewById(R.id.myCalendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                GregorianCalendar date=new GregorianCalendar(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
                selectedDate=simpleDateFormat.format(date.getTime());
                 getReminder(view);

            }
        });
    }

    public void setReminder(View view){
        Date selectedDate1=new Date();
        try {
            selectedDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(selectedDate);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void getReminder(View view){
        Date selectedDate1=new Date();
        Boolean found=false;
        try {
            selectedDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(selectedDate);
        }catch (Exception e){
            e.printStackTrace();
        }
//        for(Reminder r: DBAccess.reminders){
//            if (selectedDate1.compareTo(r.getDate()) == 0){
//                reminderText.setText(r.getName());
//                found=true;
//            }
//        }
        if(!found){
            reminderText.setText("");
        }
    }
}
