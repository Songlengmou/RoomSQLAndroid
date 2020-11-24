package com.anningtex.roomsqlandroid.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.anningtex.roomsqlandroid.bean.PhoneBean;
import com.anningtex.roomsqlandroid.dao.PhoneDao;

/**
 * @author Administrator
 */
@Database(entities = {PhoneBean.class}, version = 1, exportSchema = false)
@TypeConverters({ConversionFactory.class})
public abstract class PhoneDatabase extends RoomDatabase {

    public static PhoneDatabase getDefault(Context context) {
        return buildDatabase(context);
    }

    private static PhoneDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), PhoneDatabase.class, "PHONE.db")
                .allowMainThreadQueries()
                .build();
    }

    public abstract PhoneDao getPhoneDao();
}
