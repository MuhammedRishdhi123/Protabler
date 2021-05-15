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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class settingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private TextView user_name,user_phone,user_password;
    SharedPreferences sharedPreferences;
    API api;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPreferences=getSharedPreferences("session",MODE_PRIVATE);
        API_BASE_URL base_url=new API_BASE_URL();
        url=base_url.getURL();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(API.class);
        setupView();
        loadUserProfile();
    }


    private void loadUserProfile() {

        if(sharedPreferences.getString("role", "none").equalsIgnoreCase("Student")) {
            try {
                Call<StudentList> call = api.getStudent(sharedPreferences.getInt("userId", -1));
                call.enqueue(new Callback<StudentList>() {
                    @Override
                    public void onResponse(Call<StudentList> call, Response<StudentList> response) {
                        StudentList studentList = response.body();
                        List<StudentDTO> studentLists= studentList.getList();
                        if (studentLists.size() != 0) {
                            user_name.setText(studentLists.get(0).getStudentName());
                            user_phone.setText(studentLists.get(0).getStudentPhone());
                        }

                    }

                    @Override
                    public void onFailure(Call<StudentList> call, Throwable t) {
                        Toast.makeText(settingActivity.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(settingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{

            try {
                Call<UserList> call = api.getUser(sharedPreferences.getInt("userId", -1));
                call.enqueue(new Callback<UserList>() {
                    @Override
                    public void onResponse(Call<UserList> call, Response<UserList> response) {
                        UserList users = response.body();
                        User user=users.getList().get(0);
                        if (user != null) {
                            user_name.setText(user.getName());
                            user_phone.setText(user.getPhone());
                        }

                    }

                    @Override
                    public void onFailure(Call<UserList> call, Throwable t) {
                        Toast.makeText(settingActivity.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(settingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }


    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.ToolBarMain);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");

        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (sharedPreferences.getString("role", "none").equalsIgnoreCase("Admin")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawermenu);
        } else if (sharedPreferences.getString("role","none").equalsIgnoreCase("Student")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawermenu);
        }
        else if (sharedPreferences.getString("role","none").equalsIgnoreCase("Lecturer")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawermenu);
        }

        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.user_profile_name);
        username.setText(sharedPreferences.getString("name", "Username"));
        user_name = findViewById(R.id.user_name_setting);
        user_phone = findViewById(R.id.user_phone_setting);
        user_password = findViewById(R.id.user_password_setting);
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
                Intent intent=new Intent(settingActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.timetable_menu_item:{
                Intent intent=new Intent(settingActivity.this,timetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.module_menu_item:{
                Intent intent=new Intent(settingActivity.this,moduleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.faculty_menu_item:{
                Intent intent=new Intent(settingActivity.this,facultyActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.resource_menu_item:{
                Intent intent=new Intent(settingActivity.this,resourcesActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(settingActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(settingActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(settingActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(settingActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(settingActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(settingActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void update(View view){
        User currentUser=new User();

        if(currentUser != null){
            String username=user_name.getText().toString().trim();
            String password=user_password.getText().toString();
            String phoneNumber=user_phone.getText().toString().trim();
            Boolean passwordChanged=false;

            //Checking the input details

            if(username.isEmpty()){
                user_name.setError("Name is required");
                user_name.requestFocus();
                return;
            }

            if(!password.isEmpty()){
              passwordChanged=true;
            }

            if(phoneNumber.isEmpty()){
                user_phone.setError("Phone is required");
                user_phone.requestFocus();
                return;
            }

            currentUser.setUserId(sharedPreferences.getInt("userId",-1));
            currentUser.setName(username);
            currentUser.setPhone(phoneNumber);


            //If the password field is changed the user password is changed
            if(passwordChanged){
                currentUser.setPassword(password);
            }

            Call<String> call=api.updateUser(currentUser);
            try {
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String check = response.body();
                        if (check.equalsIgnoreCase("updated")) {
                            Toast.makeText(settingActivity.this, "User account updated successfully !", Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(settingActivity.this, "Something went wrong please try again !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(settingActivity.this, "Something went wrong Please try again!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(settingActivity.this, "Something went wrong Please try again!", Toast.LENGTH_LONG).show();
            }

        }

    }



}
