package com.foxconn.matthew.passworddemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Matthew on 2017/12/15.
 */

public class MyApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
