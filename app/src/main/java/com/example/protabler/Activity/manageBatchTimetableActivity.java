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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Dto.SessionDTO;
import com.example.protabler.Entities.Batch;
import com.example.protabler.Entities.Session;
import com.example.protabler.JsonList.BatchList;
import com.example.protabler.JsonList.SessionList;
import com.example.protabler.R;
import com.example.protabler.Utils.LetterImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class manageBatchTimetableActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ListView listView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private ActionBarDrawerToggle mtoggle;
    SharedPreferences sharedPreferences;
    String url;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_batch_timetable);
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
    }



    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.ToolBar);
        listView = (ListView) findViewById(R.id.lv_timetables);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab=(FloatingActionButton)findViewById(R.id.fab_timetable);
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
        username.setText(sharedPreferences.getString("name", "Username"));




        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage timetable");

        try {
            Call<SessionList> call = api.getBatchSessions(getIntent().getStringExtra("batchTitle"));

            call.enqueue(new Callback<SessionList>() {
                @Override
                public void onResponse(Call<SessionList> call, Response<SessionList> response) {
                    SessionList sessions = response.body();
                    List<SessionDTO> sessionList= sessions.getSessionList();
                    ListAdapter listAdapter=new ListAdapter(manageBatchTimetableActivity.this,sessionList);
                    listView.setAdapter(listAdapter);

                }

                @Override
                public void onFailure(Call<SessionList> call, Throwable t) {
                    Toast.makeText(manageBatchTimetableActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e ){
            Toast.makeText(manageBatchTimetableActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
        }



        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(manageBatchTimetableActivity.this,addNewSessionActivity.class);
                intent.putExtra("batchTitle",getIntent().getStringExtra("batchTitle"));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setupView();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.profile_menu_item:{
                Intent intent=new Intent(manageBatchTimetableActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(manageBatchTimetableActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(manageBatchTimetableActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(manageBatchTimetableActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(manageBatchTimetableActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(manageBatchTimetableActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(manageBatchTimetableActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    public class ListAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private TextView sessionModule;
        private TextView sessionLecturer;
        private TextView sessionBatch;
        private TextView sessionTime;
        private TextView sessionClass;
        private Button deleteBtn;
        private LetterImageView letterImageView;
        private List<SessionDTO> sessionsList;

        public ListAdapter(Context context, List<SessionDTO> sessionsList) {
            this.context = context;
            this.sessionsList = sessionsList;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return sessionsList.size();
        }

        @Override
        public Object getItem(int position) {
            return sessionsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.single_timetable_item, null);
            }
            sessionModule = (TextView) convertView.findViewById(R.id.sessionModule);
            sessionBatch = (TextView) convertView.findViewById(R.id.sessionBatch);
            sessionClass = (TextView) convertView.findViewById(R.id.sessionClass);
            sessionTime = (TextView) convertView.findViewById(R.id.sessionTime);
            sessionLecturer = (TextView) convertView.findViewById(R.id.sessionLecturer);
            deleteBtn = (Button) convertView.findViewById(R.id.delete_session);
            letterImageView = (LetterImageView) convertView.findViewById(R.id.timetableImg);

            sessionModule.setText(sessionsList.get(position).getModuleTitle());
            sessionLecturer.setText(sessionsList.get(position).getLecturerName());
            sessionBatch.setText(sessionsList.get(position).getBatchTitle());
            sessionClass.setText(sessionsList.get(position).getRoomName());
            sessionTime.setText(sessionsList.get(position).getLectureTime());
            letterImageView.setOval(true);
            letterImageView.setLetter(sessionsList.get(position).getModuleTitle().charAt(0));
            deleteBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {



                    try{
                        Call<String> call=api.deleteSession(sessionsList.get(position).getSessionId());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String check=response.body();
                                if(check.equalsIgnoreCase("success")){
                                    Toast.makeText(manageBatchTimetableActivity.this, "Session deleted successfully !", Toast.LENGTH_LONG).show();
                                    sessionsList.remove(position);
                                    notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(manageBatchTimetableActivity.this, "Session deletion unsuccessfully !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(manageBatchTimetableActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                            }
                        });
                    }catch (Exception e) {
                          Toast.makeText(manageBatchTimetableActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }


                }
            });


            return convertView;
        }
    }

}