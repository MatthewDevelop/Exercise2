package com.foxconn.matthew.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Matthew on 2017/9/19.
 */

public class MyProvider extends ContentProvider {

    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.foxconn.matthew.contentproviderdemo", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.foxconn.matthew.contentproviderdemo", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.foxconn.matthew.contentproviderdemo", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.foxconn.matthew.contentproviderdemo", "table2/#", TABLE2_ITEM);

    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                //查询表1中所有数据
                break;
            case TABLE1_ITEM:
                //查询表1中单条数据
                break;
            case TABLE2_DIR:
                //查询表2中的所有数据
                break;
            case TABLE2_ITEM:
                //查询表2中的单条数据
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.foxconn.matthew.contentproviderdemo.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.foxconn.matthew.contentproviderdemo.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.foxconn.matthew.contentproviderdemo.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.com.foxconn.matthew.contentproviderdemo.table2";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
