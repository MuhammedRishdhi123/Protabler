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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.Model.Timetable;
import com.example.protabler.R;
import com.example.protabler.Utils.DBAccess;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class editTimetableActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private GridView gridView;
    private TextView batchCode,curriculum;
    private String [] days;
    SharedPreferences session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timetable);
        setupView();
    }

    private void setupView()
    {
        toolbar = (Toolbar) findViewById(R.id.ToolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        batchCode = (TextView)findViewById(R.id.tv_batch_code);
        curriculum = (TextView)findViewById(R.id.tv_curriculum);
        gridView = (GridView)findViewById(R.id.gv_days);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.user_profile_name);
        session = getSharedPreferences("session", MODE_PRIVATE);
        username.setText(session.getString("user_name", "Username"));
        days = getResources().getStringArray(R.array.days);
        ArrayAdapter adapter = new ArrayAdapter<String>(editTimetableActivity.this,android.R.layout.simple_expandable_list_item_1,days);
        gridView.setAdapter(adapter);

        Intent intent=getIntent();
        int timetableId=intent.getIntExtra("timetableId",-1);
        batchCode.setText(DBAccess.timetableList.get(timetableId-1).getBatchCode());
        curriculum.setText(DBAccess.timetableList.get(timetableId-1).getCourseTitle());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(editTimetableActivity.this,editDayScheduleActivity.class);
                intent.putExtra("Day",position);
                startActivity(intent);

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit timetable");

        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.profile_menu_item:{
                Intent intent=new Intent(editTimetableActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(editTimetableActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(editTimetableActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_faculty_menu_item:{
                Intent intent=new Intent(editTimetableActivity.this,manageFacultyActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(editTimetableActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(editTimetableActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor=session.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(editTimetableActivity.this,MainActivity.class);
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
