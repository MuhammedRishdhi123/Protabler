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
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Adapter.moduleListAdapter;
import com.example.protabler.Dto.ModuleDTO;
import com.example.protabler.Entities.Module;
import com.example.protabler.JsonList.ModuleList;
import com.example.protabler.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class moduleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ListView listView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    SharedPreferences sharedPreference;
    API api;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        sharedPreference = getSharedPreferences("session",MODE_PRIVATE);
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

    private void setupView(){
        toolbar=(Toolbar)findViewById(R.id.ToolBarMain);
        listView=(ListView)findViewById(R.id.lv_modules);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.my_modules));

        mtoggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(sharedPreference.getString("role","none").equalsIgnoreCase("Admin")){
            navigationView.getMenu().findItem(R.id.manage_course_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.manage_lecturer_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.manage_module_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.manage_student_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.manage_timetable_menu_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.profile_menu_item).setVisible(true);
        }
        else if(sharedPreference.getString("role","none").equalsIgnoreCase("Student")){
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
        View headerView=navigationView.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.user_profile_name);

        username.setText(sharedPreference.getString("name","Username"));

        if(sharedPreference.getString("role","none").equalsIgnoreCase("Student")) {
            try {
                Call<ModuleList> call = api.getMyModules(sharedPreference.getInt("userId", -1));
                call.enqueue(new Callback<ModuleList>() {
                    @Override
                    public void onResponse(Call<ModuleList> call, Response<ModuleList> response) {
                        ModuleList moduleList = response.body();
                        List<ModuleDTO> moduleLists = moduleList.getModuleList();
                        ArrayAdapter adapter = new moduleListAdapter(moduleActivity.this, R.layout.single_module, moduleLists);
                        listView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<ModuleList> call, Throwable t) {
                        Toast.makeText(moduleActivity.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(moduleActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else{
            try {
                Call<ModuleList> call = api.getLecturerModules(sharedPreference.getInt("userId", -1));
                call.enqueue(new Callback<ModuleList>() {
                    @Override
                    public void onResponse(Call<ModuleList> call, Response<ModuleList> response) {
                        ModuleList moduleList = response.body();
                        List<ModuleDTO> moduleLists = moduleList.getModuleList();
                        ArrayAdapter adapter = new moduleListAdapter(moduleActivity.this, R.layout.single_module, moduleLists);
                        listView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<ModuleList> call, Throwable t) {
                        Toast.makeText(moduleActivity.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(moduleActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

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
                SharedPreferences.Editor editor= sharedPreference.edit();
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
