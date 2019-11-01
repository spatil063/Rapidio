package com.example.rapido.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rapido.BaseApp;


public class SPManager {
    private static SPManager myManager;
    private static SharedPreferences sharedPreferences;
    private String ACCESS_TOKEN = "access_token";



    private SPManager(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static SPManager getInstance() {
        return myManager != null ? myManager : new SPManager(BaseApp.getContext());
    }

    private void saveString(String key, String data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    private String retrieveString(String key) {
        return sharedPreferences.getString(key, "");
    }

    private int retrieveInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void setAccessToken(String token) {
        saveString(ACCESS_TOKEN, token);
    }

    public String getToken() {
        return retrieveString(ACCESS_TOKEN);
    }

    public void setVideoID(String token) {
        saveString(ACCESS_TOKEN, token);
    }

    public String getVideoID() {
        return retrieveString(ACCESS_TOKEN);
    }



}