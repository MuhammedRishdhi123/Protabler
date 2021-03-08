package com.example.protabler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protabler.Model.UserRole;
import com.example.protabler.Utils.DBAccess;
import com.example.protabler.Model.User;
import com.example.protabler.R;
import com.example.protabler.Utils.SessionManagement;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerLink;
    private EditText u_email,u_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBAccess.main();

        u_email=findViewById(R.id.email);
        u_password=findViewById(R.id.password);
        registerLink=(TextView)findViewById(R.id.registerLink);
        registerLink.setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement=new SessionManagement(MainActivity.this);
        int user_id=sessionManagement.getSession();
        if(user_id != -1){
            for(int i=0;i< DBAccess.userRoles.size();i++){
                if(user_id==DBAccess.userRoles.get(i).getUserId()){
                    UserRole userRole=DBAccess.userRoles.get(i);
                    if(userRole.getRoleId()== 1){
                        goToAdminPanelActivity();
                    }
                    else if(userRole.getRoleId()== 2){
                        goToHomeActivity();
                    }
                }
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
        ArrayList<User> users=DBAccess.users;
        Boolean found=false;
        for(int i=0;i<users.size();i++)
        {

            if(users.get(i).getEmail().equalsIgnoreCase(email) && users.get(i).getPassword().equals(password))
            {
                User user=users.get(i);
                found=true;
                Toast.makeText(MainActivity.this,"Login Success!",Toast.LENGTH_LONG).show();
                SessionManagement sessionManagement=new SessionManagement(MainActivity.this);
                sessionManagement.saveSession(users.get(i));
                for(int j=0;j < DBAccess.userRoles.size();j++){
                    if(user.getUserId()== DBAccess.userRoles.get(j).getUserId()){
                        UserRole userRole=DBAccess.userRoles.get(j);
                        if(userRole.getRoleId()== 1)
                        {
                            goToAdminPanelActivity();
                        }
                        else if(userRole.getRoleId() == 2){
                            goToHomeActivity();
                        }
                    }

                }

            }

        }
        if(found==false){
            u_email.setText("");
            u_password.setText("");
            Toast.makeText(MainActivity.this,"Sorry your email and password do not match! Try again.",Toast.LENGTH_LONG).show();


        }
    }

    private void goToHomeActivity() {
        Intent intent=new Intent(this, homeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToAdminPanelActivity(){
        Intent intent=new Intent(this, adminPanelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
