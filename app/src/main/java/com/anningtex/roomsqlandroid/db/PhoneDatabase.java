package com.anningtex.roomsqlandroid.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;

import android.content.Context;

import com.anningtex.roomsqlandroid.bean.NewReport;
import com.anningtex.roomsqlandroid.bean.OrderSpecEntity;
import com.anningtex.roomsqlandroid.bean.PhoneBean;
import com.anningtex.roomsqlandroid.bean.TwoReport;
import com.anningtex.roomsqlandroid.dao.OrderSpecDao;
import com.anningtex.roomsqlandroid.dao.PhoneDao;

/**
 * @author Administrator
 */
@Database(entities = {PhoneBean.class, NewReport.class, TwoReport.class, OrderSpecEntity.class},
        version = 8, exportSchema = false)
@TypeConverters({ConversionFactory.class})
public abstract class PhoneDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "PHONE.db";

    public static PhoneDatabase getDefault(Context context) {
        return buildDatabase(context);
    }

    private static PhoneDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), PhoneDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7)
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
     * 创建新表NewReport 和 TwoReport  两种写法格式
     */
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `NewReport` " +
                    "(`orderNo` TEXT,`name` TEXT,`age` INTEGER NOT NULL DEFAULT 0," +
                    "PRIMARY KEY(`orderNo`))");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //创建新表TwoReport
            database.execSQL("CREATE TABLE IF NOT EXISTS TwoReport " +
                    "(color TEXT PRIMARY KEY NOT NULL, brandName TEXT NOT NULL, olid INTEGER NOT NULL DEFAULT 0," +
                    "ciid INTEGER NOT NULL DEFAULT 0)");
        }
    };

    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //在NewReport新增一个键值
            database.execSQL("ALTER TABLE NewReport "
                    + " ADD COLUMN address TEXT");

            //在TwoReport新增一个键值
            database.execSQL("ALTER TABLE TwoReport "
                    + " ADD COLUMN bhiiid INTEGER NOT NULL DEFAULT 0");
        }
    };

    /**
     * 创建新表，删除原表，改名原表
     */
    static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //1. 创建一个新表TwoReportTemp，只设定想要的字段
            database.execSQL("CREATE TABLE TwoReportTemp " +
                    "(color TEXT PRIMARY KEY NOT NULL, brandName TEXT NOT NULL," +
                    "ciid INTEGER NOT NULL DEFAULT 0)");
            //2.将原来表中的数据复制过来
            database.execSQL("INSERT INTO TwoReportTemp (color,brandName,ciid) " +
                    "SELECT color, brandName, ciid FROM TwoReport"
            );

            //todo 这两句有error
            //3.将原表TwoReport删除
//            database.execSQL("DROP TABLE TwoReport");
            //4.将新建的表改名
//            database.execSQL("ALTER TABLE TwoReportTemp RENAME to TwoReport");
        }
    };

    /**
     * 不需要写上方的这些Migration，直接创建一个新表,升级数据库版本进行调用
     */
    public abstract OrderSpecDao getOrderSpecDao();
}
