package com.example.protabler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Dto.StudentDTO;
import com.example.protabler.Entities.User;
import com.example.protabler.JsonList.StudentList;
import com.example.protabler.JsonList.UserList;
import com.example.protabler.R;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class profileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private TextView user_name,user_email,user_phone,user_batch,user_course;
    private String url;
    private int userId;
    API api;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences=getSharedPreferences("session", Context.MODE_PRIVATE);
        setupView();
        API_BASE_URL base_url=new API_BASE_URL();
        url=base_url.getURL();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(API.class);
        userId=sharedPreferences.getInt("userId",-1);

        loadUserProfile();

    }

    private void loadUserProfile() {
        if(sharedPreferences.getString("role","none").equalsIgnoreCase("Student")) {
            try {
                Call<StudentList> call = api.getStudent(userId);

                call.enqueue(new Callback<StudentList>() {
                    @Override
                    public void onResponse(Call<StudentList> call, Response<StudentList> response) {
                        StudentList student = response.body();
                        StudentDTO studentDTO = student.getList().get(0);
                        if (studentDTO != null) {
                            user_name.setText(studentDTO.getStudentName());
                            user_email.setText(studentDTO.getStudentEmail());
                            user_phone.setText(studentDTO.getStudentPhone());
                            user_batch.setVisibility(View.VISIBLE);
                            user_batch.setText(studentDTO.getBatchTitle());
                            user_course.setVisibility(View.VISIBLE);
                            user_course.setText(studentDTO.getCourseName());

                        }

                    }

                    @Override
                    public void onFailure(Call<StudentList> call, Throwable t) {
                        Toast.makeText(profileActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(profileActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
            }
        }else{
            try {
                Call<UserList> call = api.getUser(userId);

                call.enqueue(new Callback<UserList>() {
                    @Override
                    public void onResponse(Call<UserList> call, Response<UserList> response) {
                        UserList users = response.body();
                        User user=users.getList().get(0);

                        if (user != null) {
                            user_name.setText(user.getName());
                            user_email.setText(user.getEmail());
                            user_phone.setText(user.getPhone());

                        }

                    }

                    @Override
                    public void onFailure(Call<UserList> call, Throwable t) {
                        Toast.makeText(profileActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(profileActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
            }

        }


    }


    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.ToolBarMain);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(sharedPreferences.getString("role","none").equalsIgnoreCase("Admin")){
            navigationView.getMenu().findItem(R.id.manage_course_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.manage_lecturer_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.manage_module_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.manage_student_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.manage_timetable_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.profile_menu_item).setVisible(true);
        }
        else if(sharedPreferences.getString("role","none").equalsIgnoreCase("Student")){
            navigationView.getMenu().findItem(R.id.module_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.faculty_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.resource_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.setting_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.timetable_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.profile_menu_item).setVisible(true);
        }
        else{
            navigationView.getMenu().findItem(R.id.module_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.resource_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.setting_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.timetable_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.profile_menu_item).setVisible(true);
        }

        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.user_profile_name);
        username.setText(sharedPreferences.getString("name", "Username"));

        user_name=findViewById(R.id.user_name);
        user_email=findViewById(R.id.user_email);
        user_phone=findViewById(R.id.user_phone);
        user_batch=findViewById(R.id.user_batch);
        user_course=findViewById(R.id.user_course);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.profile_menu_item:{
                Intent intent=new Intent(profileActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.timetable_menu_item:{
                Intent intent=new Intent(profileActivity.this,timetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.module_menu_item:{
                Intent intent=new Intent(profileActivity.this,moduleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.faculty_menu_item:{
                Intent intent=new Intent(profileActivity.this,facultyActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.resource_menu_item:{
                Intent intent=new Intent(profileActivity.this,resourcesActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(profileActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(profileActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(profileActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(profileActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(profileActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(profileActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}
