
package com.base.util.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.alibaba.fastjson.JSON;
import com.base.util.Utils;
import com.base.util.http.GsonUtil;

import java.lang.reflect.Type;

/**
 * Created by Zhu TingYu on 2018/7/2.
 */

@Entity
public class DbEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String userId;

    @ColumnInfo
    private String data;

    @ColumnInfo
    private String type;

    @ColumnInfo
    private long timeSample;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimeSample() {
        return timeSample;
    }

    public void setTimeSample(long timeSample) {
        this.timeSample = timeSample;
    }

    public  <T> T getData(Type tClass){
        try {
            return GsonUtil.fromJson(getData(), tClass);
        } catch (Exception e) {
            return JSON.parseObject(getData(), tClass);
        }
    }

    public <T> void updata(T data){
        this.setData(GsonUtil.toJson(data));
        AppDatabase.getInstance(Utils.getApp()).DbEntityDao()
                .update(this);
    }
}

