package com.foxconn.matthew.mediademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        //NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //manager.cancel(1);
    }
}
