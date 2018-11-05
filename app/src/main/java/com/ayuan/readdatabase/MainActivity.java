package com.ayuan.readdatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

/**
 * 使用内容解析者来操作数据库
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------------------------------------------------------------------------------------------
        //第二种方式读取数据库
        //由于其他应用已经通过内容提供者暴露出来了 所以可以直接通过内容的解析者来访问
        //1.拿到内容的解析这
        Uri uri = Uri.parse("content://com.ayuan.provider/query");//路径和定义的路径的一样
        Cursor query = getContentResolver().query(uri, null, null, null, null);
        if (query != null && query.getCount() > 0) {
            while (query.moveToNext()) {
                String name = query.getString(1);
                String money = query.getString(2);
                Log.i(TAG, "姓名:" + name + " 金额:" + money);
            }
        }
        //---------------------------------------------------------------------------------------------------------
        //给数据库增加一条数据
        Uri parse = Uri.parse("content://com.ayuan.provider/insert");
        ContentValues contentValues = new ContentValues();
        //key对应表的字段  value对应值
        contentValues.put("name", "王五");
        contentValues.put("money", "1000000");
        Uri insert = getContentResolver().insert(parse, contentValues);
        Log.i(TAG, "insert:" + insert);
        //----------------------------------------------------------------------------------------------------------
        //删除数据库中的数据
        Uri parse1 = Uri.parse("content://com.ayuan.provider/delete");
        //返回值为删除的行数
        int delete = getContentResolver().delete(parse1, "name=?", new String[]{"王五"});
        Log.i(TAG, "delete:" + delete);
        //----------------------------------------------------------------------------------------------------------------
        //更新数据库的操作
        Uri parse2 = Uri.parse("content://com.ayuan.provider/update");
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("name", "赵六");
        contentValues.put("money", "200000");
        //返回值为修改的行数
        int update = getContentResolver().update(parse2, contentValues1, "name=?", new String[]{"张三"});
        Log.i(TAG, "update:" + update);


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