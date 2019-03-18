package com.foxconn.matthew.contentproviderdemo;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayAdapter<String> adapter;

    List<String> contactList = new ArrayList<>();

    private String newId;

    private void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    , null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactList.add(displayName + "\n" + number);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView contactView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
        contactView.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
        } else {
            readContacts();
        }
        /*SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd.HHmmss");
        String date=formatter.format(new Date());
        Log.e(TAG, "onCreate: " +date);*/
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    call();
                }
                break;
            case R.id.add_data:
                Uri uri1 = Uri.parse("content://com.foxconn.matthew.databasedemo.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "Hello World");
                values.put("author", "Matthew");
                values.put("pages", 1024);
                values.put("price", 66);
                //values.put("press", "ABC");
                Uri newUri = getContentResolver().insert(uri1, values);
                newId = newUri.getPathSegments().get(1);
                break;
            case R.id.query_data:
                Uri uri2 = Uri.parse("content://com.foxconn.matthew.databasedemo.provider/book");
                Cursor cursor = getContentResolver().query(uri2, null, null, null, null);
                if (cursor.moveToNext()) {
                    do {
                        Log.e(TAG, "Name:" + cursor.getString(cursor.getColumnIndex("name"))
                                + "  Author:" + cursor.getString(cursor.getColumnIndex("author"))
                                + "  Pages:" + cursor.getInt(cursor.getColumnIndex("pages"))
                                + "  Prices:" + cursor.getDouble(cursor.getColumnIndex("price"))
                                /*+ "  Press:" + cursor.getString(cursor.getColumnIndex("press"))*/);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                break;
            case R.id.update_data:
                Uri uri3 = Uri.parse("content://com.foxconn.matthew.databasedemo.provider/book/" + newId);
                ContentValues values1 = new ContentValues();
                //values1.put("name","Hello World");
                values1.put("author", "Matthew_");
                values1.put("pages", 668);
                //values1.put("price",66);
                //values1.put("press", "BBC");
                getContentResolver().update(uri3, values1, null, null);
                break;
            case R.id.delete_data:
                Uri uri4 = Uri.parse("content://com.foxconn.matthew.databasedemo.provider/book/" + newId);
                getContentResolver().delete(uri4, null, null);
                break;
            default:
                break;
        }
    }

    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //readContacts();
                    call();
                } else {
                    Toast.makeText(this, "You denied the permission!", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                    //call();
                } else {
                    Toast.makeText(this, "You denied the permission!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
