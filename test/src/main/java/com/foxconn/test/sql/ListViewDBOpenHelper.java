package com.foxconn.test.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-27 上午10:09
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ListViewDBOpenHelper extends SQLiteOpenHelper {

    /**
     * 数据库创建的构造方法
     *
     * @param context
     */
    public ListViewDBOpenHelper(Context context) {
        super(context, "listviewtest.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 初始化数据库的表结构
        db.execSQL("create table listview (_id integer primary key autoincrement,name varchar(20),gender varchar(20),age vachar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}