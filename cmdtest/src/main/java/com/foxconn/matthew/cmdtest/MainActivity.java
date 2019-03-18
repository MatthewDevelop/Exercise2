package com.foxconn.matthew.cmdtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ShellUtil.exec("reboot");
    }

    public void onClick(View view) {
//        ShellUtil.exec("reboot");
//        ShellUtil.execSuper("");
        //ShellUtil.exec("mount > /sdcard/mount.txt");
        SystemPartition.getSystemMountPiont();
    }

}
