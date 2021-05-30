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

public class LecturerHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;
    SharedPreferences sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_home);
        setupView();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupView(){
        toolbar=(Toolbar)findViewById(R.id.ToolBarMain);
        listView=(ListView)findViewById(R.id.lv_main);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.module_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.resource_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.setting_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.timetable_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.profile_menu_item).setVisible(true);
        View headerView=navigationView.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.user_profile_name);
        sharedPreference = getSharedPreferences("session",MODE_PRIVATE);
        username.setText(sharedPreference.getString("name","Username"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.home));

        String [] titles=getResources().getStringArray(R.array.LecturerHomeItems);
        String [] descriptions=getResources().getStringArray(R.array.Description_lecturerHomeItems);
        LecturerHomeActivity.ListAdapter listadapter=new LecturerHomeActivity.ListAdapter(this,titles,descriptions);
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
                        Intent intent=new Intent(LecturerHomeActivity.this,timetableActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent=new Intent(LecturerHomeActivity.this,moduleActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent=new Intent(LecturerHomeActivity.this,resourcesActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3:{
                        Intent intent=new Intent(LecturerHomeActivity.this,settingActivity.class);
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
    protected void onRestart() {
        super.onRestart();
        setupView();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.profile_menu_item:{
                Intent intent=new Intent(LecturerHomeActivity.this,profileActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.timetable_menu_item:{
                Intent intent=new Intent(LecturerHomeActivity.this,timetableActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.module_menu_item:{
                Intent intent=new Intent(LecturerHomeActivity.this,moduleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.resource_menu_item:{
                Intent intent=new Intent(LecturerHomeActivity.this,resourcesActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting_menu_item:{
                Intent intent=new Intent(LecturerHomeActivity.this,settingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.logout_menu_item:{
                SharedPreferences.Editor editor= sharedPreference.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(LecturerHomeActivity.this,MainActivity.class);
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
        private TextView title,description;
        private ImageView imageView;
        private String [] titleList;
        private String [] descriptionList;

        public ListAdapter(Context context, String[] titleList, String[] descriptionList) {
            this.context = context;
            this.titleList = titleList;
            this.descriptionList = descriptionList;
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
                convertView=layoutInflater.inflate(R.layout.home_single_item,null);
            }
            title=(TextView) convertView.findViewById(R.id.itemTitle);
            description=(TextView)convertView.findViewById(R.id.itemDesc);
            imageView=(ImageView)convertView.findViewById(R.id.itemImg);
            title.setText(titleList[position]);
            description.setText(descriptionList[position]);
            if(titleList[position].equalsIgnoreCase("Timetable")){
                imageView.setImageResource(R.drawable.schedule);
            }else if(titleList[position].equalsIgnoreCase("Modules")){
                imageView.setImageResource(R.drawable.study);
            }else if(titleList[position].equalsIgnoreCase("Resources")){
                imageView.setImageResource(R.drawable.productivity);
            }else if(titleList[position].equalsIgnoreCase("Settings")){
                imageView.setImageResource(R.drawable.settings);
            }


            return convertView;
        }
    }
}