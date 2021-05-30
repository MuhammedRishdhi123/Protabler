package com.example.protabler.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Dto.FacultyDTO;
import com.example.protabler.JsonList.FacultyList;
import com.example.protabler.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class facultyActivity extends AppCompatActivity {
    private ListView listView;
    API api;
    String url;
    SharedPreferences sharedPreference;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);;
        API_BASE_URL base_url=new API_BASE_URL();
        url=base_url.getURL();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(API.class);
        setupView();
    }


    public void setupView() {
        listView = (ListView) findViewById(R.id.lv_faculty);
        sharedPreference =getSharedPreferences("session",MODE_PRIVATE);
        toolbar=findViewById(R.id.ToolBarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.my_faculty));

        try {
            Call<FacultyList> call = api.getMyFaculty(sharedPreference.getInt("userId", -1));
            call.enqueue(new Callback<FacultyList>() {
                @Override
                public void onResponse(Call<FacultyList> call, Response<FacultyList> response) {
                    FacultyList facultyList = response.body();
                    List<FacultyDTO> facultyLists = facultyList.getList();
                    FacultyListAdapter adapter=new FacultyListAdapter(facultyActivity.this,R.layout.single_faculty_row, facultyLists);
                    listView.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<FacultyList> call, Throwable t) {
                    Toast.makeText(facultyActivity.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }catch(Exception e){
            Toast.makeText(facultyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public class FacultyListAdapter extends BaseAdapter {


        private int resource;
        private Context context;
        private LayoutInflater layoutInflater;
        List<FacultyDTO> facultyList;
        private TextView name;
        private TextView email;
        private TextView phone;


        public FacultyListAdapter(Context context,int resource,List<FacultyDTO> facultyList){
            this.context=context;
            this.resource=resource;
            this.facultyList=facultyList;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return facultyList.size();
        }

        @Override
        public Object getItem(int position) {
            return facultyList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.single_faculty_row, null);
            }
            name = convertView.findViewById(R.id.tv_name_faculty);
            email = convertView.findViewById(R.id.et_email_faculty);
            phone = convertView.findViewById(R.id.et_phone_faculty);
            name.setText(facultyList.get(position).getName());
            email.setText(facultyList.get(position).getEmail());
            phone.setAutoLinkMask(Linkify.PHONE_NUMBERS);
            phone.setText(facultyList.get(position).getContactNumber());

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
