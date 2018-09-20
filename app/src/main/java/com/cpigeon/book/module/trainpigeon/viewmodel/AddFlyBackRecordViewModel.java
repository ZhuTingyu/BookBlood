package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.TrainEntity;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class AddFlyBackRecordViewModel extends BaseViewModel {
    public TrainEntity mTrainEntity;
    public String footId;//足环id
    public String pigeonId;//足环id
    public String endTime; //结束时间
    public String speed;
    String footNumber;

    public AddFlyBackRecordViewModel(Activity activity) {
        mTrainEntity = activity.getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
    }

    public void addFlyBackRecord() {
        submitRequestThrowError(TrainPigeonModel.addFlyBackRecord(mTrainEntity.getPigeonTrainID()
                , mTrainEntity.getPigeonTrainCountID(), footId, endTime, speed, pigeonId), r -> {
            if (r.isOk()) {
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    public Consumer<String> setFootNumber() {
        return s -> {
            footNumber = s;
            isCanCommit();
        };
    }

    public void isCanCommit() {
        isCanCommit(footNumber, speed);
    }
}
