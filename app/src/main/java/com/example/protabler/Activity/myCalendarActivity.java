package com.example.protabler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.protabler.Model.Reminder;
import com.example.protabler.R;
import com.example.protabler.Utils.DBAccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        for(Reminder r: DBAccess.reminders){
            if (selectedDate1.compareTo(r.getDate()) == 0){
                r.setName(reminderText.getText().toString());
                reminderText.setText(r.getName());
                Toast.makeText(myCalendarActivity.this,"Calendar updated successfully !",Toast.LENGTH_LONG).show();
            }
            else{
                Reminder reminder=new Reminder(DBAccess.reminders.size()+1,reminderText.getText().toString(),selectedDate1);
                DBAccess.reminders.add(reminder);
                reminderText.setText(reminderText.getText());
                Toast.makeText(myCalendarActivity.this,"Calendar updated successfully !",Toast.LENGTH_LONG).show();
            }
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
        for(Reminder r: DBAccess.reminders){
            if (selectedDate1.compareTo(r.getDate()) == 0){
                reminderText.setText(r.getName());
                found=true;
            }
        }
        if(!found){
            reminderText.setText("");
        }
    }
}
