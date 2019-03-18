package com.foxconn.matthew.databasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by Matthew on 2017/9/13.
 */

public class Litepal_Activity extends AppCompatActivity {

    private static final String TAG = "Litepal_Activity";

    MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_database:
                Connector.getDatabase();
                break;
            case R.id.add_data:
                Book book = new Book();
                book.setName("第一行代码");
                book.setAuthor("郭霖");
                book.setPages(369);
                book.setPrice(67.8);
                book.setPress("图灵出版社");
                book.save();
                //book对象再次设置数据并保存即可达到更新的效果
                //book.setPrice(88.8);
                //book.save();
                break;
            case R.id.update_data:
                Book book1 = new Book();
                book1.setPrice(16);
                book1.setPages(666);
                book1.updateAll("name=? and author=?", "第一行代码", "郭霖");
                //将指定列设置为默认值
                //book1.setToDefault("pages");
                //book1.updateAll();
                break;
            case R.id.delete_data:
                //可以通过保存过的book对象的delete方法实现数据的删除
                //也可以通过以下方式
                DataSupport.deleteAll(Book.class);
                break;
            case R.id.query_data:
                //查询所有数据
                //List<Book> books = DataSupport.findAll(Book.class);
                //查询指定的列的数据
                //List<Book> books=DataSupport.select("name","author").find(Book.class);
                //查询指定条件的数据
                //List<Book> books=DataSupport.where("pages>?","100").find(Book.class);
                //查询结果排序
                //List<Book> books=DataSupport.order("price desc").find(Book.class);
                //指定查询结果的数量
                //List<Book> books=DataSupport.limit(3).find(Book.class);
                //指定查询结果的偏移量
                //List<Book> books=DataSupport.limit(3).offset(1).find(Book.class);
                //组合查询
                List<Book> books = DataSupport
                        .where("pages>?", "100")
                        .order("price desc")
                        .limit(2)
                        .offset(1)
                        .find(Book.class);
                for (Book book2 : books) {
                    Log.e(TAG, "Name:" + book2.getName() + " Author" + book2.getAuthor() +
                            " Pages" + book2.getPages() + " Press" + book2.getPress() + " Price" + book2.getPrice());
                }
                //查询第一行数据
                //Book book=DataSupport.findFirst(Book.class);
                //查询最后一行数据
                //Book book=DataSupport.findLast(Book.class);

                break;
            case R.id.sql_operate:
                Cursor cursor = DataSupport.findBySQL("select * from book where price>? and pages>?", "20", "300");
                //Log.e(TAG, "onClick: "+cursor );
                if (cursor.moveToNext()) {
                    do {
                        Log.e(TAG, "Name:" + cursor.getString(cursor.getColumnIndex("name"))
                                + "  Author:" + cursor.getString(cursor.getColumnIndex("author"))
                                + "  Pages:" + cursor.getInt(cursor.getColumnIndex("pages"))
                                + "  Prices:" + cursor.getDouble(cursor.getColumnIndex("price"))
                                + "  Press:" + cursor.getString(cursor.getColumnIndex("press")));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                break;
            default:
                break;
        }
    }
}
