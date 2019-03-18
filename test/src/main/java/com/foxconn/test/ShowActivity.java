package com.foxconn.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.foxconn.test.test.TestActivity;
import com.foxconn.test.activity.TestListViewLoadMoreActivity;
import com.foxconn.test.activity.TestListViewLoadMoreActivity2;
import com.foxconn.test.activity.TestListViewLoadMoreActivity3;
import com.foxconn.test.fragment.TestFragment;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-30 上午8:46
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ShowActivity extends MainActivity {
    private RadioButton[] radioButton;
    private RadioGroup radioGroup;
    private Intent mIntent;

    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        radioGroup = (RadioGroup) findViewById(R.id.rg_bottom);
        initRadioGroup();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new TestFragment());
        fragmentTransaction.commit();

//        FileUtil.mkdirs(FileUtil.getSDcardPath() + "/skylark/key");
//        FileUtil.copyAssetDirToFiles(this,"key",FileUtil.getSDcardPath() + "/skylark/key");
//        FileUtil.copyAssetDirToFiles(this,"key",FileUtil.getSDcardPath() + "/skylark/key");

    }

    private void initRadioGroup() {
        radioButton = new RadioButton[radioGroup.getChildCount()];
        for (int i = 0; i < radioButton.length; i++) {
            radioButton[i] = (RadioButton) radioGroup.getChildAt(i);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_daily:
                        mIntent = new Intent(ShowActivity.this, TestListViewLoadMoreActivity.class);
                        startActivity(mIntent);
                        break;
                    case R.id.rbtn_report:
                        mIntent = new Intent(ShowActivity.this, TestListViewLoadMoreActivity2.class);
                        startActivity(mIntent);

                        break;
                    case R.id.rbtn_tools:
                        mIntent = new Intent(ShowActivity.this, TestListViewLoadMoreActivity3.class);
                        startActivity(mIntent);
                        break;
                    case R.id.rbtn_settings:
                        mIntent = new Intent(ShowActivity.this, TestActivity.class);
                        startActivity(mIntent);
                        break;
                }
            }
        });

    }
}
