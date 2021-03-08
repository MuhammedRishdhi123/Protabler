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
import android.widget.ListView;
import android.widget.TextView;

import com.example.protabler.Model.UserRole;
import com.example.protabler.R;
import com.example.protabler.Utils.DBAccess;
import com.example.protabler.Utils.SessionManagement;
import com.google.android.material.navigation.NavigationView;

public class profileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private TextView user_name,user_email,user_curriculum,user_phone;
    SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupView();
        loadUserProfile();
    }

    private void loadUserProfile() {
        session=getSharedPreferences("session",MODE_PRIVATE);
        user_name.setText(session.getString("user_name","Username"));
        user_email.setText(session.getString("user_email","Email"));
        user_curriculum.setText(session.getString("user_curriculum","Curriculum"));
        user_phone.setText(session.getString("user_phone","Phone"));

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
        SessionManagement sessionManagement=new SessionManagement(profileActivity.this);
        int user_id=sessionManagement.getSession();
        if(user_id != -1){
            for(int i = 0; i< DBAccess.userRoles.size(); i++){
                if(user_id==DBAccess.userRoles.get(i).getUserId()){
                    UserRole userRole=DBAccess.userRoles.get(i);
                    if(userRole.getRoleId()== 1){
                       navigationView.getMenu().clear();
                       navigationView.inflateMenu(R.menu.drawermenuadmin);
                    }
                    else if(userRole.getRoleId()== 2){
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.drawermenu);
                    }
                }
            }

        }
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.user_profile_name);
        session = getSharedPreferences("session", MODE_PRIVATE);
        username.setText(session.getString("user_name", "Username"));

        user_name=findViewById(R.id.user_name);
        user_email=findViewById(R.id.user_email);
        user_curriculum=findViewById(R.id.user_curriculum);
        user_phone=findViewById(R.id.user_phone);

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
            case R.id.manage_faculty_menu_item:{
                Intent intent=new Intent(profileActivity.this,manageFacultyActivity.class);
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
                SharedPreferences.Editor editor=session.edit();
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
