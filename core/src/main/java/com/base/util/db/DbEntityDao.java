
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
    List<DbEntity> getDataByType(String userId, String type);

    @Insert
    void insert(DbEntity dbEntity);

    @Insert
    void insertAll(List<DbEntity> dbEntities);

    @Delete
    void delete(DbEntity dbEntity);

    @Update
    void updata(DbEntity dbEntity);
}

