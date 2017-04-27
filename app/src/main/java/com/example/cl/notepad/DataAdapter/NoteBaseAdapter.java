package com.example.cl.notepad.DataAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cl.notepad.ConvertDate;
import com.example.cl.notepad.NoteVO;
import com.example.cl.notepad.PrefVO;
import com.example.cl.notepad.R;

import java.util.Date;
import java.util.List;

/**
 * Created by cl on 2017/4/9.
 */

public class NoteBaseAdapter extends BaseAdapter {
    private List<NoteVO> list;
    private Context context;
    private int resource;

    public NoteBaseAdapter(Context context, List<NoteVO> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(resource,null);
        NoteVO noteVO=(NoteVO) list.get(i);
        String noteTitle=noteVO.getNoteTitle();
        Date noteDate=noteVO.getNoteDate();
        TextView imNoteIcon= (TextView) view.findViewById(R.id.itemimage);
        TextView tvNoteTitle= (TextView) view.findViewById(R.id.itemtitle);
        TextView tvNoteDate= (TextView) view.findViewById(R.id.itemdate);
        imNoteIcon.setText((i+1)+"");
        imNoteIcon.setBackgroundColor(PrefVO.themeColorValue);
        tvNoteTitle.setText(noteTitle);
        tvNoteDate.setText(ConvertDate.datatoString(noteDate));

        return view;
    }
}
