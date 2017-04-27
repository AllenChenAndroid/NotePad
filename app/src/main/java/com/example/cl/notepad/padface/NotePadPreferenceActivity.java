package com.example.cl.notepad.padface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cl.notepad.PrefVO;
import com.example.cl.notepad.R;

import java.util.zip.Inflater;

/**
 * Created by cl on 2017/4/9.
 */

public class NotePadPreferenceActivity extends PreferenceActivity {
    private ListPreference themeList;
    private Preference usersafety,aboutapp;
    private LayoutInflater inflater;
    private LinearLayout linearLayout_1;
   private LinearLayout linearLayout_2;
    private EditText newkeytext,newkeyagaintext;
    private EditText oldkeytext,modifykeytext,modifykeyagaintext;
    private AlertDialog.Builder builder_1,builder_2;
    private Dialog dialog_1,dialog_2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        themeList=(ListPreference) findPreference("themelist");
        themeList.setSummary(PrefVO.themeListValue);
        themeList.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String value= (String) o;
                themeList.setSummary(value);
                PrefVO.setThemeListValue(value);
                return true;
            }
        });
        aboutapp=(Preference) findPreference("aboutapp");
        aboutapp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                AlertDialog ad=new AlertDialog.Builder(NotePadPreferenceActivity.this).create();
                ad.setTitle("NotePad");
                ad.setIcon(R.drawable.icon);
                ad.setMessage("         by CL      2017.4.10");
                ad.setCanceledOnTouchOutside(true);
                ad.show();
                return false;
            }
        });
        inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout_1= (LinearLayout) inflater.inflate(R.layout.newkey,null);
        linearLayout_2= (LinearLayout) inflater.inflate(R.layout.modifykey,null);
        newkeytext= (EditText) linearLayout_1.findViewById(R.id.newkeytext);
        newkeyagaintext= (EditText) linearLayout_1.findViewById(R.id.newkeyagaintext);
        oldkeytext= (EditText) linearLayout_2.findViewById(R.id.oldkeytext);
        modifykeytext= (EditText) linearLayout_2.findViewById(R.id.modifykeytext);
        modifykeyagaintext= (EditText) linearLayout_2.findViewById(R.id.modifykeyagaintext);
        builder_1=new AlertDialog.Builder(NotePadPreferenceActivity.this);
        builder_1.setView(linearLayout_1);
        builder_1.setTitle("设置新密码");
        builder_1.setIcon(R.drawable.lock_light);
        builder_1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String key=newkeytext.getText().toString();
                String keyagain=newkeyagaintext.getText().toString();
                if(key.equals("")){
                    Toast.makeText(NotePadPreferenceActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                }
                else if(key.equals(keyagain)){
                    PrefVO.setUserPasswordValue(key);
                    usersafety.setTitle("修改密码");
                    dialogInterface.dismiss();
                    clearText();
                }
                else{
                    Toast.makeText(NotePadPreferenceActivity.this,"两次输入不一致",Toast.LENGTH_LONG).show();
                }

            }

        });
        builder_1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                clearText();
            }
        });
        dialog_1=builder_1.create();
        builder_2=new AlertDialog.Builder(NotePadPreferenceActivity.this);
        builder_2.setView(linearLayout_2);
        builder_2.setTitle("修改密码");
        builder_2.setIcon(R.drawable.lock_light);
        builder_2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String oldkey=oldkeytext.getText().toString();
                String key=modifykeytext.getText().toString();
                String keyagain=modifykeyagaintext.getText().toString();
                if(!oldkey.equals(PrefVO.userPasswordValue)){
                    Toast.makeText(NotePadPreferenceActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                }
                else if(key.equals("")){
                    Toast.makeText(NotePadPreferenceActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                }
                else if(key.equals(keyagain)){

                    PrefVO.setUserPasswordValue(key);
                    dialogInterface.dismiss();
                    clearText();
                }
                else{
                    Toast.makeText(NotePadPreferenceActivity.this,"两次输入不一致",Toast.LENGTH_LONG).show();
                }

            }

        });
        builder_2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                clearText();
            }
        });
        dialog_2=builder_2.create();
        usersafety=findPreference("usersafety");
        if(PrefVO.userPasswordValue.equals("")){
            usersafety.setTitle("设置新密码");
        }
        else{
            usersafety.setTitle("修改密码");
        }
        usersafety.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(PrefVO.userPasswordValue.equals("")){
                    dialog_1.show();
                }
                else{
                    dialog_2.show();
                }
                return false;
            }
        });





    }

    private void clearText() {
        newkeytext.setText("");
        newkeyagaintext.setText("");
        newkeytext.requestFocus();


        oldkeytext.setText("");
        modifykeytext.setText("");
        modifykeyagaintext.setText("");
        oldkeytext.requestFocus();
    }
}
