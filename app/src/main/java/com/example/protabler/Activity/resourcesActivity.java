package com.example.protabler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.protabler.R;

public class resourcesActivity extends AppCompatActivity {

    private GridView gridView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        setupView();
    }


    public void setupView(){
        gridView=findViewById(R.id.gv_resources);
        toolbar=findViewById(R.id.ToolBarMain);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(R.string.resources));

        String [] resources=getResources().getStringArray(R.array.resources);
        ListAdapter adapter =new ListAdapter(resourcesActivity.this,resources);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        Intent intent=new Intent(resourcesActivity.this,myCalendarActivity.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }



    public class ListAdapter extends BaseAdapter {

        private Context context;
        private String [] resources;
        private ImageView resourceImage;
        private TextView resourceName;
        private LayoutInflater layoutInflater;

        public ListAdapter (Context context,String [] resources){
            this.context = context;
            this.resources = resources;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return resources.length;
        }

        @Override
        public Object getItem(int position) {
            return resources[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.single_resource_item,null);
            }
            resourceName=(TextView) convertView.findViewById(R.id.resource_name);
            resourceImage=(ImageView)convertView.findViewById(R.id.resource_image);

            resourceName.setText(resources[position]);
            if(resources[position].equalsIgnoreCase("My Calender")){
                resourceImage.setImageResource(R.drawable.calendar);
            }


            return convertView;
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
