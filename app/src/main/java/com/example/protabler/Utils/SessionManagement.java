package com.example.protabler.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.protabler.Model.User;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME="session";
    String SESSION_KEY="session_user";

    public  SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void saveSession(User user){
        editor.putInt(SESSION_KEY,user.getUserId());
        editor.putString("user_name",user.getName());
        editor.putString("user_email",user.getEmail());
        editor.putString("user_password",user.getPassword());
        editor.putString("user_curriculum",user.getCurriculum());
        editor.putString("user_phone",user.getPhone());
        editor.putString("user_batch_code",user.getBatchCode());
        editor.commit();
    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,-1);

    }

    public void updateSession(User user){
        editor.putString("user_name",user.getName());
        editor.putString("user_email",user.getEmail());
        editor.putString("user_password",user.getPassword());
        editor.putString("user_curriculum",user.getCurriculum());
        editor.putString("user_phone",user.getPhone());
        editor.apply();
    }


}
