package com.anningtex.roomsqlandroid.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

/**
 * @Author Song
 * @Desc:
 */
@Entity
public class NewReport {
    @NonNull
    @PrimaryKey
    private String orderNo;
    private String name;
    private int age;
    /**
     * 新增
     */
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "NewReport{" +
                "orderNo='" + orderNo + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
