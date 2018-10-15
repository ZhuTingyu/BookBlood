package com.cpigeon.book.module.home.home.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.AdModel;
import com.cpigeon.book.model.CountModel;
import com.cpigeon.book.model.entity.HomeAdEntity;
import com.cpigeon.book.model.entity.HomeTopEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/22.
 */

public class HomeViewModel extends BaseViewModel{

    public MutableLiveData<HomeAdEntity> mLdHomeAd = new MutableLiveData<>();
    public MutableLiveData<List<HomeTopEntity>> mDataHomeTop = new MutableLiveData<>();

    public void getHomeAd(){
        submitRequestThrowError(AdModel.getHomeAd(),r -> {
            if(r.isOk()){
                mLdHomeAd.setValue(r.data);
            }
        });
    }

    public void getHomeTop(){
        submitRequestThrowError(CountModel.getHomeTop(),r -> {
            if(r.isOk()){
                mDataHomeTop.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
