package com.example.cl.notepad.dbhandle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cl.notepad.ConstantValue;

/**
 * Created by cl on 2017/4/7.
 */

public class DBUtil extends SQLiteOpenHelper {
    public DBUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBUtil(Context context) {
        super(context, ConstantValue.DB_NAME, null, ConstantValue.DB_VERSION);
    }
    public DBUtil(Context context, int version) {
        super(context, ConstantValue.DB_NAME, null, version);
    }
    public DBUtil(Context context, String name, int version) {
        super(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql="create table "+ConstantValue.TABLE_NAME+"("+ConstantValue.DB_MetaData.NOTEID_COL+" integer primary key autoincrement,"+ConstantValue.DB_MetaData.NOTETITLE_COL+" varchar,"+ConstantValue.DB_MetaData.NOTECONTENT_COL+" text,"+ConstantValue.DB_MetaData.NOTEDATE_COL+" date)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql="drop table if exsits "+ConstantValue.TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }
    public static void closeDB(SQLiteDatabase db){
        if(db!=null){
        db.close();}

    }
    public static void closeCusor(Cursor cursor){
        if(cursor!=null)
        {
            cursor.close();
        }
    }
}
