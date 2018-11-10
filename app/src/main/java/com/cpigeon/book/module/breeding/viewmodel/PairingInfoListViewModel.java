package com.cpigeon.book.module.breeding.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.BreedEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * 配对信息列表
 * Created by Administrator on 2018/9/11.
 */

public class PairingInfoListViewModel extends BaseViewModel {

    public PigeonEntity mBreedPigeonEntity;
    public BreedEntity mBreedEntity;
    public String PigeonID;
    public String FootRingID;

    public int pi = 1;
    public int ps = 50;
    public String year="";
    public String footnum="";


    public MutableLiveData<List<BreedEntity>> mBreedingInfoListData = new MutableLiveData<>();
    public MutableLiveData<List<PairingInfoEntity>> mPairingInfoListData = new MutableLiveData<>();

    //获取  配对信息列表
    public void getTXGP_PigeonBreed_SelectPigeonAllData() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreed_SelectPigeonAll(String.valueOf(pi),
                String.valueOf(ps),
                PigeonID,
                FootRingID
        ), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPairingInfoListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }
    public void getTXGP_PigeonBreed_SelectAll() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreed_SelectAll(year,footnum
        ), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mBreedingInfoListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
