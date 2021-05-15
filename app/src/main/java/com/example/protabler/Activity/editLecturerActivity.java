package com.example.protabler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Dto.LecturerDTO;
import com.example.protabler.Dto.StudentDTO;
import com.example.protabler.R;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class editLecturerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private EditText name,phone;
    private TextView email;
    int lecturerId;
    SharedPreferences sharedPreference;
    String url;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lecturer);
        API_BASE_URL base_url=new API_BASE_URL();
        url=base_url.getURL();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(API.class);
        setupView();
    }

    private void setupView() {
        name=findViewById(R.id.et_lecturer_name);
        email=findViewById(R.id.tv_lecturer_email);
        phone=findViewById(R.id.et_lecturer_phone);
        toolbar = (Toolbar) findViewById(R.id.ToolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.manage_course_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.manage_lecturer_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.manage_module_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.manage_student_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.manage_timetable_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.profile_menu_item).setVisible(true);
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.user_profile_name);
        sharedPreference = getSharedPreferences("session", MODE_PRIVATE);
        username.setText(sharedPreference.getString("name", "Username"));

        Intent intent=getIntent();
        lecturerId=intent.getIntExtra("lecturerId",-1);
        name.setText(intent.getStringExtra("lecturerName"));
        email.setText(intent.getStringExtra("lecturerEmail"));
        phone.setText(intent.getStringExtra("lecturerPhone"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit lecturer");

        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.profile_menu_item:{
                Intent intent=new Intent(editLecturerActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(editLecturerActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(editLecturerActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(editLecturerActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(editLecturerActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(editLecturerActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor= sharedPreference.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(editLecturerActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    public void update(View view){
        String lecturerName=name.getText().toString();
        String lecturerPhone=phone.getText().toString();
        String lecturerEmail=email.getText().toString();


        LecturerDTO lecturerDTO=new LecturerDTO();
        lecturerDTO.setLecturerId(lecturerId);
        lecturerDTO.setLecturerName(lecturerName);
        lecturerDTO.setLecturerEmail(lecturerEmail);
        lecturerDTO.setLecturerPhone(lecturerPhone);

        Call<String> call=api.updateLecturer(lecturerDTO);

        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String check = response.body();
                    if (check.equalsIgnoreCase("success")) {
                        Toast.makeText(editLecturerActivity.this, "Lecturer updated successfully !", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(editLecturerActivity.this,manageLecturerActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(editLecturerActivity.this, "Lecturer update unsuccessfully !", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(editLecturerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e ){
            Toast.makeText(editLecturerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}