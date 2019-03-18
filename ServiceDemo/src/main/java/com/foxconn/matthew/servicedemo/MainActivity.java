package com.foxconn.matthew.servicedemo;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyService.DownloadBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "onServiceConnected: ");
            mBinder = (MyService.DownloadBinder) iBinder;
            mBinder.startDownload();
            mBinder.getProgress();
        }

        /**
         * Service被异常销毁时调用
         * @param componentName
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "onServiceDisconnected: ");
        }
    };

    /**
     * 调用startService()或bindService()后，服务便会一直在后台运行，只有二者同时不满足，服务才会被销毁
     * 即同时调用startService()和bindService()后,需要调用stopService()和unbindService()方法，服务才会被销毁
     *
     * @param view
     */

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                if (isServiceRunning(MyService.class.getName())) {
                    Intent stopIntent = new Intent(this, MyService.class);
                    stopService(stopIntent);
                }
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                if (isServiceRunning(MyService.class.getName())) {
                    unbindService(connection);
                }
                break;
            case R.id.start_intent_service:
                Log.e(TAG, "onClick: current thread id " + Thread.currentThread().getId());
                Intent startIntentService = new Intent(this, MyIntentService.class);
                startService(startIntentService);
                break;
            default:
                break;
        }
    }

    /**
     * 判断服务是否在运行
     *
     * @param serviceName
     * @return
     */
    public boolean isServiceRunning(String serviceName) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(30);
        if (!(serviceInfos.size() > 0))
            return false;
        for (ActivityManager.RunningServiceInfo serviceInfo : serviceInfos) {
            //Log.e(TAG, "isServiceRunning: "+serviceInfo.service.getClassName() );
            if (serviceInfo.service.getClassName().equals(serviceName))
                //return true;
                isRunning = true;
        }
        return isRunning;
    }
}
