package com.foxconn.test;

import android.test.AndroidTestCase;

import com.foxconn.test.sql.Dao;

public class Test1 extends AndroidTestCase {

    public void testAdd() {
        Dao dao = new Dao(getContext());
        for (int i = 0; i < 100; i++) {
            String number = String.valueOf(i + 1);
            boolean add = dao.add(number);
            // 断言，如果 add 为 true，就继续执行，
            // 否则终止程序执行
            assertEquals(true, add);
        }
    }
}
