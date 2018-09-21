package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.TrainEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/18.
 */

public class NewTrainPigeonViewModel extends BaseViewModel {
    public String name;
    public double fromLo;
    public double fromLa;
    public double endLo;
    public double endLa;
    public String fromLocation;
    public double dis;
    public List<PigeonEntity> mPigeonEntities;
    public MutableLiveData<TrainEntity> mDataTrain = new MutableLiveData<>();

    public TrainEntity mTrainEntity;

    public NewTrainPigeonViewModel(Activity activity) {
        mTrainEntity = activity.getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
    }

    public void getTrainDetails() {
        submitRequestThrowError(TrainPigeonModel.getTrainDetails(mTrainEntity.getPigeonTrainID()), r -> {
            if (r.isOk()) {
                mTrainEntity = r.data;
                mDataTrain.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    public void newTrainPigeon() {
        submitRequestThrowError(TrainPigeonModel.newTrainPigeon(name, fromLo, fromLa, endLo, endLa
                , fromLocation, String.valueOf(dis), getFootIds()), r -> {
            if (r.isOk()) {
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    public void trainAgain() {
        submitRequestThrowError(TrainPigeonModel.trainAgain(mTrainEntity.getPigeonTrainID()
                , fromLo, fromLa, fromLocation, dis), r -> {
            if (r.isOk()) {
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public String getFootIds() {
        List<String> footId = Lists.newArrayList();
        for (PigeonEntity pigeonEntity : mPigeonEntities) {
            footId.add(pigeonEntity.getFootRingID());
        }
        return Lists.appendStringByList(footId);
    }
}
