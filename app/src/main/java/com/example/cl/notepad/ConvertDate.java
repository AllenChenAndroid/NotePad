package com.example.cl.notepad;

import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by cl on 2017/4/7.
 * 用于实现Date与String的相互转换
 */

@TargetApi(Build.VERSION_CODES.N)
public class ConvertDate {
    public static SimpleDateFormat sdf;
    static {
        sdf=new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
    }
    public static String datatoString(Date date){
        String stringData=sdf.format(date);
        return stringData;
    }
    public static Date Stringtodate(String string){
        try {
            Date date=sdf.parse(string);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
