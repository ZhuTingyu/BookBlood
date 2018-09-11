package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.alibaba.idst.nls.internal.utils.L;
import com.base.base.BaseViewModel;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.TrainEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class TrainPigeonListViewModel extends BaseViewModel {
    public int pi = 1;
    int ps = 10;

    public MutableLiveData<List<TrainEntity>> mTrainData = new MutableLiveData<>();

    public void getTrainPigeonList() {
        submitRequestThrowError(TrainPigeonModel.getTrainPigeonList(pi, ps), listApiResponse -> {
            if (listApiResponse.isOk()) {
                listEmptyMessage.setValue(listApiResponse.msg);
                mTrainData.setValue(listApiResponse.data);
            }
        });
    }
}
