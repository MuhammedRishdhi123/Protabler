package com.example.protabler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Dto.StudentDTO;
import com.example.protabler.Entities.Batch;
import com.example.protabler.Entities.Course;

import com.example.protabler.JsonList.BatchList;
import com.example.protabler.JsonList.CourseList;
import com.example.protabler.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class registerActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText u_name,u_email,u_password,u_phoneNumber;
    private Spinner u_curriculum;
    private Spinner u_batchCode;
    private TextView loginLink;
    private String url;
    private API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        u_name=findViewById(R.id.name);
        u_email=findViewById(R.id.email);
        u_curriculum=(Spinner) findViewById(R.id.course);
        u_batchCode=(Spinner)findViewById(R.id.batchCode);
        u_password=findViewById(R.id.password);
        u_phoneNumber=findViewById(R.id.phone);
        loginLink=(TextView)findViewById(R.id.loginLink);
        loginLink.setOnClickListener(this);
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

    private void loadData() {
        try {
            Call<BatchList> call = api.getBatches();

            call.enqueue(new Callback<BatchList>() {
                @Override
                public void onResponse(Call<BatchList> call, Response<BatchList> response) {
                    BatchList batches = response.body();
                    List<Batch> batchList=batches.getList();
                    List<String> batchCodes=new ArrayList<String>();

                    if (batchList.size() != 0) {
                        batchCodes.add("Select a batch");
                    for(Batch batch:batchList){
                        batchCodes.add(batch.getBatchTitle());
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(registerActivity.this, R.layout.spinner_item2, batchCodes);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    u_batchCode.setAdapter(dataAdapter);


                    }

                }

                @Override
                public void onFailure(Call<BatchList> call, Throwable t) {
                    Toast.makeText(registerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });

            Call<CourseList> call1 = api.getCourses();

            call1.enqueue(new Callback<CourseList>() {
                @Override
                public void onResponse(Call<CourseList> call, Response<CourseList> response) {
                    CourseList courses = response.body();
                    List<Course> courseList=courses.getList();
                    List<String> courseTitles=new ArrayList<String>();

                    if (courseList.size() != 0) {
                        courseTitles.add("Select a course");
                        for(Course course:courseList){
                            courseTitles.add(course.getCourseTitle());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(registerActivity.this, R.layout.spinner_item, courseTitles);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        u_curriculum.setAdapter(dataAdapter);


                    }

                }

                @Override
                public void onFailure(Call<CourseList> call, Throwable t) {
                    Toast.makeText(registerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(registerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
        }
    }

    public void register(View view){
        String name=u_name.getText().toString().trim();
        String email=u_email.getText().toString().trim();
        String password=u_password.getText().toString();
        String curriculum=u_curriculum.getSelectedItem().toString();
        String batchCode=u_batchCode.getSelectedItem().toString();
        String phoneNumber=u_phoneNumber.getText().toString().trim();

        if(name.isEmpty()){
            u_name.setError("Name is required");
            u_name.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()){
            u_email.setError("Enter a valid email");
            u_email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            u_password.setError("Password is required");
            u_password.requestFocus();
            return;
        }

        if(u_curriculum.getSelectedItemPosition()==0){
            u_curriculum.requestFocus();
            return;
        }

        if(u_batchCode.getSelectedItemPosition()==0){
            u_batchCode.requestFocus();
            return;
        }

        if(phoneNumber.isEmpty()){
            u_phoneNumber.setError("Phone is required");
            u_phoneNumber.requestFocus();
            return;
        }


        StudentDTO studentDTO=new StudentDTO();
        studentDTO.setStudentName(name);
        studentDTO.setStudentEmail(email);
        studentDTO.setBatchTitle(batchCode);
        studentDTO.setCourseName(curriculum);
        studentDTO.setStudentPhone(phoneNumber);
        studentDTO.setPassword(password);

        Call<String> call=api.saveStudent(studentDTO);
        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String check = response.body();
                    if (check.equalsIgnoreCase("success")) {
                        Toast.makeText(registerActivity.this, "User account created successfully !", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(registerActivity.this, MainActivity.class);
                        startActivity(i);
                    } else if(check.equalsIgnoreCase("fail")) {
                        Toast.makeText(registerActivity.this, "Email already exists !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(registerActivity.this, "Something went wrong please try again !", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(registerActivity.this, "Something went wrong Please try again!", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(registerActivity.this, "Something went wrong Please try again!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginLink){
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
    }
}
