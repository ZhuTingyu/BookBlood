package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.amap.api.maps.model.LatLng;
import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.FlyBackRecordEntity;
import com.cpigeon.book.model.entity.TrainEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/20.
 */

public class FlyBackRecordViewModel extends BaseViewModel {

    public double dis;
    public String fromTime;
    public String trainId;//训练表id
    public String countId;//训练次数表
    public double fromLo;// 放飞的东经坐标
    public double fromLa; //放飞的北纬坐标
    public String windPower;//训练风力
    public String weather;//比赛天气
    public String dir;// 风向
    public String hum;//湿度
    public String alt;//海拔
    public String temper;//温度
    public String fromplace;//开始地址

    public TrainEntity mTrainEntity;
    public LatLng mHouseLocation;
    public MutableLiveData<List<FlyBackRecordEntity>> mDataFlyBack = new MutableLiveData<>();
    public MutableLiveData<String> mDataDeleteR = new MutableLiveData<>();
    public MutableLiveData<String> mDataSetTrainInfoR = new MutableLiveData<>();
    public MutableLiveData<TrainEntity> mDataTrain = new MutableLiveData<>();
    private List<FlyBackRecordEntity> mFlyBackRecordEntities;

    public FlyBackRecordViewModel(Activity activity) {
        mTrainEntity = activity.getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
        mHouseLocation = new LatLng(UserModel.getInstance().getUserData().pigeonHouseEntity.getLatitude()
                ,UserModel.getInstance().getUserData().pigeonHouseEntity.getLongitude());
    }


    public void getFlyBackRecord() {
        submitRequestThrowError(TrainPigeonModel.getFlyBackRecord(mTrainEntity.getPigeonTrainID(),
                mTrainEntity.getPigeonTrainCountID(), mTrainEntity.getTrainStateID()), r -> {
            if (r.isOk()) {
                mFlyBackRecordEntities = r.data;
                listEmptyMessage.setValue(r.msg);
                List<FlyBackRecordEntity> data = r.data;
                for (FlyBackRecordEntity entity : data) {
                    entity.mFlyBackRecordExpandEntity = Lists.newArrayList(entity);
                }
                mDataFlyBack.setValue(data);
            } else throw new HttpErrorException(r);
        });
    }

    public void getTrainEntity() {
        submitRequestThrowError(TrainPigeonModel.getTrainPigeon(mTrainEntity.getPigeonTrainID(),
                mTrainEntity.getPigeonTrainCountID()), r -> {
            if (r.isOk()) {
                mDataTrain.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    public void setTrainInfo() {
        submitRequestThrowError(TrainPigeonModel.setTrainInfo(
                dis,
                fromTime,
                mTrainEntity.getPigeonTrainID(),//训练表id
                mTrainEntity.getPigeonTrainCountID(),//训练次数表
                fromLo,// 放飞的东经坐标
                fromLa, //放飞的北纬坐标
                windPower,//训练风力
                weather,//比赛天气
                dir,// 风向
                hum,//湿度
                alt,//海拔
                temper,//温度
                fromplace //开始地址
        ), r -> {
            if(r.isOk()){
                mDataSetTrainInfoR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }

    public void endTrain() {
        submitRequestThrowError(TrainPigeonModel.endTrainPigeon(mTrainEntity.getPigeonTrainID()
                , mTrainEntity.getPigeonTrainCountID()), r -> {
            if (r.isOk()) {
                mDataDeleteR.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    //是否还有未归巢的鸽子
    public boolean isHaveNotBack() {
        if (Lists.isEmpty(mFlyBackRecordEntities)) {
            return true;
        }
        return mFlyBackRecordEntities.get(0).getFlyCount() > mFlyBackRecordEntities.size();
    }
}
