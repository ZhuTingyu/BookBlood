package com.cpigeon.book.module.breeding.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;

import java.util.List;

/**
 * 配对信息列表
 * Created by Administrator on 2018/9/11.
 */

public class PairingInfoListViewModel extends BaseViewModel {

    public BreedPigeonEntity mBreedPigeonEntity;

    public int pi = 1;
    public int ps = 15;

    public MutableLiveData<List<PairingInfoEntity>> mPairingInfoListData = new MutableLiveData<>();

    //获取  配对信息列表
    public void getTXGP_PigeonBreed_SelectPigeonAllData() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreed_SelectPigeonAll(String.valueOf(pi),
                String.valueOf(ps),
                mBreedPigeonEntity.getPigeonID(),
                mBreedPigeonEntity.getFootRingID(),
                mBreedPigeonEntity.getPigeonSexID()
        ), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPairingInfoListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


}
