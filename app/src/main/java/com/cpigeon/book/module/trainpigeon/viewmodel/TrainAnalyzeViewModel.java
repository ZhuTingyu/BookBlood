package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.TrainAnalyseEntity;
import com.cpigeon.book.model.entity.TrainEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class TrainAnalyzeViewModel extends BaseViewModel {

    public static final String TYPE_ORDER_SCORE = "1";
    public static final String TYPE_ORDER_SPEED = "2";

    private String orderType = TYPE_ORDER_SCORE;//（1:积分,2:分速（默认））
    public List<TrainEntity> mTrainEntities;
    public TrainEntity mTrainEntity;
    public MutableLiveData<List<TrainAnalyseEntity>> mDataTrainAnalyse = new MutableLiveData<>();

    public TrainAnalyzeViewModel(Activity activity) {
        mTrainEntity = activity.getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
        mTrainEntities = activity.getIntent().getParcelableArrayListExtra(IntentBuilder.KEY_DATA_2);
    }

    public void getAnalyze() {
        submitRequestThrowError(TrainPigeonModel.getPigeonTrainAnalyse(mTrainEntity.getPigeonTrainID()
                ,getTrainIds(), orderType),r -> {
            if(r.isOk()){
                listEmptyMessage.setValue(r.msg);
                mDataTrainAnalyse.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }

    private String getTrainIds() {
        List<String> trainId = Lists.newArrayList();
        for (TrainEntity trainEntity : mTrainEntities) {
            trainId.add(trainEntity.getPigeonTrainCountID());
        }
        return Lists.appendStringByList(trainId);
    }

    public void setOrderType(String orderType){
        this.orderType = orderType;
    }
}
