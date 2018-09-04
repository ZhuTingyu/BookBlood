package com.cpigeon.book.module.breed.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.entity.BreedPigeonEntity;

/**
 * Created by Administrator on 2018/9/3.
 */

public class BreedPigeonDetailsViewModel extends BaseViewModel {

    public MutableLiveData<BreedPigeonEntity> mBreedPigeonData = new MutableLiveData<>();


    public String  footid;

    //获取  种鸽列表
    public void getPigeonDetails() {

            submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_GetInfo(footid), r -> {
                if (r.isOk()) {
                    mBreedPigeonData.setValue(r.data);
                } else throw new HttpErrorException(r);
            });

    }
}
