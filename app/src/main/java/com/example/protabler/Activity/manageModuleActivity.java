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
import com.example.protabler.Dto.ModuleDTO;
import com.example.protabler.Entities.Module;
import com.example.protabler.JsonList.BatchList;
import com.example.protabler.JsonList.ModuleList;
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

public class manageModuleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
        setContentView(R.layout.activity_manage_module);
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
        listView = (ListView) findViewById(R.id.lv_modules);
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
        getSupportActionBar().setTitle("Manage modules");

        try {
            Call<ModuleList> call = api.getAllModules();

            call.enqueue(new Callback<ModuleList>() {
                @Override
                public void onResponse(Call<ModuleList> call, Response<ModuleList> response) {
                    ModuleList modules = response.body();
                    List<ModuleDTO> moduleList= modules.getModuleList();
                    listAdapter=new ListAdapter(manageModuleActivity.this,moduleList);
                    listView.setAdapter(listAdapter);

                }

                @Override
                public void onFailure(Call<ModuleList> call, Throwable t) {
                    Toast.makeText(manageModuleActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e ){
            Toast.makeText(manageModuleActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
        }



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
                Intent intent=new Intent(manageModuleActivity.this,profileActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(manageModuleActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(manageModuleActivity.this,manageModuleActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(manageModuleActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(manageModuleActivity.this,manageStudentActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(manageModuleActivity.this,settingActivity.class);
                startActivity(intent);
                finish();
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor= sharedPreference.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(manageModuleActivity.this,MainActivity.class);
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
        private List<ModuleDTO> moduleList;

        public ListAdapter(Context context, List<ModuleDTO> moduleList) {
            this.context = context;
            this.moduleList=moduleList;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return moduleList.size();
        }

        @Override
        public Object getItem(int position) {
            return moduleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.single_module_item,null);
            }
            title=(TextView) convertView.findViewById(R.id.moduleTitle);
            deleteBtn=(Button) convertView.findViewById(R.id.delete_module);
            editBtn=(Button) convertView.findViewById(R.id.edit_module);
            letterImageView=(LetterImageView) convertView.findViewById(R.id.moduleImg);

            title.setText(moduleList.get(position).getModuleTitle());
            letterImageView.setOval(true);
            letterImageView.setLetter(moduleList.get(position).getModuleTitle().charAt(0));
            deleteBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    try{
                        Call<String> call=api.deleteModule(moduleList.get(position).getModuleId());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String check=response.body();
                                if(check.equalsIgnoreCase("success")){
                                    Toast.makeText(manageModuleActivity.this, "Module deleted successfully !", Toast.LENGTH_LONG).show();
                                    moduleList.remove(position);
                                    notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(manageModuleActivity.this, "Module deletion unsuccessfully !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(manageModuleActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                            }
                        });
                    }catch (Exception e) {
                        Toast.makeText(manageModuleActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }

                }
            });

            editBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ModuleDTO module = (ModuleDTO)listAdapter.getItem(position);
                    Intent intent=new Intent(manageModuleActivity.this,editModuleActivity.class);
                    intent.putExtra("moduleId",module.getModuleId());
                    intent.putExtra("moduleTitle",module.getModuleTitle());
                    intent.putExtra("moduleCredits",module.getModuleCredits());
                    startActivity(intent);
                    finish();
                }
            });
            return convertView;
        }
    }

}
