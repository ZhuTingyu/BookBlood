package com.cpigeon.book.module.home.home.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.cpigeon.book.model.AdModel;
import com.cpigeon.book.model.entity.HomeAdEntity;

/**
 * Created by Zhu TingYu on 2018/8/22.
 */

public class HomeViewModel extends BaseViewModel{

    public MutableLiveData<HomeAdEntity> mLdHomeAd = new MutableLiveData<>();

    public void getHomeAd(){
        submitRequestThrowError(AdModel.getHomeAd(),r -> {
            if(r.isOk()){
                mLdHomeAd.setValue(r.data);
            }
        });
    }
}
