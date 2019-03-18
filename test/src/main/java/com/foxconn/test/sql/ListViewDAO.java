package com.foxconn.test.sql;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-27 上午10:09
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.foxconn.test.model.PersonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 增删改查的业务
 *
 * @author Administrator
 *
 */
public class ListViewDAO {
    private ListViewDBOpenHelper helper;

    public ListViewDAO(Context context) {
        helper = new ListViewDBOpenHelper(context);
    }

    /**
     * 查找数据库中
     * @param name
     * @return
     */
    public boolean find(String name) {
        boolean reslut = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from listview where name = ?",
                new String[] { name });
        if (cursor.moveToNext()) {
            reslut = true;
        }
        cursor.close();
        db.close();
        return reslut;
    }

    /**
     * 查找数据库
     * @param name
     * @return 默认返回null
     */
    public String findMode(String name) {
        String reslut = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select gender from listview where name = ?",
                new String[] { name });
        if (cursor.moveToNext()) {
            reslut = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return reslut;
    }

    /**
     * 添加
     * @param name
     * @param gender
     * @param age
     */
    public void add(String name, String gender,int age) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("gender", gender);
        values.put("age", age);
        db.insert("listview", null, values);
        db.close();
    }


    /**
     * 删除
     * @param number
     */
    public void delete(String number) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("listview", "number=?", new String[] { number });
        db.close();
    }

    /**
     * 查找数据库中所有
     * @return
     */
    public List<PersonInfo> findAll() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<PersonInfo> reslut = new ArrayList<PersonInfo>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select name,gender,age from listview order by _id desc", null);
        while (cursor.moveToNext()) {
            PersonInfo info = new PersonInfo();
            String name = cursor.getString(0);
            String gender = cursor.getString(1);
            int age = cursor.getInt(2);
            info.setName(name);
            info.setGender(gender);
            info.setAge(age);
            reslut.add(info);
        }
        cursor.close();
        db.close();
        return reslut;
    }

    /**
     * 查找数据库中部分
     * @param offset
     * @param maxnumber
     * @return
     */
    public List<PersonInfo> findPart(int offset, int maxnumber) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<PersonInfo> reslut = new ArrayList<PersonInfo>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db
                .rawQuery(
                        "select name,gender,age from listview order by _id desc limit ? offset ?",
                        new String[] { String.valueOf(maxnumber),
                                String.valueOf(offset) });
        while (cursor.moveToNext()) {
            PersonInfo info = new PersonInfo();
            String name = cursor.getString(0);
            String gender = cursor.getString(1);
            int age = cursor.getInt(2);
            info.setName(name);
            info.setGender(gender);
            info.setAge(age);
            reslut.add(info);
        }
        cursor.close();
        db.close();
        return reslut;
    }
}