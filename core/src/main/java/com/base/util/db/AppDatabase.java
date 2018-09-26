
package com.base.util.db;

import android.app.Activity;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.base.util.Lists;
import com.base.util.http.GsonUtil;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by Zhu TingYu on 2018/7/3.
 */

@Database(entities = {DbEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String TYPE_USER_DATA = "TYPE_USER_DATA";
    public static final String TYPE_SEARCH_ASS_HISTORY = "TYPE_SEARCH_ASS_HISTORY";
    public static final String TYPE_SEARCH_FOOT_HISTORY = "TYPE_SEARCH_FOOT_HISTORY";
    public static final String TYPE_SEARCH_FOOT_TO_PHOTO_HISTORY = "TYPE_SEARCH_FOOT_TO_PHOTO_HISTORY";
    public static final String TYPE_SEARCH_COUNTY_HISTORY = "TYPE_SEARCH_COUNTY_HISTORY";
    public static final String TYPE_SEARCH_BREED_PIGEON = "TYPE_SEARCH_BREED_PIGEON";
    public static final String TYPE_SEARCH_TRAIN_PIGEON = "TYPE_SEARCH_TRAIN_PIGEON";
    public static final String TYPE_SEARCH_FEED_PIGEON_RECORD = "TYPE_SEARCH_FEED_PIGEON_RECORD";
    public static final String TYPE_SEARCH_PIGEON_TO_LEAGUE = "TYPE_SEARCH_PIGEON_TO_LEAGUE";
    public static final String TYPE_SEARCH_GOOD_PIGEON = "TYPE_SEARCH_GOOD_PIGEON";
    public static final String TYPE_SELECT_PIGEON_TO_TRAINING = "TYPE_SELECT_PIGEON_TO_TRAINING";
    public static final String TYPE_SEARCH_IN_TRAIN_PIGEON = "TYPE_SEARCH_IN_TRAIN_PIGEON";
    public static final String TYPE_SEARCH_SHARE_PIGEON = "TYPE_SEARCH_SHARE_PIGEON";

    private static AppDatabase INSTANCE;
    private static final Object sLok = new Object();

    public abstract DbEntityDao DbEntityDao();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLok) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "data.db")
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }

    public static <T> List<T> getDates(List<DbEntity> data, Class<T> tClass) {
        List<T> entities = Lists.newArrayList();
        for (DbEntity dbEntity : data) {
            entities.add(dbEntity.getData(tClass));
        }
        return entities;
    }

    public static <T> T getDates(DbEntity data, Class<T> tClass) {
        return data.getData(tClass);
    }

    public void delete(DbEntity data) {
        DbEntityDao().delete(data);
    }

    public void delete(List<DbEntity> data) {
        for (DbEntity dbEntity : data) {
            delete(dbEntity);
        }
    }


    public <T> void saveData(T data, String type, String userId) {
        DbEntity dbEntity = new DbEntity();
        dbEntity.setUserId(userId);
        dbEntity.setType(type);
        dbEntity.setData(GsonUtil.toJson(data));
        dbEntity.setTimeSample(System.currentTimeMillis());
        DbEntityDao().insert(dbEntity);
    }

    public <T> void saveData(List<T> data, String type, String userId) {
        for (int i = 0, len = data.size(); i < len; i++) {
            saveData(data.get(i), type, userId);
        }
    }

    public void deleteAll(List<DbEntity> data) {
        for (DbEntity t : data) {
            DbEntityDao().deleteAll(t);
        }
    }

}

