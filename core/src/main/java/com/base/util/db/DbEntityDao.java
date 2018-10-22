
package com.base.util.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


/**
 * Created by Zhu TingYu on 2018/7/2.
 */

@Dao
public interface DbEntityDao {
    @Query("SELECT * FROM DbEntity")
    List<DbEntity> getAll();

    @Query("SELECT * FROM DbEntity WHERE userId LIKE :userId" +
            " AND type LIKE :type")
    List<DbEntity> getDataByUserAndType(String userId, String type);

    @Query("SELECT * FROM DbEntity WHERE userId LIKE :userId" +
            " AND type LIKE :type AND data IS :data")
    DbEntity getData(String userId, String type, String data);


    @Query("SELECT * FROM DbEntity WHERE type LIKE :type")
    List<DbEntity> getDataByType(String type);

    @Insert
    void insert(DbEntity dbEntity);

    @Insert
    void insertAll(DbEntity... dbEntities);

    @Insert
    void insertAll(List<DbEntity> dbEntities);

    @Delete
    void delete(DbEntity dbEntity);

    @Delete
    void deleteAll(DbEntity... dbEntities);

    @Update
    void update(DbEntity dbEntity);
}

