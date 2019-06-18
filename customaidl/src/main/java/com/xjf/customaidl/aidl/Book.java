package com.xjf.customaidl.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * description: 专家个人信息界面
 *
 * @author xuejingfei
 * create at 19-6-17
 */
public class Book implements Parcelable {
    public  String name;
    public String des;


    public Book(String name, String des) {
        this.name = name;
        this.des = des;
    }

    protected Book(Parcel in) {
        name = in.readString();
        des = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(des);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
