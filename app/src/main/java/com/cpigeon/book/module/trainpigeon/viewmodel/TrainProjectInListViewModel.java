package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.TrainEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/21.
 */

public class TrainProjectInListViewModel extends BaseViewModel {
    public int pi = 1;
    public MutableLiveData<List<TrainEntity>> mDataCountList = new MutableLiveData<>();
    public TrainEntity mTrainEntity;
    private List<TrainEntity> mTrainCountEntities;

    public TrainProjectInListViewModel(Activity activity) {
        mTrainEntity = activity.getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
    }

    public void getCountList() {
        submitRequestThrowError(TrainPigeonModel.getTrainCountList(mTrainEntity.getPigeonTrainID()
                , pi), r -> {
            if (r.isOk()) {
                mTrainCountEntities = r.data;
                listEmptyMessage.setValue(r.msg);
                for (TrainEntity trainEntity : mTrainCountEntities) {
                    trainEntity.setPigeonTrainName(mTrainEntity.getPigeonTrainName()
                            + "—"+ Utils.getString(R.string.text_time_content,trainEntity.getTrainCount()));
                }
                mDataCountList.setValue(mTrainCountEntities);
            } else throw new HttpErrorException(r);
        });
    }

    public boolean isAllEnd() {
        boolean isAllEnd = false;
        if (!Lists.isEmpty(mTrainCountEntities)) {
            if (mTrainCountEntities.get(0).getTrainStateName()
                    .equals(Utils.getString(R.string.text_end_yet))) {
                isAllEnd = true;
            }
        }else {
            isAllEnd = true;
        }
        return isAllEnd;
    }
}
