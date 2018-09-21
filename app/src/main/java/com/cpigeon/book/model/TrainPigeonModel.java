package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.FlyBackRecordEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonTrainDetailsEntity;
import com.cpigeon.book.model.entity.TrainEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class TrainPigeonModel {
    public static Observable<ApiResponse<List<TrainEntity>>> getTrainPigeonList(int pi, int ps) {
        return RequestData.<ApiResponse<List<TrainEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<TrainEntity>>>() {
                }.getType())
                .url(R.string.get_train_pigeon_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }

    public static Observable<ApiResponse<List<PigeonEntity>>> getAllPigeons(int pi
            , int ps
            , String typeId
            , String blood
            , String sex
            , String year
            , String state
            , String footNumber
    ) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.get_all_pigeon_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .addBody("typeid", typeId)
                .addBody("blood", blood)
                .addBody("sex", sex)
                .addBody("year", year)
                .addBody("State", state)
                .addBody("footnum", footNumber)
                .request();
    }

    public static Observable<ApiResponse> newTrainPigeon(
            String name,
            double fromLo,
            double fromLa,
            double endLo,
            double endLa,
            String fromLocation,
            String dis,
            String footIds
    ) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.new_train_pigeon)
                .addBody("name", name)
                .addBody("fromlo", String.valueOf(fromLo))
                .addBody("fromla", String.valueOf(fromLa))
                .addBody("endlo", String.valueOf(endLo))
                .addBody("endla", String.valueOf(endLa))
                .addBody("fromplace", fromLocation)
                .addBody("dis", dis)
                .addBody("idstr", footIds)
                .request();
    }

    public static Observable<ApiResponse<TrainEntity>> getTrainDetails(String trainId) {
        return RequestData.<ApiResponse<TrainEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<TrainEntity>>() {
                }.getType())
                .url(R.string.get_train_pigeon_details)
                .addBody("trainid", String.valueOf(trainId))
                .request();
    }

    public static Observable<ApiResponse> openTrain(
            String temper,//气温
            String windPower,//训练风力
            String weather,//比赛天气
            String trainId,//训练表id
            String count,//训练次数表
            String dir,// 风向
            String hum,//湿度
            String alt,//海拔
            double fromLo,// 放飞的东经坐标
            double fromLa //放飞的北纬坐标
    ) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.open_train_pigeon)
                .addBody("temper", temper)
                .addBody("windpower", windPower)
                .addBody("weather", weather)
                .addBody("trainid", trainId)
                .addBody("countid", count)
                .addBody("dir", dir)
                .addBody("hum", hum)
                .addBody("alt", alt)
                .addBody("fromlo", String.valueOf(fromLo))
                .addBody("fromla", String.valueOf(fromLa))
                .request();
    }

    public static Observable<ApiResponse> deleteTrain(
            String trainId,//训练表id
            String countId//训练次数表
    ) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.delete_train_pigeon)
                .addBody("trainid", trainId)
                .addBody("countid", countId)
                .request();
    }

    public static Observable<ApiResponse<List<FlyBackRecordEntity>>> getFlyBackRecord(
            String trainId,
            String countid,
            String stateId
    ) {
        return RequestData.<ApiResponse<List<FlyBackRecordEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<FlyBackRecordEntity>>>() {
                }.getType())
                .url(R.string.get_fly_back_record)
                .addBody("trainid", String.valueOf(trainId))
                .addBody("countid", countid)
                .addBody("StateID", stateId)
                .addBody("pi", String.valueOf(1))
                .addBody("ps", String.valueOf(100000))
                .request();
    }

    public static Observable<ApiResponse<List<FlyBackRecordEntity>>> addFlyBackRecord(
            String trainId,
            String countId,//训练次数表
            String footId, //足环id
            String endTime, //结束时间
            String speed, //得分
            String pigeonId //得分
    ) {
        return RequestData.<ApiResponse<List<FlyBackRecordEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<FlyBackRecordEntity>>>() {
                }.getType())
                .url(R.string.add_fly_back_record)
                .addBody("trainid", trainId)
                .addBody("countid", countId)
                .addBody("footid", footId)
                .addBody("endtime", endTime)
                .addBody("fraction", speed)
                .addBody("pigeonID", pigeonId)
                .request();
    }

    public static Observable<ApiResponse<List<PigeonEntity>>> searchTrainPigeon(
            String trainId,
            String countId,
            int pi
    ) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.search_train_pigeon)
                .addBody("trainid", trainId)
                .addBody("countid", countId)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(10000))
                .request();
    }

    public static Observable<ApiResponse> endTrainPigeon(
            String trainId,
            String countId
    ) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.end_train_pigeon)
                .addBody("trainid", trainId)
                .addBody("countid", countId)
                .request();
    }

    public static Observable<ApiResponse<List<TrainEntity>>> getTrainCountList(
            String trainId,
            int pi
    ) {
        return RequestData.<ApiResponse<List<TrainEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<TrainEntity>>>() {
                }.getType())
                .url(R.string.get_train_count_list)
                .addBody("trainid", trainId)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(10))
                .request();
    }

    public static Observable<ApiResponse> trainAgain(
            String trainId,//训练记录id
            double fromLo,// 放飞的东经坐标
            double fromLa,//放飞的北纬坐标
            String fromPlace,//开始地址
            double dis//训练空距
    ) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.train_again)
                .addBody("trainid", trainId)
                .addBody("fromlo", String.valueOf(fromLo))
                .addBody("fromla", String.valueOf(fromLa))
                .addBody("fromplace", fromPlace)
                .addBody("dis", String.valueOf(dis))
                .request();
    }

    public static Observable<ApiResponse<List<PigeonTrainDetailsEntity>>> getPigeonTrainDetails(
            String foodId,
            String pigeonId
    ) {
        return RequestData.<ApiResponse<List<PigeonTrainDetailsEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonTrainDetailsEntity>>>() {
                }.getType())
                .url(R.string.get_pigeon_train_details)
                .addBody("footid", foodId)
                .addBody("pigeonid", pigeonId)
                .request();
    }
}
