package com.foxconn.test.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-26 下午4:08
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    /**
     * 数据库名字
     */
    private static final String DB_NAME = "database.db";

    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;

    /**
     * 建表语句
     */
    private static final String CREATE_TABLE =
            "create table dataList (" +
                    "_id integer primary key autoincrement, " +
                    "number varchar(20))";

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
