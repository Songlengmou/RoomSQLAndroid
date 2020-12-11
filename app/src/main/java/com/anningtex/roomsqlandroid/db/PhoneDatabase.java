package com.anningtex.roomsqlandroid.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.anningtex.roomsqlandroid.bean.NewReport;
import com.anningtex.roomsqlandroid.bean.PhoneBean;
import com.anningtex.roomsqlandroid.dao.PhoneDao;

/**
 * @author Administrator
 */
@Database(entities = {PhoneBean.class, NewReport.class}, version = 4, exportSchema = false)
@TypeConverters({ConversionFactory.class})
public abstract class PhoneDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "PHONE.db";

    public static PhoneDatabase getDefault(Context context) {
        return buildDatabase(context);
    }

    private static PhoneDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), PhoneDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                .build();
    }

    public abstract PhoneDao getPhoneDao();

    /**
     * 升级数据库
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE PHONE "
                    + " ADD COLUMN TEST_ID INTEGER NOT NULL DEFAULT 0");

            database.execSQL("ALTER TABLE PHONE "
                    + " ADD COLUMN TEST_NAME TEXT");

            database.execSQL("ALTER TABLE PHONE "
                    + " ADD COLUMN testCs INTEGER NOT NULL DEFAULT 0");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE PHONE "
                    + " ADD COLUMN TEST_PHONE TEXT");
        }
    };

    /**
     * 创建新表NewReport
     */
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `NewReport` " +
                    "(`orderNo` TEXT,`name` TEXT,`age` INTEGER NOT NULL DEFAULT 0," +
                    "PRIMARY KEY(`orderNo`))");
        }
    };
}
