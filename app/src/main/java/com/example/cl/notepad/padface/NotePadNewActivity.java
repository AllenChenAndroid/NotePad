package com.example.cl.notepad.padface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class NotePadNewActivity extends Activity {
    private LinearLayout editLayout;
    private EditText noteTitleText;
    private EditText noteContentText;
    private boolean flagTextChanged=true;
    private MenuItem menuItem_0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String noteTitle=noteTitleText.getText().toString();
        String noteContent=noteContentText.getText().toString();
        if(noteTitle.toString().trim().equals("")&&noteContent.toString().trim().equals("")){
            NotePadNewActivity.this.finish();
        }
        else {
            NoteVO noteVO=new NoteVO();
            noteVO.setNoteTitle(noteTitle);
            noteVO.setNoteContent(noteContent);
            noteVO.setNoteDate(new Date());
            DBAccess access=new DBAccess(this);
            access.insertNote(noteVO);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        editLayout= (LinearLayout) findViewById(R.id.editlayout);
        editLayout.setBackgroundColor(PrefVO.themeColorValue);
        noteTitleText= (EditText) findViewById(R.id.titleedit);
        noteContentText= (EditText) findViewById(R.id.contentedit);
        noteContentText.requestFocus();
        noteContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(flagTextChanged&&(noteTitleText.getText().toString().trim().equals(null)||noteTitleText.getText().toString().trim().equals(""))){
                    flagTextChanged=true;
                }
                else if(!noteTitleText.getText().toString().equals(noteContentText.getText().toString())){
                    flagTextChanged=false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(flagTextChanged){
                    noteTitleText.setText(noteContentText.getText());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuItem_0=menu.add(0,0,0,"删除");
        PrefVO.setIconEnable(menu,true);
        menuItem_0.setIcon(R.drawable.delete_dark);
        menuItem_0.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case 0:{
                        NotePadNewActivity.this.finish();
                        break;
                    }
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
