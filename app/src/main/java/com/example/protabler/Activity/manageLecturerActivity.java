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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Dto.LecturerDTO;
import com.example.protabler.Dto.ModuleDTO;
import com.example.protabler.JsonList.LecturerList;
import com.example.protabler.JsonList.ModuleList;
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

public class manageLecturerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ListView listView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private ListAdapter listAdapter;
    SharedPreferences sharedPreference;
    String url;
    API api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lecturer);

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
        listView = (ListView) findViewById(R.id.lv_lecturers);
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

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage lecturers");

        try {
            Call<LecturerList> call = api.getAllLecturers();

            call.enqueue(new Callback<LecturerList>() {
                @Override
                public void onResponse(Call<LecturerList> call, Response<LecturerList> response) {
                    LecturerList lecturers = response.body();
                    List<LecturerDTO> lecturersList= lecturers.getList();
                    listAdapter=new ListAdapter(manageLecturerActivity.this,lecturersList);
                    listView.setAdapter(listAdapter);

                }

                @Override
                public void onFailure(Call<LecturerList> call, Throwable t) {
                    Toast.makeText(manageLecturerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e ){
            Toast.makeText(manageLecturerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
        }



        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                Intent intent=new Intent(manageLecturerActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(manageLecturerActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(manageLecturerActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(manageLecturerActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(manageLecturerActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(manageLecturerActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor= sharedPreference.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(manageLecturerActivity.this,MainActivity.class);
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
        private TextView title;
        private Button deleteBtn,editBtn;
        private LetterImageView letterImageView;
        private List<LecturerDTO> lecturerList;

        public ListAdapter(Context context, List<LecturerDTO> lecturerList) {
            this.context = context;
            this.lecturerList=lecturerList;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return lecturerList.size();
        }

        @Override
        public Object getItem(int position) {
            return lecturerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.single_lecturer_item,null);
            }
            title=(TextView) convertView.findViewById(R.id.lecturerName);
            deleteBtn=(Button) convertView.findViewById(R.id.delete_lecturer);
            editBtn=(Button) convertView.findViewById(R.id.edit_lecturer);
            letterImageView=(LetterImageView) convertView.findViewById(R.id.lecturerImg);

            title.setText(lecturerList.get(position).getLecturerName());
            letterImageView.setOval(true);
            letterImageView.setLetter(lecturerList.get(position).getLecturerName().charAt(0));
            deleteBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    try{
                        Call<String> call=api.deleteLecturer(lecturerList.get(position).getLecturerId());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String check=response.body();
                                if(check.equalsIgnoreCase("success")){
                                    Toast.makeText(manageLecturerActivity.this, "Lecturer deleted successfully !", Toast.LENGTH_LONG).show();
                                    lecturerList.remove(position);
                                    notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(manageLecturerActivity.this, "Lecturer deletion unsuccessfully !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(manageLecturerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                            }
                        });
                    }catch (Exception e) {
                        Toast.makeText(manageLecturerActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }

                }
            });

            editBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    LecturerDTO lecturer = (LecturerDTO)listAdapter.getItem(position);
                    Intent intent=new Intent(manageLecturerActivity.this,editLecturerActivity.class);
                    intent.putExtra("lecturerId",lecturer.getLecturerId());
                    intent.putExtra("lecturerName",lecturer.getLecturerName());
                    intent.putExtra("lecturerEmail",lecturer.getLecturerEmail());
                    intent.putExtra("lecturerPhone",lecturer.getLecturerPhone());
                    startActivity(intent);
                    finish();
                }
            });
            return convertView;
        }
    }
}