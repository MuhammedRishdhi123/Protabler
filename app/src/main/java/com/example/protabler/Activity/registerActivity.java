package com.example.protabler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.Model.User;
import com.example.protabler.Utils.DBAccess;

import com.example.protabler.R;


public class registerActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText u_firstName,u_lastName,u_email,u_password,u_curriculum,u_batchCode,u_phoneNumber;
    private TextView loginLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        u_firstName=findViewById(R.id.first_name);
        u_lastName=findViewById(R.id.last_name);
        u_email=findViewById(R.id.email);
        u_curriculum=findViewById(R.id.curriculum);

        u_batchCode=findViewById(R.id.batchCode);
        u_password=findViewById(R.id.password);
        u_phoneNumber=findViewById(R.id.phone);
        loginLink=(TextView)findViewById(R.id.loginLink);
        loginLink.setOnClickListener(this);
    }

    public void register(View view){
        String firstName=u_firstName.getText().toString().trim();
        String lastName=u_lastName.getText().toString().trim();
        String email=u_email.getText().toString().trim();
        String password=u_password.getText().toString();
        String curriculum=u_curriculum.getText().toString();
        String batchCode=u_batchCode.getText().toString().trim();
        String phoneNumber=u_phoneNumber.getText().toString().trim();

        if(firstName.isEmpty()){
            u_firstName.setError("Name is required");
            u_firstName.requestFocus();
            return;
        }

        if(lastName.isEmpty()){
            u_lastName.setError("Name is required");
            u_lastName.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()){
            u_email.setError("Enter a valid email");
            u_email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            u_password.setError("Password is required");
            u_password.requestFocus();
            return;
        }

        if(curriculum.isEmpty()){
            u_curriculum.setError("Curriculum is required");
            u_curriculum.requestFocus();
            return;
        }

        if(batchCode.isEmpty()){
            u_batchCode.setError("Batch code is required");
            u_batchCode.requestFocus();
            return;
        }

        if(phoneNumber.isEmpty()){
            u_phoneNumber.setError("Phone is required");
            u_phoneNumber.requestFocus();
            return;
        }

        User user=new User(DBAccess.users.size()+1,firstName+" "+lastName,email,curriculum,password,phoneNumber,batchCode);
        DBAccess.addStudent(user);
        Toast.makeText(registerActivity.this,"User account created successfully !",Toast.LENGTH_LONG).show();
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginLink){
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
    }
}
