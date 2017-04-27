package com.example.cl.notepad.padface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

import com.example.cl.notepad.GetColor;

/**
 * Created by cl on 2017/4/9.
 */

public class NoteEditText extends EditText {
    private Paint paint;
    public NoteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        GetColor.getColor(context);
        paint=new Paint();
        paint.setColor(GetColor.iThemeColor);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int lineHeight=this.getLineHeight();
        int topPadding=this.getPaddingTop();
        int leftPadding=this.getPaddingLeft();
        float textSize=getTextSize();
        setGravity(Gravity.LEFT|Gravity.TOP);
        int y= (int) (topPadding+textSize);
        for(int i=0;i<getLineCount();i++){
            canvas.drawLine(leftPadding,y+5,getRight()-leftPadding,y+5,paint);
            y+=lineHeight;
        }
        canvas.translate(0,0);
        super.onDraw(canvas);
    }
}
