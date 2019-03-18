package com.foxconn.test.webservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.foxconn.test.R;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-30 上午11:22
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class WsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws);
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Log.e("skylark","所有省份" + WSUtil.getProvinceList().toString());
                    }
                }.start();


            }
        });
    }
}
