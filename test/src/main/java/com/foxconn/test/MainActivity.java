package com.foxconn.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.foxconn.test.activity.CallWebServiceActivity;
import com.foxconn.test.socket.SocketActivity;
import com.foxconn.test.test.TestActivity;
import com.foxconn.test.ksoap2.MainKsoap2Activity;
import com.foxconn.test.webservice.WsActivity;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button mBtn01,mBtn02,mBtn03,mBtn04,mBtn05,mBtn06;

    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn01 = (Button) findViewById(R.id.btn_01);
        mBtn02 = (Button) findViewById(R.id.btn_02);
        mBtn03 = (Button) findViewById(R.id.btn_03);
        mBtn04 = (Button) findViewById(R.id.btn_04);
        mBtn05 = (Button) findViewById(R.id.btn_05);
        mBtn06 = (Button) findViewById(R.id.btn_06);

        mBtn01.setOnClickListener(this);
        mBtn02.setOnClickListener(this);
        mBtn03.setOnClickListener(this);
        mBtn04.setOnClickListener(this);
        mBtn05.setOnClickListener(this);
        mBtn06.setOnClickListener(this);

    }

    private Intent mIntent;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_01:
                mIntent = new Intent(MainActivity.this,ShowActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_02:
                mIntent = new Intent(MainActivity.this, MainKsoap2Activity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_03:
                mIntent = new Intent(MainActivity.this, CallWebServiceActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_04:
                mIntent = new Intent(MainActivity.this, WsActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_05:
                mIntent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_06:
                mIntent = new Intent(MainActivity.this, SocketActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }



    public interface MyOnTouchListener {
        public boolean dispatchTouchEvent(MotionEvent ev);
    }
}