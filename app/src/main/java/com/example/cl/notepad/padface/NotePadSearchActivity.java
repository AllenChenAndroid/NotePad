package com.example.cl.notepad.padface;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.cl.notepad.DataAdapter.NoteCursorAdapter;
import com.example.cl.notepad.NoteVO;
import com.example.cl.notepad.R;
import com.example.cl.notepad.dbhandle.DBAccess;

/**
 * Created by cl on 2017/4/9.
 */

public class NotePadSearchActivity extends Activity {
    private AutoCompleteTextView searchTextView;
    private ListView searchListView;
    private NoteCursorAdapter noteCursorAdapter;
    private Cursor c;
    private DBAccess access;
    private NoteVO noteVO=new NoteVO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search);
        access=new DBAccess(this);
        c=access.selectAllNoteCursor(null,null);
        noteCursorAdapter=new NoteCursorAdapter(this,c,true);
        searchTextView= (AutoCompleteTextView) findViewById(R.id.searchtext);
        searchTextView.setDropDownHeight(0);
        searchTextView.requestFocus();
        searchTextView.setAdapter(noteCursorAdapter);
        searchListView= (ListView) findViewById(R.id.searchList);
        searchListView.setAdapter(noteCursorAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                noteVO=noteCursorAdapter.getList().get(i);
                Intent intent=new Intent();
                intent.setClass(NotePadSearchActivity.this,NotePadScanActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("note",noteVO);
                intent.putExtra("noteBundle",bundle);
                NotePadSearchActivity.this.startActivity(intent);
            }
        });
    }
}

