package com.example.cl.notepad.dbhandle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cl.notepad.ConstantValue;
import com.example.cl.notepad.ConvertDate;
import com.example.cl.notepad.NoteVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cl on 2017/4/7.
 */

public class DBAccess {
    private  DBUtil dbUtil;
    private SQLiteDatabase db;
    private static String[] colNames=new String[]{
            ConstantValue.DB_MetaData.NOTEID_COL,
            ConstantValue.DB_MetaData.NOTETITLE_COL,
            ConstantValue.DB_MetaData.NOTECONTENT_COL,
            ConstantValue.DB_MetaData.NOTEDATE_COL
    };
    public DBAccess(Context context){
        dbUtil=new DBUtil(context);
    }
    public void insertNote(NoteVO noteVO){
        db=dbUtil.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ConstantValue.DB_MetaData.NOTETITLE_COL,noteVO.getNoteTitle());
        contentValues.put(ConstantValue.DB_MetaData.NOTECONTENT_COL,noteVO.getNoteContent());
        contentValues.put(ConstantValue.DB_MetaData.NOTEDATE_COL, ConvertDate.datatoString(noteVO.getNoteDate()));
        db.insert(ConstantValue.TABLE_NAME,null,contentValues);
    }
    public void deleteNote(NoteVO noteVO){
        db=dbUtil.getWritableDatabase();
        db.delete(ConstantValue.TABLE_NAME,ConstantValue.DB_MetaData.NOTEID_COL+"="+noteVO.getNoteID(),null);
    }
    public void updateNote(NoteVO noteVO){
        db=dbUtil.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ConstantValue.DB_MetaData.NOTETITLE_COL,noteVO.getNoteTitle());
        contentValues.put(ConstantValue.DB_MetaData.NOTECONTENT_COL,noteVO.getNoteContent());
        contentValues.put(ConstantValue.DB_MetaData.NOTEDATE_COL, ConvertDate.datatoString(noteVO.getNoteDate()));
        db.update(ConstantValue.TABLE_NAME,contentValues,ConstantValue.DB_MetaData.NOTEID_COL+"="+noteVO.getNoteID(),null);
    }
    public Cursor selectAllNoteCursor(String selection,String[]selectionArgs){
        db=dbUtil.getReadableDatabase();
        Cursor cursor=db.query(ConstantValue.TABLE_NAME,colNames,selection,selectionArgs,null,null,ConstantValue.DB_MetaData.DEFAULT_ORDER);
        return cursor;
    }
    public List<NoteVO> findAllNote(){
        Cursor cursor=selectAllNoteCursor(null,null);
        List<NoteVO> list=new ArrayList<NoteVO>();
        while(cursor.moveToNext()){
            int noteId=cursor.getInt(0);
            String noteTitle=cursor.getString(1);
            String noteContent=cursor.getString(2);
            String noteDate=cursor.getString(3);
            list.add(new NoteVO(noteContent,ConvertDate.Stringtodate(noteDate),noteId,noteTitle));
        }
        DBUtil.closeCusor(cursor);
        DBUtil.closeDB(db);
        return list;
    }
}
