package com.example.cl.notepad.DataAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.cl.notepad.ConstantValue;
import com.example.cl.notepad.ConvertDate;
import com.example.cl.notepad.NoteVO;
import com.example.cl.notepad.PrefVO;
import com.example.cl.notepad.R;
import com.example.cl.notepad.dbhandle.DBAccess;
import com.example.cl.notepad.dbhandle.DBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cl on 2017/4/9.
 */

public class NoteCursorAdapter extends CursorAdapter {
    private Context context;
    private Cursor c;
    private LayoutInflater layoutInflater;
    private View view;
    private DBUtil dbUtil;
    private List<NoteVO> list=new ArrayList<NoteVO>();

    public NoteCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
        this.c = c;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        view=layoutInflater.inflate(R.layout.item,null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView imNoteIcon= (TextView) view.findViewById(R.id.itemimage);
        TextView tvNoteTitle= (TextView) view.findViewById(R.id.itemtitle);
        TextView tvNoteDate= (TextView) view.findViewById(R.id.itemdate);
        imNoteIcon.setText(cursor.getPosition()+1+"");
        imNoteIcon.setBackgroundColor(PrefVO.themeColorValue);
        tvNoteTitle.setText(cursor.getString(cursor.getColumnIndex("notetitle")));
        tvNoteDate.setText(cursor.getString(cursor.getColumnIndex("notedate")));
        NoteVO noteVO=new NoteVO();
        noteVO.setNoteID(c.getInt(c.getColumnIndex(ConstantValue.DB_MetaData.NOTEID_COL)));
        noteVO.setNoteTitle(c.getString(c.getColumnIndex(ConstantValue.DB_MetaData.NOTETITLE_COL)));
        noteVO.setNoteContent(c.getString(c.getColumnIndex(ConstantValue.DB_MetaData.NOTECONTENT_COL)));
        noteVO.setNoteDate(ConvertDate.Stringtodate(c.getString(c.getColumnIndex(ConstantValue.DB_MetaData.NOTEDATE_COL))));
        list.add(noteVO);
    }

    public List<NoteVO> getList() {
        return list;
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        String name=cursor.getString(cursor.getColumnIndex("notetitle"));
        return name;
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        c.moveToFirst();
        if(null==dbUtil){
            dbUtil=new DBUtil(context);
        }
        list.clear();
        if(null!=constraint){
            String []selectionArgs=new String[]{"%"+constraint.toString()+"%","%"+constraint.toString()+"%"};
            String selection="notetitle like ? or notecontent like ?";
            c=new DBAccess(context).selectAllNoteCursor(selection,selectionArgs);
        }
        else{
            c=new DBAccess(context).selectAllNoteCursor(null,null);
        }
        return c;
    }
}
