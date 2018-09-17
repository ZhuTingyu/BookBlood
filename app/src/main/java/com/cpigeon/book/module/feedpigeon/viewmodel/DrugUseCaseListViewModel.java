package com.cpigeon.book.module.feedpigeon.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.DrugUseCaseModel;
import com.cpigeon.book.model.entity.DrugUseCaseEntity;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class DrugUseCaseListViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;

    public int pi = 1;
    public int ps = 15;

    public MutableLiveData<List<DrugUseCaseEntity>> mDrugUseCaseData = new MutableLiveData<>();

    //获取  病情记录  列表
    public void getTXGP_PigeonDisease_SelectAllData() {
        submitRequestThrowError(DrugUseCaseModel.getTXGP_PigeonDisease_SelectAll(String.valueOf(pi),
                String.valueOf(ps),
                mPigeonEntity.getFootRingID(),
                mPigeonEntity.getPigeonID()), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mDrugUseCaseData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
