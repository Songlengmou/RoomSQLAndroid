package com.anningtex.roomsqlandroid.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

/**
 * @Author Song
 */
@Entity
public class TwoReport {
    @NotNull
    @PrimaryKey
    private String color;
    @NotNull
    private String brandName;
    private int olid;
    private boolean ciid;

    /**
     * 新增
     */
    private int bhiiid;

    @Override
    public String toString() {
        return "TwoReport{" +
                "color='" + color + '\'' +
                ", brandName='" + brandName + '\'' +
                ", olid=" + olid +
                ", ciid=" + ciid +
                '}';
    }

    public int getBhiiid() {
        return bhiiid;
    }

    public void setBhiiid(int bhiiid) {
        this.bhiiid = bhiiid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getOlid() {
        return olid;
    }

    public void setOlid(int olid) {
        this.olid = olid;
    }

    public boolean isCiid() {
        return ciid;
    }

    public void setCiid(boolean ciid) {
        this.ciid = ciid;
    }
}
