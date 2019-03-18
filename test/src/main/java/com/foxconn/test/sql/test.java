package com.foxconn.test.sql;

import android.test.AndroidTestCase;

import com.foxconn.test.MyApplication;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-26 下午4:50
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class test extends AndroidTestCase{

    public void testAdd(){

        Dao dao = new Dao(MyApplication.getContext());
        for (int i = 0; i < 100; i++) {
            String number = String.valueOf(i + 1);
            boolean add = dao.add(number);
            // 断言，如果 add 为 true，就继续执行，
            // 否则终止程序执行
            assertEquals(true, add);
        }

    }
}
