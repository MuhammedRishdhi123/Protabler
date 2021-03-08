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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.protabler.Adapter.moduleListAdapter;
import com.example.protabler.R;
import com.example.protabler.Utils.DBAccess;
import com.google.android.material.navigation.NavigationView;

public class editDayScheduleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView day;
    private Toolbar toolbar;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_day_schedule);
        setupView();
    }


    private void setupView()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar=(Toolbar)findViewById(R.id.ToolBar);
        listView=(ListView)findViewById(R.id.lv_day_schedule);
        day = findViewById(R.id.tv_day);
        Intent intent=getIntent();
        int clickedDay = intent.getIntExtra("Day",-1);
        switch (clickedDay)
        {
            case 0:
            {
                day.setText("Monday");
                ArrayAdapter adapter=new moduleListAdapter(editDayScheduleActivity.this,R.layout.edit_day_info_single_item, DBAccess.modules);
                listView.setAdapter(adapter);
                break;
            }
            case 1:
            {
                day.setText("Tuesday");
                ArrayAdapter adapter=new moduleListAdapter(editDayScheduleActivity.this,R.layout.edit_day_info_single_item, DBAccess.modules);
                listView.setAdapter(adapter);
                break;
            }
            case 2:
            {
                day.setText("Wednesday");
                ArrayAdapter adapter=new moduleListAdapter(editDayScheduleActivity.this,R.layout.edit_day_info_single_item, DBAccess.modules);
                listView.setAdapter(adapter);
                break;
            }
            case 3:
            {
                day.setText("Thursday");
                ArrayAdapter adapter=new moduleListAdapter(editDayScheduleActivity.this,R.layout.edit_day_info_single_item, DBAccess.modules);
                listView.setAdapter(adapter);
                break;
            }
            case 4:
            {
                day.setText("Friday");
                ArrayAdapter adapter=new moduleListAdapter(editDayScheduleActivity.this,R.layout.edit_day_info_single_item, DBAccess.modules);
                listView.setAdapter(adapter);
                break;
            }
            case 5:
            {
                day.setText("Saturday");
                ArrayAdapter adapter=new moduleListAdapter(editDayScheduleActivity.this,R.layout.edit_day_info_single_item, DBAccess.modules);
                listView.setAdapter(adapter);
                break;
            }
            default:
                break;
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Schedule");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.user_profile_name);
        session = getSharedPreferences("session", MODE_PRIVATE);
        username.setText(session.getString("user_name", "Username"));

        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.profile_menu_item:{
                Intent intent=new Intent(editDayScheduleActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(editDayScheduleActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(editDayScheduleActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_faculty_menu_item:{
                Intent intent=new Intent(editDayScheduleActivity.this,manageFacultyActivity.class);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(editDayScheduleActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(editDayScheduleActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor=session.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(editDayScheduleActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
