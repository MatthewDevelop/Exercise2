package com.foxconn.test.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-26 下午4:25
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class Dao {
    private static final String KEY_NUMBER = "number";

    private static final String TABLE_NAME = "dataList";

    public MySQLiteOpenHelper helper;

    public Dao(Context context) {
        helper = new MySQLiteOpenHelper(context);
    }

    /**
     * 插入数据
     *
     * @param number 数字
     * @return 执行结果
     * true 表示 执行成功
     * false 表示 执行失败
     */
    public boolean add(String number) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NUMBER, number);
        // 如果执行失败，会返回 -1
        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();
        if (rowId == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 分批加载
     *
     * @param startIndex 开始的位置
     * @param maxCount   要加载的数据数量
     * @return 新增的数据
     */
    public List<String> loadMore(int startIndex, int maxCount) {
        SQLiteDatabase db = helper.getReadableDatabase();
        // limit 表示 限制当前有多少数据
        // offset 表示 跳过，从第几条开始
        // sql 语句含义：
        // 假设 startIndex 为 19， maxCount 为 20：
        // 查询从第 (19 + 1) = 20 条数据开始，往后的 20 条数据
        Cursor cursor = db.rawQuery("select number from " + TABLE_NAME + " limit ? offset ?", new
                String[]{String.valueOf(maxCount),
                String.valueOf(startIndex)});
        List<String> moreDataList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 获取每行 "number" 那一列 的数据
                String number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER));
                moreDataList.add(number);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return moreDataList;
    }

    /**
     * 获取数据总数
     *
     * @return 数据总数
     */
    public int getTotalCount() {
        int totalCount = -1;
        SQLiteDatabase db = helper.getReadableDatabase();
        // sql 语句含义：
        // 获取 "number" 那一列数据的总数
        Cursor cursor = db.rawQuery("select count(number) from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            totalCount = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return totalCount;
    }

}
