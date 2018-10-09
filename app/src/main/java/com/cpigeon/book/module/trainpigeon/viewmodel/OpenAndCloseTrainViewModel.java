package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.TrainEntity;

/**
 * Created by Zhu TingYu on 2018/9/19.
 */

public class OpenAndCloseTrainViewModel extends BaseViewModel {

    public boolean isOpen;
    public TrainEntity mTrainEntity;

    public String temper;//气温
    public String windPower;//训练风力
    public String weather;//比赛天气
    public String trainId;//训练表id
    public String countId;//训练次数表
    public String dir;// 风向
    public String hum;//湿度
    public String alt;//海拔
    public double fromLo;// 放飞的东经坐标
    public double fromLa;//放飞的北纬坐标

    public MutableLiveData<String> mDataOpenR = new MutableLiveData<>();
    public MutableLiveData<String> mDataDeleteR = new MutableLiveData<>();
    public OpenAndCloseTrainViewModel(Activity activity) {
        isOpen = activity.getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);
        mTrainEntity = activity.getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
    }

    /*public void openTrain() {
        submitRequestThrowError(TrainPigeonModel.openTrain(
                temper,//气温
                windPower,//训练风力
                weather,//比赛天气
                mTrainEntity.getPigeonTrainID(),//训练表id
                mTrainEntity.getPigeonTrainCountID(),//训练次数表
                dir,// 风向
                hum,//湿度
                alt,//海拔
                fromLo,// 放飞的东经坐标
                fromLa //放飞的北纬坐标
        ), r -> {
            if (r.isOk()) {
                mDataOpenR.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }*/

    public void deleteTrain(){
        submitRequestThrowError(TrainPigeonModel.deleteTrain(mTrainEntity.getPigeonTrainID()
                ,mTrainEntity.getPigeonTrainCountID()),r -> {
            if(r.isOk()){
                mDataDeleteR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }
}
