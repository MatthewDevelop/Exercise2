package com.foxconn.test;

import android.app.Application;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-26 下午4:50
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static MyApplication getContext(){
        return myApplication;
    }
}
