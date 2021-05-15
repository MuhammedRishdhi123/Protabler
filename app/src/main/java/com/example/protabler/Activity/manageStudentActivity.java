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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Dto.ModuleDTO;
import com.example.protabler.Dto.StudentDTO;
import com.example.protabler.Entities.User;
import com.example.protabler.JsonList.ModuleList;
import com.example.protabler.JsonList.StudentList;
import com.example.protabler.R;
import com.example.protabler.Utils.LetterImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class manageStudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ListView listView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private ListAdapter listAdapter;
    SharedPreferences sharedPreferences;
    String url;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);
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
        listView = (ListView) findViewById(R.id.lv_students);
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
            Call<StudentList> call = api.getAllStudents();

            call.enqueue(new Callback<StudentList>() {
                @Override
                public void onResponse(Call<StudentList> call, Response<StudentList> response) {
                    StudentList students = response.body();
                    List<StudentDTO> studentsList= students.getList();
                    listAdapter=new ListAdapter(manageStudentActivity.this,studentsList);
                    listView.setAdapter(listAdapter);

                }

                @Override
                public void onFailure(Call<StudentList> call, Throwable t) {
                    Toast.makeText(manageStudentActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e ){
            Toast.makeText(manageStudentActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
        }

//        ListAdapter adapter= new ListAdapter(this, DBAccess.users);
//        listView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage students");

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
                Intent intent=new Intent(manageStudentActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(manageStudentActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(manageStudentActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(manageStudentActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(manageStudentActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(manageStudentActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(manageStudentActivity.this,MainActivity.class);
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


    public class ListAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private TextView id,name;
        private Button deleteBtn,editBtn;
        private LetterImageView letterImageView;
        private List<StudentDTO> studentList;

        public ListAdapter(Context context, List<StudentDTO> studentList) {
            this.context = context;
            this.studentList=studentList;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return studentList.size();
        }

        @Override
        public Object getItem(int position) {
            return studentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.single_student_item,null);
            }
            name=(TextView) convertView.findViewById(R.id.tv_student_name);
            deleteBtn=(Button) convertView.findViewById(R.id.delete_student);
            editBtn=(Button) convertView.findViewById(R.id.edit_student);
            letterImageView=(LetterImageView) convertView.findViewById(R.id.studentImg);

            name.setText(studentList.get(position).getStudentName());
            letterImageView.setOval(true);
            letterImageView.setLetter(studentList.get(position).getStudentName().charAt(0));
            deleteBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    try{
                        Call<String> call=api.deleteStudent(studentList.get(position).getStudentId());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String check=response.body();
                                if(check.equalsIgnoreCase("success")){
                                    Toast.makeText(manageStudentActivity.this, "Student deleted successfully !", Toast.LENGTH_LONG).show();
                                    studentList.remove(position);
                                    notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(manageStudentActivity.this, "Student deletion unsuccessfully !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(manageStudentActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                            }
                        });
                    }catch (Exception e) {
                        Toast.makeText(manageStudentActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }

                }
            });

            editBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(manageStudentActivity.this,editStudentActivity.class);
                    intent.putExtra("studentId",studentList.get(position).getStudentId());
                    intent.putExtra("studentName",studentList.get(position).getStudentName());
                    intent.putExtra("studentEmail",studentList.get(position).getStudentEmail());
                    intent.putExtra("studentPhone",studentList.get(position).getStudentPhone());
                    intent.putExtra("batchTitle",studentList.get(position).getBatchTitle());
                    intent.putExtra("courseName",studentList.get(position).getCourseName());
                    startActivity(intent);
                    finish();
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
