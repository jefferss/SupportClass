package com.example.andorid.supportclass.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kona on 2017/9/14.
 * Sharepreference 封装包
 */

public class PrefUtils {
    public static boolean getBoolean(Context cox,String key,boolean defult){
        //activity 本身就是一个context
        SharedPreferences sp = cox.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key,defult);
    }
    public static void setBoolean(Context cox,String key,boolean value){
        //activity 本身就是一个context
        SharedPreferences sp = cox.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    public static void setString(Context cox,String key,String value){
        //activity 本身就是一个context
        SharedPreferences sp = cox.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static String getString(Context cox,String key,String defult){
        //activity 本身就是一个context
        SharedPreferences sp = cox.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key,defult);
    }
    public static int getInt(Context cox,String key, int defult){
        //activity 本身就是一个context
        SharedPreferences sp = cox.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key,defult);
    }
    public static void setInt(Context cox,String key,int value){
        //activity 本身就是一个context
        SharedPreferences sp = cox.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
}
