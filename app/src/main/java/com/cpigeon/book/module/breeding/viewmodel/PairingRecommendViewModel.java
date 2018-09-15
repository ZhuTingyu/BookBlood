package com.cpigeon.book.module.breeding.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PriringRecommendEntity;

import java.util.List;

/**
 * 推荐配对
 * Created by Administrator on 2018/9/14 0014.
 */

public class PairingRecommendViewModel extends BaseViewModel {


    public PigeonEntity mBreedPigeonEntity;

    public int pi = 1;
    public int ps = 15;
    public String sex;
    public String blood;
    public String pigeonid;

    public MutableLiveData<List<PriringRecommendEntity>> mPriringRecommendData = new MutableLiveData<>();

    //获取  信鸽血统推荐
    public void getTXGP_PigeonBreed_RecomBloodData() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreed_RecomBlood(String.valueOf(pi),
                String.valueOf(ps), sex, pigeonid, blood), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPriringRecommendData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  信鸽赛绩推荐
    public void getTXGP_PigeonBreed_RecomMatchData() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonBreed_RecomMatch(String.valueOf(pi),
                String.valueOf(ps), sex, pigeonid), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPriringRecommendData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  信鸽评分推荐
    public void getTXGP_PigeonTrain_RecomSorceData() {
        submitRequestThrowError(PairingModel.getTXGP_PigeonTrain_RecomSorce(String.valueOf(pi),
                String.valueOf(ps), sex, pigeonid), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPriringRecommendData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


}
