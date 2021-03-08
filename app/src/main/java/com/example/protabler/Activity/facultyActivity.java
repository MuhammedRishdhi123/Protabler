package com.example.protabler.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.protabler.R;
import com.example.protabler.Utils.DBAccess;

import org.w3c.dom.Text;

public class facultyActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        setupView();
    }


    public void setupView() {
        listView = (ListView) findViewById(R.id.lv_faculty);
        FacultyListAdapter adapter = new FacultyListAdapter(this,DBAccess.facultyNames, DBAccess.facultyEmails, DBAccess.facultyPhones);
        listView.setAdapter(adapter);
    }

    public class FacultyListAdapter extends BaseAdapter {


        private int resource;
        private Context context;
        private LayoutInflater layoutInflater;
        private String[] names;
        private String[] emails;
        private String[] phoneNumbers;
        private TextView name;
        private TextView email;
        private TextView phoneNumber;


        public FacultyListAdapter(Context context,String [] names,String [] emails,String [] phoneNumbers){
            this.context=context;
            this.names=names;
            this.emails=emails;
            this.phoneNumbers=phoneNumbers;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
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
            phoneNumber = convertView.findViewById(R.id.et_phone_faculty);
            name.setText(names[position]);
            email.setText(emails[position]);
            phoneNumber.setAutoLinkMask(Linkify.PHONE_NUMBERS);
            phoneNumber.setText(phoneNumbers[position]);

            return convertView;
        }

    }
}
