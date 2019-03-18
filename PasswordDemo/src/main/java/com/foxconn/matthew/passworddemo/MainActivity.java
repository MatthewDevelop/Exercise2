package com.foxconn.matthew.passworddemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText username;
    EditText password;
    CheckBox isRememberCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        isRememberCB = findViewById(R.id.isRemember);
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("isRemember", false);
        Log.e(TAG, "onCreate: " + isRemember);
        if (isRemember) {
            isRememberCB.setChecked(true);
            String[] passwordInfo = PasswordHelper.readPassword(this, isRemember);
            username.setText(passwordInfo[0]);
            password.setText(passwordInfo[1]);
        } else {
            isRememberCB.setChecked(false);
            String[] passwordInfo = PasswordHelper.readPassword(this, isRemember);
            username.setText(passwordInfo[0]);
            password.setText(passwordInfo[1]);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (username.getText().equals("") || password.getText().equals("")) {
                    Toast.makeText(this, "请输入账户信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = username.getText().toString();
                String pwd = password.getText().toString();
                boolean isRemember = isRememberCB.isChecked();
                Log.e(TAG, "isRemember:---> " + isRemember);
                if (name.equals("123") && pwd.equals("123")) {
                    PasswordHelper.savePassword(this, name, pwd, isRemember);
                    Intent intent = new Intent(this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.gson:
                //String json="\\""{"result":"true","userInfo":[{"englishName":"","address":"","sex":"男","name":"NTHZ01","chineseName":"南通弘准","telNum":"","fax":"","userId":27,"email":""}]}"""
                break;
        }
    }
}
