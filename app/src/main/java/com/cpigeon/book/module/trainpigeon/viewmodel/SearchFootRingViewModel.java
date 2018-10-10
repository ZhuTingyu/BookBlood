package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.TrainEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/20.
 */

public class SearchFootRingViewModel extends BaseViewModel {

    public TrainEntity mTrainEntity;
    public MutableLiveData<List<PigeonEntity>> mDataPigeon = new MutableLiveData<>();

    public SearchFootRingViewModel(Activity activity){
        mTrainEntity = activity.getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
    }

    public void getFootRingToFlyBack() {
        submitRequestThrowError(TrainPigeonModel.searchTrainPigeon(mTrainEntity.getPigeonTrainID()
                ,mTrainEntity.getPigeonTrainCountID(), 1),r -> {
            if(r.isOk()){
                listEmptyMessage.setValue(r.msg);
                mDataPigeon.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
