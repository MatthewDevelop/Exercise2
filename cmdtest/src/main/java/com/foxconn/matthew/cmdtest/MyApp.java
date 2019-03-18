package com.foxconn.matthew.cmdtest;

import android.app.Application;
import android.content.Context;

/**
 * Created by Matthew on 2017/12/28.
 */

public class MyApp extends Application {

    static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

    public static Context getContext(){
        return mContext;
    }
}
