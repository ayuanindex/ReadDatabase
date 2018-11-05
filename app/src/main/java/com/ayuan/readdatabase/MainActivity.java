package com.ayuan.readdatabase;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第二种方式读取数据库
        //由于其他应用已经通过内容提供者暴露出来了 所以可以直接通过内容的解析者来访问
        //1.拿到内容的解析这
        Uri uri = Uri.parse("content://com.ayuan.provider/query");//路径和定义的路径的一样
        Cursor query = getContentResolver().query(uri, null, null, null, null);
        if (query != null && query.getCount() > 0) {
            while (query.moveToNext()) {
                String name = query.getString(1);
                String phone = query.getString(2);
                Log.i(TAG, "姓名:" + name + " 电话:" + phone);
            }
        }

        /**
         * 需求：读取其他应用私有的数据库
         * path:需要打开数据库的路径
         * factory:游标工厂
         * flags:访问的模式
         *//*
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase("/data/data/com.ayuan.database/databases/Account.db", null, SQLiteDatabase.OPEN_READWRITE);
        Cursor info = sqLiteDatabase.query("info", null, null, null, null, null, null);
        if (info != null && info.getCount() > 0) {
            while (info.moveToNext()) {
                String name = info.getString(1);
                String phone = info.getString(2);
                Log.i(TAG, "姓名:" + name + " 号码:" + phone);
            }
        }*/
    }
}