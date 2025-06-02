package com.example.loginandregisterusingretrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManagement(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("User",context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void InsertDataUsingSharePreference(String name,String email,String password,String gender,String course,String location,String phone){
        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putString("gender",gender);
        editor.putString("course",course);
        editor.putString("location",location);
        editor.putString("phone",phone);
        editor.apply();
    }

    public void EmailAndPassword(String email,String password){
        editor.putString("email",email);
        editor.putString("password",password);
        editor.apply();
    }
    public boolean CheckDataExistsEmailAndPassword(){
        if (sharedPreferences.contains("email")||sharedPreferences.contains("password")){
            return true;
        }else {
            return false;
        }
    }
    public void Logouts(){
        editor.clear();
        editor.apply();
    }
}
