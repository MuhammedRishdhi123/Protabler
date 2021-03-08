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

public class moduleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ListView listView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        setupView();



    }

    private void setupView(){
        toolbar=(Toolbar)findViewById(R.id.ToolBarMain);
        listView=(ListView)findViewById(R.id.lv_modules);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My modules");

        mtoggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.user_profile_name);
        session= getSharedPreferences("session",MODE_PRIVATE);
        username.setText(session.getString("user_name","Username"));

        ArrayAdapter adapter=new moduleListAdapter(this,R.layout.day_info_single_item, DBAccess.modules);
        listView.setAdapter(adapter);

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
                Intent intent=new Intent(moduleActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.timetable_menu_item:{
                Intent intent=new Intent(moduleActivity.this,timetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.module_menu_item:{
                Intent intent=new Intent(moduleActivity.this,moduleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.faculty_menu_item:{
                Intent intent=new Intent(moduleActivity.this,facultyActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.resource_menu_item:{
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(moduleActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor=session.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(moduleActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
