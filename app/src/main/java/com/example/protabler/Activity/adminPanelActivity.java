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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.protabler.R;
import com.google.android.material.navigation.NavigationView;

public class adminPanelActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ListView listView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        setupView();
    }


    private void setupView(){
        toolbar=(Toolbar)findViewById(R.id.ToolBar);
        listView=(ListView)findViewById(R.id.lv_admin_panel);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.manage_course_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.manage_lecturer_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.manage_module_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.manage_student_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.manage_timetable_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.profile_menu_item).setVisible(true);
        View headerView=navigationView.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.user_profile_name);
        sharedPreferences = getSharedPreferences("session",MODE_PRIVATE);
        username.setText(sharedPreferences.getString("name","Username"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Panel");

        String [] titles=getResources().getStringArray(R.array.adminPanelItems);
        ListAdapter listadapter=new ListAdapter(this,titles);
        listView.setAdapter(listadapter);

        mtoggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Events when user clicks on the list view items.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:{
                        Intent intent=new Intent(adminPanelActivity.this,manageTimetableActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent=new Intent(adminPanelActivity.this,manageModuleActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent=new Intent(adminPanelActivity.this,manageLecturerActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3:{
                        Intent intent=new Intent(adminPanelActivity.this,manageStudentActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent=new Intent(adminPanelActivity.this,settingActivity.class);
                        startActivity(intent);
                        break;

                    }
                    default: {
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.profile_menu_item:{
                Intent intent=new Intent(adminPanelActivity.this,profileActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.manage_timetable_menu_item:{
                Intent intent=new Intent(adminPanelActivity.this,manageTimetableActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.manage_module_menu_item:{
                Intent intent=new Intent(adminPanelActivity.this,manageModuleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.manage_lecturer_menu_item:{
                Intent intent=new Intent(adminPanelActivity.this,manageLecturerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.manage_student_menu_item:{
                Intent intent=new Intent(adminPanelActivity.this,manageStudentActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(adminPanelActivity.this,settingActivity.class);
                startActivity(intent);
                break;

            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(adminPanelActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setupView();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
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
        private TextView title;
        private ImageView imageView;
        private String [] titleList;

        public ListAdapter(Context context, String[] titleList) {
            this.context = context;
            this.titleList = titleList;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return titleList.length;
        }

        @Override
        public Object getItem(int position) {
            return titleList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.single_admin_panel_item,null);
            }
            title=(TextView) convertView.findViewById(R.id.itemTitle);
            imageView=(ImageView)convertView.findViewById(R.id.itemImg);
            title.setText(titleList[position]);
            if(titleList[position].equalsIgnoreCase("Manage timetable")){
                imageView.setImageResource(R.drawable.admin_timetable);
            }else if(titleList[position].equalsIgnoreCase("Manage modules")){
                imageView.setImageResource(R.drawable.admin_modules);
            }else if(titleList[position].equalsIgnoreCase("Manage lecturers")){
                imageView.setImageResource(R.drawable.ic_people_black_24dp);
            }else if(titleList[position].equalsIgnoreCase("Manage students")){
                imageView.setImageResource(R.drawable.admin_students);
            }else{
                imageView.setImageResource(R.drawable.settings);
            }


            return convertView;
        }
    }
}
