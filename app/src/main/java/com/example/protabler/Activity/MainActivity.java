package com.example.protabler.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Entities.Login;
import com.example.protabler.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerLink;
    private EditText u_email,u_password;
    private String url;
    API api;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        u_email=findViewById(R.id.email);
        u_password=findViewById(R.id.password);
        registerLink=(TextView)findViewById(R.id.registerLink);
        registerLink.setOnClickListener(this);

        sharedPreferences=getSharedPreferences("session", Context.MODE_PRIVATE);

        API_BASE_URL base_url=new API_BASE_URL();
        url=base_url.getURL();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(API.class);



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPreferences.getInt("userId",-1) != -1){
            if(sharedPreferences.getString("role","none").equalsIgnoreCase("Admin")){
                Intent intent=new Intent(MainActivity.this,adminPanelActivity.class);
                startActivity(intent);
            }
            else if(sharedPreferences.getString("role","none").equalsIgnoreCase("Student")){
                Intent intent=new Intent(MainActivity.this,homeActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent(MainActivity.this,LecturerHomeActivity.class);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.registerLink){
            Intent i=new Intent(this, registerActivity.class);
            startActivity(i);
        }

    }

    public void login(View view){
        String email=u_email.getText().toString().trim();
        String password=u_password.getText().toString().trim();
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() ||email.isEmpty()){
                u_email.setError("Enter a valid email!");
                u_email.requestFocus();
                return;
            }
            else if(password.isEmpty()){
                u_password.setError("Password field cannot be empty!");
                u_password.requestFocus();
            }

            try {
                Login login=new Login(email,password);
                Call<String> call=api.login(login);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String check=response.body();

                        String[] data=check.split(" ");

                        SharedPreferences.Editor editor=sharedPreferences.edit();

                        if(data[0].equals("Invalid")){
                            Toast.makeText(MainActivity.this,"Email and password doesnt match !",Toast.LENGTH_LONG).show();
                        }
                        else if(data[0].equals("Admin"))
                        {

                            editor.putString("role",data[0]);
                            editor.putInt("userId",Integer.parseInt(data[1]));
                            editor.putString("name",data[2]);
                            editor.putString("email",data[3]);
                            editor.commit();
                            Intent intent=new Intent(MainActivity.this,adminPanelActivity.class);
                            startActivity(intent);
                        }
                        else if(data[0].equals("Student"))
                        {

                            editor.putString("role",data[0]);
                            editor.putInt("userId",Integer.parseInt(data[1]));
                            editor.putString("name",data[2]);
                            editor.putString("email",data[3]);
                            editor.commit();
                            Intent intent=new Intent(MainActivity.this,homeActivity.class);
                            startActivity(intent);
                        }
                        else if(data[0].equals("Lecturer"))
                        {

                            editor.putString("role",data[0]);
                            editor.putInt("userId",Integer.parseInt(data[1]));
                            editor.putString("name",data[2]);
                            editor.putString("email",data[3]);
                            editor.commit();
                            Intent intent=new Intent(MainActivity.this,LecturerHomeActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();
                        System.out.println(t.getLocalizedMessage());
                    }
                });

            }
            catch (Exception e){
                Toast.makeText(MainActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();
            }

    }

}
