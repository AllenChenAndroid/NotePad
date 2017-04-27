package com.example.cl.notepad.padface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cl.notepad.NoteVO;
import com.example.cl.notepad.PrefVO;
import com.example.cl.notepad.R;
import com.example.cl.notepad.dbhandle.DBAccess;

import java.util.Date;

/**
 * Created by cl on 2017/4/9.
 */

public class NotePadEditActivity extends Activity {
    private LinearLayout editLayout;
    private EditText noteTitleText;
    private EditText noteContentText;
    private NoteVO noteVO;
    private MenuItem menuItem_0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        editLayout= (LinearLayout) findViewById(R.id.editlayout);
        editLayout.setBackgroundColor(PrefVO.themeColorValue);
        noteTitleText= (EditText) findViewById(R.id.titleedit);
        noteContentText= (EditText) findViewById(R.id.contentedit);
        noteContentText.requestFocus();
        Intent intent=this.getIntent();
        Bundle bundle=intent.getBundleExtra("noteBundle");
        noteVO=bundle.getParcelable("note");
        noteTitleText.setText(noteVO.getNoteTitle());
        noteContentText.setText(noteVO.getNoteContent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuItem_0=menu.add(0,0,0,"删除");
        PrefVO.setIconEnable(menu,true);
        menuItem_0.setIcon(R.drawable.delete_dark);
        menuItem_0.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
               switch (menuItem.getItemId()){
                   case 0:{
                       AlertDialog.Builder builder=new AlertDialog.Builder(NotePadEditActivity.this);
                       builder.setTitle("删除");
                       builder.setIcon(R.drawable.delete_dark);
                       builder.setMessage("您确定要把日志删除吗？");
                       builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               DBAccess access=new DBAccess(NotePadEditActivity.this);
                               access.deleteNote(noteVO);
                               dialogInterface.dismiss();
                               Toast.makeText(NotePadEditActivity.this,"已删除",Toast.LENGTH_LONG).show();
                               NotePadEditActivity.this.finish();
                           }
                       });
                       builder.setNegativeButton("取消",null);
                       builder.create().show();
                       break;
                   }
               }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String noteTitle=noteTitleText.getText().toString();
        String noteContent=noteContentText.getText().toString();

        noteVO.setNoteTitle(noteTitle);
        noteVO.setNoteContent(noteContent);
        noteVO.setNoteDate(new Date());
        DBAccess access=new DBAccess(this);
        access.updateNote(noteVO);
        Toast.makeText(this,"已保存",Toast.LENGTH_LONG).show();
        Intent intent=new Intent();
        intent.setClass(this,NotePadScanActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable("note",noteVO);
        intent.putExtra("noteBundle",bundle);
        this.startActivity(intent);
        this.finish();
    }
}
