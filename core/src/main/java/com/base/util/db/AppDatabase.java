
package com.base.util.db;

import android.app.Activity;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.base.util.Lists;
import com.base.util.http.GsonUtil;

import java.util.List;


/**
 * Created by Zhu TingYu on 2018/7/3.
 */

@Database(entities = {DbEntity.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String TYPE_USER_DATA = "TYPE_USER_DATA";
    public static final String TYPE_SEARCH_ASS_HISTORY = "TYPE_SEARCH_ASS_HISTORY";
    public static final String TYPE_SEARCH_FOOT_HISTORY = "TYPE_SEARCH_FOOT_HISTORY";
    public static final String TYPE_SEARCH_COUNTY_HISTORY = "TYPE_SEARCH_COUNTY_HISTORY";

    private static AppDatabase INSTANCE;
    private static final Object sLok = new Object();
    public abstract DbEntityDao DbEntityDao();

    public static AppDatabase getInstance(Context context){
        synchronized (sLok){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,"data.db")
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }

    public static  <T> List<T> getDates(List<DbEntity> data, Class<T> tClass){
        List<T> entities = Lists.newArrayList();
        for (DbEntity dbEntity : data) {
            entities.add(dbEntity.getData(tClass));
        }
        return entities;
    }

    public <T> void saveData(T data, String type, String userId){
        DbEntity dbEntity = new DbEntity();
        dbEntity.setUserId(userId);
        dbEntity.setType(type);
        dbEntity.setData(GsonUtil.toJson(data));
        dbEntity.setTimeSample(System.currentTimeMillis());
        DbEntityDao().insert(dbEntity);
    }

    public  void delectAll(List<DbEntity> data){
        for (DbEntity t : data) {
            DbEntityDao().deleteAll(t);
        }
    }

}

