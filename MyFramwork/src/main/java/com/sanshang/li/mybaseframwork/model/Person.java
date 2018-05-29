package com.sanshang.li.mybaseframwork.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by li on 2018/5/22.
 * WeChat 18571658038
 * author LiWei
 */

public class Person implements Parcelable {

    private String name;

    private int age;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
