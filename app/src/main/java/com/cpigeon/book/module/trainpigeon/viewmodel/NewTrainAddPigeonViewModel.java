package com.cpigeon.book.module.trainpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.TrainPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class NewTrainAddPigeonViewModel extends BaseViewModel {

    public int pi = 1;
    int ps = 100000;

    public String footNumber;

    public MutableLiveData<List<PigeonEntity>> mDataPigeon = new MutableLiveData<>();

    public void getPigeonList() {
        submitRequestThrowError(TrainPigeonModel.getAllPigeons(pi, ps,PigeonEntity.ID_MATCH_PIGEON
                , null, null, null, null, footNumber), r -> {
            if(r.isOk()){
                listEmptyMessage.setValue(r.msg);
                mDataPigeon.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
