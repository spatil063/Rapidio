package com.example.rapido;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import java.lang.ref.WeakReference;

public class BaseApp extends Application {

    private static WeakReference<BaseApp> mInstance = new WeakReference<>(null);

    public static Context getContext() {
        return mInstance.get().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        mInstance.clear();
        mInstance = new WeakReference<>(this);
        Stetho.initializeWithDefaults(this);

    }

}
