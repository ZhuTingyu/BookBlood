package com.cpigeon.book.module.home.goodpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.GoodPigeonModel;
import com.cpigeon.book.model.entity.GoodPigeonCountEntity;

/**
 * Created by Zhu TingYu on 2018/9/27.
 */

public class GoodPigeonHomeViewModel extends BaseViewModel {
    public MutableLiveData<GoodPigeonCountEntity> mDataGoodPitgeonCount = new MutableLiveData<>();
    public GoodPigeonCountEntity mGoodPigeonCountEntity;

    public void getCount() {
        submitRequestThrowError(GoodPigeonModel.getGoodPigeonCount(), r -> {
            if(r.isOk()){
                mGoodPigeonCountEntity = r.data;
                mDataGoodPitgeonCount.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
