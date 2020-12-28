package com.anningtex.roomsqlandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.anningtex.roomsqlandroid.bean.OrderSpecEntity;

import java.util.List;

/**
 * @author Administrator
 */
@Dao
public interface OrderSpecDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrderSpecEntity(OrderSpecEntity orderSpecEntity);

    @Query("SELECT * FROM OrderSpecEntity")
    List<OrderSpecEntity> queryAll();

    @Delete()
    void deleteOrderSpecEntity(OrderSpecEntity orderSpecEntity);
}
