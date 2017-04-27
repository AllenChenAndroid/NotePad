package com.example.cl.notepad;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by cl on 2017/4/7.
 *
 */

public class NoteVO implements Parcelable {
    private int NoteID;
    private String NoteTitle;
    private String NoteContent;
    private Date NoteDate;
   public static final Parcelable.Creator<NoteVO> CREATOR=new Parcelable.Creator<NoteVO>(){
       @Override
       public NoteVO createFromParcel(Parcel parcel) {
           NoteVO noteVO=new NoteVO();
           noteVO.setNoteID(parcel.readInt());
           noteVO.setNoteTitle(parcel.readString());
           noteVO.setNoteContent(parcel.readString());
           noteVO.setNoteDate(ConvertDate.Stringtodate(parcel.readString()));

           return noteVO;
       }

       @Override
       public NoteVO[] newArray(int i) {
           return new NoteVO[i];
       }
   };
    public NoteVO(String noteContent, Date noteDate, String noteTitle) {
        NoteContent = noteContent;
        NoteDate = noteDate;
        NoteTitle = noteTitle;
    }

    public NoteVO(String noteContent, Date noteDate, int noteID, String noteTitle) {
        NoteContent = noteContent;
        NoteDate = noteDate;
        NoteID = noteID;
        NoteTitle = noteTitle;
    }

    public NoteVO() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(NoteID);
        parcel.writeString(NoteTitle);
        parcel.writeString(NoteContent);
        parcel.writeString(ConvertDate.datatoString(NoteDate));

    }

    public String getNoteContent() {
        return NoteContent;
    }

    public void setNoteContent(String noteContent) {
        NoteContent = noteContent;
    }

    public Date getNoteDate() {
        return NoteDate;
    }

    public void setNoteDate(Date noteDate) {
        NoteDate = noteDate;
    }

    public int getNoteID() {
        return NoteID;
    }

    public void setNoteID(int noteID) {
        NoteID = noteID;
    }

    public String getNoteTitle() {
        return NoteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        NoteTitle = noteTitle;
    }
}
