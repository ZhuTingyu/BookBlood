package com.cpigeon.book.module.breeding.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/15 0015.
 */

public class PairingNestInfoListViewModel extends BaseViewModel {

    public PigeonEntity mBreedPigeonEntity;

    public PairingInfoEntity mPairingInfoEntity;
    public int pi = 1;
    public int ps = 15;

    public MutableLiveData<List<PairingNestInfoEntity>> mPairingNestInfoData = new MutableLiveData<>();

    //获取  单个繁育表里的窝次信息 列表
    public void getgetTXGP_PigeonBreed_SelectIDAll() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreed_SelectIDAll(String.valueOf(pi),
                String.valueOf(ps),
                mPairingInfoEntity.getPigeonBreedID()), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPairingNestInfoData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


}