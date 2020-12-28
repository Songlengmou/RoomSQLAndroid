package com.anningtex.roomsqlandroid.bean;

import androidx.room.Entity;
import androidx.annotation.NonNull;

/**
 * @author Administrator
 */
@Entity(primaryKeys = {"order", "metersPerBale", "unitEn"})
public class OrderSpecEntity {
    @NonNull
    private String order;
    @NonNull
    private String metersPerBale;
    @NonNull
    private String unitEn;

    public OrderSpecEntity(String order, String metersPerBale, String unitEn) {
        this.order = order;
        this.metersPerBale = metersPerBale;
        this.unitEn = unitEn;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMetersPerBale() {
        return metersPerBale;
    }

    public void setMetersPerBale(String metersPerBale) {
        this.metersPerBale = metersPerBale;
    }

    public String getUnitEn() {
        return unitEn;
    }

    public void setUnitEn(String unitEn) {
        this.unitEn = unitEn;
    }
}
