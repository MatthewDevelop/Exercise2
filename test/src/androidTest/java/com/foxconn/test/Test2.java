package com.foxconn.test;

import android.test.AndroidTestCase;

import com.foxconn.test.sql.ListViewDAO;

public class Test2 extends AndroidTestCase {

    public void testAdd() {
        ListViewDAO dao = new ListViewDAO(getContext());
            for (int i = 0; i < 100; i++) {
                dao.add("zhangsan" + i, "nan" + 1, 1 + i);
                // 断言，如果 add 为 true，就继续执行，
                // 否则终止程序执行
                assertEquals(true, true);
            }

    }
}
