package com.example.cl.notepad.padface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cl.notepad.ConvertDate;
import com.example.cl.notepad.NoteVO;
import com.example.cl.notepad.PrefVO;
import com.example.cl.notepad.R;
import com.example.cl.notepad.dbhandle.DBAccess;

/**
 * Created by cl on 2017/4/9.
 */

public class NotePadScanActivity extends Activity {
    private LinearLayout scanlayout;
    private TextView noteTitleText;
    private TextView noteContentText;
    private TextView notedateText;
    private MenuItem menuItem_0;
    private MenuItem menuItem_1;
    private MenuItem menuItem_2;
    private Intent intent;
    private NoteVO noteVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.scan);
        scanlayout= (LinearLayout) findViewById(R.id.scanlayout);
        scanlayout.setBackgroundColor(PrefVO.themeColorValue);
        noteTitleText= (TextView) findViewById(R.id.titlescan);
        noteContentText= (TextView) findViewById(R.id.contentscan);
        noteContentText.setMovementMethod(ScrollingMovementMethod.getInstance());
        notedateText= (TextView) findViewById(R.id.datescan);
        intent=this.getIntent();
        Bundle bundle=intent.getBundleExtra("noteBundle");
        noteVO=bundle.getParcelable("note");

        noteTitleText.setText(noteVO.getNoteTitle());
        noteContentText.setText(noteVO.getNoteContent());
        notedateText.setText(ConvertDate.datatoString(noteVO.getNoteDate()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        PrefVO.setIconEnable(menu,true);
        menuItem_0=menu.add(0,0,0,"编辑");
        menuItem_0.setIcon(R.drawable.edit_dark);
        menuItem_0.setOnMenuItemClickListener(new MyMenuItemClickListener());
        menuItem_1=menu.add(0,1,1,"删除");
        menuItem_1.setIcon(R.drawable.delete_dark);
        menuItem_1.setOnMenuItemClickListener(new MyMenuItemClickListener());
         menuItem_2=menu.add(0,2,2,"短信发送");
        menuItem_2.setIcon(R.drawable.message_dark);
        menuItem_2.setOnMenuItemClickListener(new MyMenuItemClickListener());
        return super.onCreateOptionsMenu(menu);
    }
    class MyMenuItemClickListener implements MenuItem.OnMenuItemClickListener{
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case 0:{
                    Intent intent=new Intent();
                    intent.setClass(NotePadScanActivity.this,NotePadEditActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("note",noteVO);
                    intent.putExtra("noteBundle",bundle);
                    NotePadScanActivity.this.startActivity(intent);
                    NotePadScanActivity.this.finish();
                    break;

                }
                case 1:{
                    AlertDialog.Builder builder=new AlertDialog.Builder(NotePadScanActivity.this);
                    builder.setTitle("删除");
                    builder.setIcon(R.drawable.delete_dark);
                    builder.setMessage("您确定要把日志删除吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DBAccess access=new DBAccess(NotePadScanActivity.this);
                            access.deleteNote(noteVO);
                            dialogInterface.dismiss();
                            Toast.makeText(NotePadScanActivity.this,"已删除",Toast.LENGTH_LONG).show();
                            NotePadScanActivity.this.finish();
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.create().show();
                    break;
                }
                case 2:{
                    Intent intent=new Intent(Intent.ACTION_SEND, Uri.parse("smsto:"));
                    if(!noteVO.getNoteContent().equals(noteVO.getNoteTitle())){
                        intent.putExtra("sms_body",noteVO.getNoteTitle()+"\n"+noteContentText);
                    }
                    else{
                        intent.putExtra("sms_body",noteVO.getNoteContent());
                    }
                    NotePadScanActivity.this.startActivity(intent);
                }
            }
            return false;
        }

    }
}
