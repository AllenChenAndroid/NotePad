package com.example.cl.notepad;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Created by cl on 2017/4/9.
 */

public class PrefVO {
    public static String themeListValue;
    public static int themeColorValue;
    public static String userPasswordValue;
    public static boolean ifLocked;
    private static Context context;
    private static SharedPreferences shares;
    private static SharedPreferences.Editor editor;

    public PrefVO(Context context) {
        super();
        PrefVO.context=context;
        PrefVO.shares=context.getSharedPreferences("notepadshares",Context.MODE_PRIVATE);
        PrefVO.editor=shares.edit();
        getThemeListValue();
        getUserPasswordValue();

    }

    public static void getUserPasswordValue() {
        userPasswordValue=shares.getString("userpassword","");
        ifLocked=shares.getBoolean("iflocked",false);
    }

    public static void setUserPasswordValue(String userPasswordValue) {
        editor.putString("userpassword",userPasswordValue);
        editor.putBoolean("ifclocked",true);
        editor.commit();
        Toast.makeText(context,"已设置新密码",Toast.LENGTH_LONG).show();
        getUserPasswordValue();
    }

    public static void getThemeListValue() {
        themeListValue=shares.getString("themelist","天蓝");
        if(themeListValue.equals("天蓝")){
            themeColorValue=ConstantValue.THEME_BLUE;
        }
        else if(themeListValue.equals("椰壳绿")){
            themeColorValue=ConstantValue.THEME_GREEN;
        }
        else if(themeListValue.equals("芒果")){
            themeColorValue=ConstantValue.THEME_YELLOW;
        }
        else if(themeListValue.equals("胭脂红")){
            themeColorValue=ConstantValue.THEME_RED;
        }
        else if(themeListValue.equals("驼绒")){
            themeColorValue=ConstantValue.THEME_BROWN;
        }
        else if(themeListValue.equals("鲜橙")){
            themeColorValue=ConstantValue.THEME_ORANGE;
        }
        else if(themeListValue.equals("紫丁香")){
            themeColorValue=ConstantValue.THEME_PURPLE;
        }

    }

    public static void setThemeListValue(String themeListValue) {
      editor.putString("themelist",themeListValue);
        editor.commit();
        getThemeListValue();
    }
    public static void dataFlush(){
        getThemeListValue();
        getUserPasswordValue();
    }
    public static void appLock(boolean flag){
        editor.putBoolean("iflocked",flag);
        editor.commit();
        getUserPasswordValue();
    }
    public static void setIconEnable(Menu menu, boolean enable){
        try{
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);

            //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
