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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Entities.Batch;
import com.example.protabler.JsonList.BatchList;
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

public class manageTimetableActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ListView listView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private List<Batch> batchList;
    SharedPreferences sharedPreferences;
    String url;
    API api;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_timetable);

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
        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        username.setText(sharedPreferences.getString("name", "Username"));

        try {
            Call<BatchList> call = api.getBatches();

            call.enqueue(new Callback<BatchList>() {
                @Override
                public void onResponse(Call<BatchList> call, Response<BatchList> response) {
                    BatchList batches = response.body();
                    batchList = batches.getList();
                    ListAdapter listAdapter=new ListAdapter(manageTimetableActivity.this,batchList);
                    listView.setAdapter(listAdapter);

                }

                @Override
                public void onFailure(Call<BatchList> call, Throwable t) {
                    Toast.makeText(manageTimetableActivity.this, "Something went wrong ar!", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e ){
            Toast.makeText(manageTimetableActivity.this, "Something went wrong ae!", Toast.LENGTH_LONG).show();
        }


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage timetable");

        try {
            Call<BatchList> call = api.getBatches();

            call.enqueue(new Callback<BatchList>() {
                @Override
                public void onResponse(Call<BatchList> call, Response<BatchList> response) {
                    BatchList batches = response.body();
                    batchList = batches.getList();
                    ListAdapter listAdapter=new ListAdapter(manageTimetableActivity.this,batchList);
                    listView.setAdapter(listAdapter);

                }

                @Override
                public void onFailure(Call<BatchList> call, Throwable t) {
                    Toast.makeText(manageTimetableActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e ){
            Toast.makeText(manageTimetableActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(manageTimetableActivity.this, manageBatchTimetableActivity.class);
                intent.putExtra("batchTitle",batchList.get(position).getBatchTitle());
                startActivity(intent);
            }
        });

        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                Intent intent=new Intent(manageTimetableActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(manageTimetableActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(manageTimetableActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(manageTimetableActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(manageTimetableActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(manageTimetableActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(manageTimetableActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }




    public class ListAdapter extends BaseAdapter{

        private Context context;
        private LayoutInflater layoutInflater;
        private LetterImageView letterImageView;
        private List<Batch> batches;
        private TextView batchCode;

        public ListAdapter(Context context, List<Batch> batches) {
            this.context = context;
            this.batches=batches;
            layoutInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return batches.size();
        }

        @Override
        public Object getItem(int position) {
            return batches.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.single_batch_item,null);
            }
            batchCode=(TextView)convertView.findViewById(R.id.batchCode);
            letterImageView=(LetterImageView)convertView.findViewById(R.id.batchImg);

            batchCode.setText(batches.get(position).getBatchTitle());
            letterImageView.setLetter(batches.get(position).getBatchTitle().charAt(0));
            return convertView;
        }
    }
}
