package com.example.cl.notepad.padface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cl.notepad.DataAdapter.NoteBaseAdapter;
import com.example.cl.notepad.NoteVO;
import com.example.cl.notepad.PrefVO;
import com.example.cl.notepad.R;
import com.example.cl.notepad.dbhandle.DBAccess;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cl on 2017/4/9.
 */

public class NotePadMainActivity extends AppCompatActivity {
    private TextView noteNumText;
    private ImageView imageViewAdd;
    private ImageView imageViewSearch;
    private ListView noteList;
    private NoteVO note=new NoteVO();
    private List<NoteVO> noteVOList;
    private DBAccess acess;
    private PrefVO prefVO;
    private MenuItem menuItem_0;
    private MenuItem menuItem_1;
    private NoteBaseAdapter noteBaseAdapter;
    private ProgressDialog progressDialog;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            flush();
            progressDialog.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_main);
       setSupportActionBar(toolbar);

        //noteNumText= (TextView) findViewById(R.id.numtext);


        progressDialog=new ProgressDialog(this);

        noteList= (ListView) findViewById(R.id.notelist);
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                note=noteVOList.get(i);
                Intent intent=new Intent();
                intent.setClass(NotePadMainActivity.this,NotePadScanActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("note",note);
                intent.putExtra("noteBundle",bundle);
                NotePadMainActivity.this.startActivity(intent);
            }
        });
        noteVOList=new ArrayList<NoteVO>();
        acess=new DBAccess(this);
        prefVO=new PrefVO(this);
        this.registerForContextMenu(noteList);



    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressDialog();
        if(PrefVO.ifLocked){
            final EditText keytext=new EditText(NotePadMainActivity.this);
            keytext.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            AlertDialog.Builder builder=new AlertDialog.Builder(NotePadMainActivity.this);
            builder.setTitle("请输入密码");
            builder.setIcon(R.drawable.lock_light);
            builder.setView(keytext);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(PrefVO.userPasswordValue.equals(keytext.getText().toString())){
                        PrefVO.appLock(false);
                        Toast.makeText(NotePadMainActivity.this,"已解除锁定",Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(NotePadMainActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                        NotePadMainActivity.this.flush();
                    }

                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    NotePadMainActivity.this.finish();
                }
            });
            builder.create().show();

        }
    }
    private void showProgressDialog(){
        progressDialog.setTitle("loading......");
        progressDialog.setMessage("正在读取数据，请稍后");
        progressDialog.show();
        new progressThread().start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main,menu);




        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
             case R.id.add:
                 Intent intent=new Intent();
                 intent.setClass(NotePadMainActivity.this,NotePadNewActivity.class);
                 NotePadMainActivity.this.startActivity(intent);
                 break;
            case R.id.search:
                Intent intentsearch=new Intent();
                intentsearch.setClass(NotePadMainActivity.this,NotePadSearchActivity.class);
                NotePadMainActivity.this.startActivity(intentsearch);
                break;
            case R.id.setting:
                Intent intentsetting=new Intent();
                intentsetting.setClass(NotePadMainActivity.this,NotePadPreferenceActivity.class);
                NotePadMainActivity.this.startActivity(intentsetting);
                break;
            case R.id.lock:
                final EditText keytext=new EditText(NotePadMainActivity.this);
                keytext.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                AlertDialog.Builder builder=new AlertDialog.Builder(NotePadMainActivity.this);
                builder.setTitle("请输入密码");
                builder.setIcon(R.drawable.lock_light);
                builder.setView(keytext);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(PrefVO.userPasswordValue.equals(keytext.getText().toString())){
                            PrefVO.appLock(true);
                            Toast.makeText(NotePadMainActivity.this,"已锁定",Toast.LENGTH_LONG).show();
                            NotePadMainActivity.this.onResume();
                        }
                        else{
                            Toast.makeText(NotePadMainActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;




        }
        return true;
    }

    private void flush() {
        PrefVO.dataFlush();
        noteVOList=acess.findAllNote();
        noteBaseAdapter=new NoteBaseAdapter(this,noteVOList,R.layout.item);
        noteList.setAdapter(noteBaseAdapter);
//        noteNumText.setText(noteVOList.size()+"");

    }

    private class progressThread extends Thread{
        public void run(){
            try{
                Thread.sleep(500);
                handler.sendEmptyMessage(1);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderIcon(R.drawable.option_light);
        menu.setHeaderTitle("日志选项");
        menu.add(0,1,1,"删除");
        menu.add(0,2,2,"短信发送");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index=info.position;
        final NoteVO note=noteVOList.get(index);
        switch (item.getItemId()){
            case 1:{
                AlertDialog.Builder builder=new AlertDialog.Builder(NotePadMainActivity.this);
                builder.setTitle("删除");
                builder.setIcon(R.drawable.delete_dark);
                builder.setMessage("您确定要把日志删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBAccess access=new DBAccess(NotePadMainActivity.this);
                        access.deleteNote(note);
                        dialogInterface.dismiss();
                        Toast.makeText(NotePadMainActivity.this,"已删除",Toast.LENGTH_LONG).show();
                        flush();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;}
            case 2:{
                Intent intent=new Intent(Intent.ACTION_SEND);
                if(!note.getNoteContent().equals(note.getNoteTitle())){
                    intent.putExtra(Intent.EXTRA_TEXT,note.getNoteTitle()+"\n"+note.getNoteContent());
                }
                else{
                    intent.putExtra(Intent.EXTRA_TEXT,note.getNoteContent());
                }
                intent.setType("text/plain");
                startActivity(intent);
                break;
            }
        }
        return super.onContextItemSelected(item);
    }
}
