package com.sanshang.li.mybaseframwork.test;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by li on 2018/6/8.
 * WeChat 18571658038
 * author LiWei
 */

public class Student  implements IDataTransferObject,Parcelable{

    private String account;
    private int age;
    private boolean isMan;


    protected Student(Parcel in) {
        account = in.readString();
        age = in.readInt();
        isMan = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(account);
        dest.writeInt(age);
        dest.writeByte((byte) (isMan ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public ContentValues getData(String[] cols, String dateFormat) {
        return null;
    }
}
