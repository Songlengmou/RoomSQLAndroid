package com.anningtex.roomsqlandroid.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * @author Administrator
 */
@Entity(tableName = "PHONE")
public class PhoneBean implements Parcelable {
    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo(name = "ID") // 定义对应的数据库的字段名成
    private int id;

    @ColumnInfo(name = "PHONE")
    private String phone;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "DATE")
    private Date date;

    public PhoneBean(String phone, String name, Date date) {
        this.phone = phone;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    protected PhoneBean(Parcel in) {
        this.id = in.readInt();
        this.phone = in.readString();
        this.name = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<PhoneBean> CREATOR = new Creator<PhoneBean>() {
        @Override
        public PhoneBean createFromParcel(Parcel source) {
            return new PhoneBean(source);
        }

        @Override
        public PhoneBean[] newArray(int size) {
            return new PhoneBean[size];
        }
    };
}
