package com.foxconn.matthew.databasedemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 3);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_database:
                myDatabaseHelper.getWritableDatabase();
                break;
            case R.id.add_data:
                database = myDatabaseHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("name", "白鹿原");
                values.put("author", "陈忠实");
                values.put("pages", 559);
                values.put("price", 99.56);
                database.insert("Book", null, values);
                break;
            case R.id.update_data:
                database = myDatabaseHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("name", "大主宰");
                values.put("author", "天蚕土豆");
                database.update("Book", values, "id=?", new String[]{"2"});
                break;
            case R.id.delete_data:
                database = myDatabaseHelper.getWritableDatabase();
                //database.delete("Book", "id>?", new String[]{"3"});
                database.delete("book", null, null);
                break;
            case R.id.query_data:
                database = myDatabaseHelper.getWritableDatabase();
                Cursor cursor = database.query("Book", null, null, null, null, null, null);
                if (cursor.moveToNext()) {
                    do {
                        Log.e(TAG, "Name:" + cursor.getString(cursor.getColumnIndex("name"))
                                + "  Author:" + cursor.getString(cursor.getColumnIndex("author"))
                                + "  Pages:" + cursor.getInt(cursor.getColumnIndex("pages"))
                                + "  Prices:" + cursor.getDouble(cursor.getColumnIndex("price")));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                break;
            case R.id.sql_operate:
                database = myDatabaseHelper.getWritableDatabase();
                /**
                 * 查询
                 */
                /*Cursor sql_cursor=database.rawQuery("select * from Book",null);
                if(sql_cursor.moveToNext()){
                    do {
                        Log.e(TAG, "Name:"+sql_cursor.getString(sql_cursor.getColumnIndex("name"))
                                +"  Author:"+sql_cursor.getString(sql_cursor.getColumnIndex("author"))
                                +"  Pages:"+sql_cursor.getInt(sql_cursor.getColumnIndex("pages"))
                                +"  Prices:"+sql_cursor.getDouble(sql_cursor.getColumnIndex("price")));
                    }while (sql_cursor.moveToNext());
                }
                sql_cursor.close();*/
                /**
                 * 添加
                 */
//                database.execSQL("insert into Book (name,author,pages,price) values (?,?,?,?)"
//                        ,new String[]{"第一行代码","郭霖","498","71"});
                /**
                 * 删除
                 */
//                database.execSQL("delete from Book where name=?",new String[]{"白鹿原"});
                /**
                 * 修改
                 */
//                database.execSQL("update Book set price=? where name=?",new String[]{"66","大主宰"});
                break;
            case R.id.litePal:
                Intent intent = new Intent(this, Litepal_Activity.class);
                startActivity(intent);
                break;
            case R.id.company_database:
                try {
                    InputStream inputStream = getAssets().open("company.json");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer stringBuffer = new StringBuffer();
                    String tempString;
                    while ((tempString = bufferedReader.readLine()) != null) {
                        String str = tempString.replaceAll("\\s", "");
//                        tempString.replaceAll(" ","");
                        stringBuffer.append(str);
                    }
                    Log.e(TAG, stringBuffer.toString());
                    CompanyData companyData = new Gson().fromJson(stringBuffer.toString(), CompanyData.class);
//                    CompanyData companyData = new Gson().fromJson(bufferedReader, CompanyData.class);
//                    Log.e(TAG, companyData.toString());
                    database = myDatabaseHelper.getWritableDatabase();
                    database.execSQL("create table company_data(" +
                            "id integer primary key autoincrement," +
                            "company_name text," +
                            "company_code text)");
                    ContentValues contentValues;
                    for (CompanyData.CompanyBean companyBean : companyData.getRows()) {
                        contentValues = new ContentValues();
                        contentValues.put("company_name", companyBean.getCompany_name());
                        contentValues.put("company_code", companyBean.getCompany_code());
                        database.insert("company_data", null, contentValues);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.query_company_data:
                database = myDatabaseHelper.getWritableDatabase();
                Cursor company_names = database.rawQuery("select company_name from company_data", null);
                while (company_names.moveToNext()) {
                    Log.e(TAG, company_names.getString(company_names.getColumnIndex("company_name")));
                }
                company_names.close();
                break;
            default:
                break;
        }
    }
}
