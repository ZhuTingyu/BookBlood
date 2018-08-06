
package com.base.util.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/**
 * Created by Zhu TingYu on 2018/7/3.
 */

@Database(entities = {DbEntity.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String TYPE_USER_DATA = "TYPE_USER_DATA";

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
}
