package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.FlyBackRecordEntity;
import com.cpigeon.book.model.entity.TrainEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/20.
 */

public class FlyBackRecordViewModel extends BaseViewModel {


    public TrainEntity mTrainEntity;
    public MutableLiveData<List<FlyBackRecordEntity>> mDataFlyBack = new MutableLiveData<>();
    public MutableLiveData<String> mDataDeleteR = new MutableLiveData<>();
    private List<FlyBackRecordEntity> mFlyBackRecordEntities;

    public FlyBackRecordViewModel(Activity activity) {
        mTrainEntity = activity.getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
    }


    public void getFlyBackRecord() {
        submitRequestThrowError(TrainPigeonModel.getFlyBackRecord(mTrainEntity.getPigeonTrainID(),
                mTrainEntity.getPigeonTrainCountID() , mTrainEntity.getTrainStateID()), r -> {
            if (r.isOk()) {
                mFlyBackRecordEntities = r.data;
                listEmptyMessage.setValue(r.msg);
                List<FlyBackRecordEntity> data = r.data;
                for (FlyBackRecordEntity entity : data) {
                    entity.mFlyBackRecordExpandEntity = Lists.newArrayList(entity);
                }
                mDataFlyBack.setValue(data);
            }else throw new HttpErrorException(r);
        });
    }

    public void endTrain(){
        submitRequestThrowError(TrainPigeonModel.endTrainPigeon(mTrainEntity.getPigeonTrainID()
                , mTrainEntity.getPigeonTrainCountID()),r -> {
            if(r.isOk()){
                mDataDeleteR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }
    //是否还有未归巢的鸽子
    public boolean isHaveNotBack(){
        return mFlyBackRecordEntities.get(0).getFlyCount() > mFlyBackRecordEntities.size();
    }
}
