package com.example.protabler.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.TypedArrayUtils;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Dto.LecturerDTO;
import com.example.protabler.Dto.ModuleDTO;
import com.example.protabler.Dto.RoomDTO;
import com.example.protabler.Dto.SessionDTO;
import com.example.protabler.Entities.Batch;
import com.example.protabler.Entities.Course;
import com.example.protabler.Entities.Module;
import com.example.protabler.Enums.Day;
import com.example.protabler.Enums.LectureTime;
import com.example.protabler.Enums.LectureType;
import com.example.protabler.JsonList.BatchList;
import com.example.protabler.JsonList.CourseList;
import com.example.protabler.JsonList.LecturerList;
import com.example.protabler.JsonList.ModuleList;
import com.example.protabler.JsonList.RoomList;
import com.example.protabler.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class addNewSessionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner module;
    private Spinner batch;
    private Spinner time;
    private Spinner day;
    private Spinner lectureType;
    private Spinner lecturer;
    private Spinner room;
    private String url;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_session);

        toolbar=(Toolbar)findViewById(R.id.ToolBar);
        module=(Spinner)findViewById(R.id.moduleTitle);
        batch=(Spinner)findViewById(R.id.batchCode);
        time=(Spinner)findViewById(R.id.time);
        day=(Spinner)findViewById(R.id.day);
        lectureType=(Spinner)findViewById(R.id.lectureType);
        lecturer=(Spinner)findViewById(R.id.lecturer);
        room=(Spinner)findViewById(R.id.room);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add new session");
        API_BASE_URL base_url=new API_BASE_URL();
        url=base_url.getURL();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(API.class);
        loadData();



    }


    public void addNewSession(View view){
        String moduleTitle=module.getSelectedItem().toString();
        String lectureTime=time.getSelectedItem().toString();
        String lectureDay=day.getSelectedItem().toString();
        String type=lectureType.getSelectedItem().toString();
        String lecturerName=lecturer.getSelectedItem().toString();
        String lectureRoom=room.getSelectedItem().toString();

        if(module.getSelectedItemPosition()==0){
            module.requestFocus();
            return;
        }

        if (time.getSelectedItemPosition() == 0) {
            time.requestFocus();
            return;
        }

        if (day.getSelectedItemPosition() == 0) {
            day.requestFocus();
            return;
        }

        if (lectureType.getSelectedItemPosition() == 0) {
            lectureType.requestFocus();
            return;
        }

        if (lecturer.getSelectedItemPosition() == 0) {
            lecturer.requestFocus();
            return;
        }

        if (room.getSelectedItemPosition() == 0) {
            room.requestFocus();
            return;
        }

        SessionDTO sessionDTO=new SessionDTO();
        sessionDTO.setModuleTitle(moduleTitle);
        sessionDTO.setBatchTitle(getIntent().getStringExtra("batchTitle"));
        sessionDTO.setDay(lectureDay);
        sessionDTO.setLecturerName(lecturerName);
        sessionDTO.setLectureTime(lectureTime);
        sessionDTO.setLectureType(type);
        sessionDTO.setRoomName(lectureRoom);

        Call<String> call=api.saveSession(sessionDTO);

        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String check = response.body();
                    if (check.equalsIgnoreCase("success")) {
                        Toast.makeText(addNewSessionActivity.this, "Session added successfully !", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(addNewSessionActivity.this, manageBatchTimetableActivity.class);
                        i.putExtra("batchTitle",getIntent().getStringExtra("batchTitle"));
                        startActivity(i);
                        finish();
                    } else if(check.equalsIgnoreCase("failRoom")) {
                        Toast.makeText(addNewSessionActivity.this, "Room is already available !", Toast.LENGTH_LONG).show();
                    }
                    else if(check.equalsIgnoreCase("failLecturer")) {
                        Toast.makeText(addNewSessionActivity.this, "Lecturer is not available !", Toast.LENGTH_LONG).show();
                    }
                    else if(check.equalsIgnoreCase("failSession")) {
                        Toast.makeText(addNewSessionActivity.this, "Session is not available !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(addNewSessionActivity.this, "Something went wrong please try again !", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(addNewSessionActivity.this, "Something went wrong Please try again!", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(addNewSessionActivity.this, "Something went wrong Please try again!", Toast.LENGTH_LONG).show();
        }


    }



    private void loadData() {
        try {


            Call<ModuleList> call1 = api.getAllModules();

            call1.enqueue(new Callback<ModuleList>() {
                @Override
                public void onResponse(Call<ModuleList> call, Response<ModuleList> response) {
                    ModuleList modules = response.body();
                    List<ModuleDTO> moduleList=modules.getModuleList();
                    List<String> moduleTitles=new ArrayList<String>();

                    if (moduleList.size() != 0) {
                        moduleTitles.add("Select a module");
                        for(ModuleDTO module:moduleList){
                            moduleTitles.add(module.getModuleTitle());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(addNewSessionActivity.this, android.R.layout.simple_spinner_dropdown_item, moduleTitles);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        module.setAdapter(dataAdapter);


                    }

                }

                @Override
                public void onFailure(Call<ModuleList> call, Throwable t) {
                    Toast.makeText(addNewSessionActivity.this, "Something went wrong !"+t.getCause(), Toast.LENGTH_LONG).show();
                }
            });


            Call<RoomList> call2 = api.getAllRooms();

            call2.enqueue(new Callback<RoomList>() {
                @Override
                public void onResponse(Call<RoomList> call2, Response<RoomList> response) {
                    RoomList rooms = response.body();
                    List<RoomDTO> roomList=rooms.getList();
                    List<String> roomNames=new ArrayList<String>();

                    if (roomList.size() != 0) {
                        roomNames.add("Select a room");
                        for(RoomDTO room:roomList){
                            roomNames.add(room.getRoomName());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(addNewSessionActivity.this, android.R.layout.simple_spinner_dropdown_item,roomNames);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        room.setAdapter(dataAdapter);


                    }

                }

                @Override
                public void onFailure(Call<RoomList> call2, Throwable t) {
                    Toast.makeText(addNewSessionActivity.this, "Something went wrong !"+t.getCause(), Toast.LENGTH_LONG).show();
                }
            });



            Call<LecturerList> call3 = api.getAllLecturers();

            call3.enqueue(new Callback<LecturerList>() {
                @Override
                public void onResponse(Call<LecturerList> call3, Response<LecturerList> response) {
                    LecturerList lecturers = response.body();
                    List<LecturerDTO> lecturerList=lecturers.getList();
                    List<String> lecturerNames=new ArrayList<String>();

                    if (lecturerList.size() != 0) {
                        lecturerNames.add("Select a lecturer");
                        for(LecturerDTO lecturer:lecturerList){
                            lecturerNames.add(lecturer.getLecturerName());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(addNewSessionActivity.this, android.R.layout.simple_spinner_dropdown_item,lecturerNames);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lecturer.setAdapter(dataAdapter);


                    }

                }

                @Override
                public void onFailure(Call<LecturerList> call3, Throwable t) {
                    Toast.makeText(addNewSessionActivity.this, "Something went wrong !"+t.getCause(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(addNewSessionActivity.this, "Something went wrong !"+e.getCause(), Toast.LENGTH_LONG).show();
        }
        List<String> lectureTimeNames=new ArrayList<String>();
        lectureTimeNames.add("Select a time");
        for(LectureTime l:LectureTime.values()){
            lectureTimeNames.add(l.getLectureTimeName());
        }

        ArrayAdapter<String> dataAdapter =new ArrayAdapter<String>(addNewSessionActivity.this, android.R.layout.simple_spinner_dropdown_item,lectureTimeNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(dataAdapter);

        List<String> lectureTypeNames=new ArrayList<String>();
        lectureTypeNames.add("Select lecture type");
        for(LectureType l:LectureType.values()){
            lectureTypeNames.add(l.getLectureTypeName());
        }

        ArrayAdapter<String> dataAdapter1 =new ArrayAdapter<String>(addNewSessionActivity.this, android.R.layout.simple_spinner_dropdown_item,lectureTypeNames);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lectureType.setAdapter(dataAdapter1);


        List<String> dayNames=new ArrayList<String>();
        dayNames.add("Select lecture day");
        for(Day d: Day.values()){
            dayNames.add(d.getDayName());
        }

        ArrayAdapter<String> dataAdapter2 =new ArrayAdapter<String>(addNewSessionActivity.this, android.R.layout.simple_spinner_dropdown_item,dayNames);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(dataAdapter2);
    }
}