package com.bupt.bignews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bupt.bignews.entity.TokenBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhaoJie1234 on 2017/3/17.
 */
public class TokenUtil {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TokenBean tokenBean;

    public TokenUtil(Context context){
        this.context = context;
    }
    /*
    * 保存参数
    * */
    public void  saveToken(String name,String token){
        preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("username",name);
        editor.putString("token",token);
        editor.commit();
    }
    /*
    * 获取token
    * */
    public String getToken(){
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return preferences.getString("token","");

    }
    /*
    * 删除token
    * */
    public void deleteToken(){
        preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove("username");
        editor.remove("token");
        editor.commit();
    }




}
