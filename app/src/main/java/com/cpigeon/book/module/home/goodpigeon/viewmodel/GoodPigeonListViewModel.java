package com.cpigeon.book.module.home.goodpigeon.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.GoodPigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/27.
 */

public class GoodPigeonListViewModel extends BaseViewModel{
    public int type = 1; // 1 世界铭鸽，2 我的铭鸽 3 申请中铭鸽
    public int pi = 1;
    public String foodNumber;

   public MutableLiveData<List<PigeonEntity>> mDataGoodPigeon = new MutableLiveData<>();

    public void getPigeon(){
        submitRequestThrowError(GoodPigeonModel.getGoodPigeon(type, pi, foodNumber),r -> {
            if(r.isOk()){
                listEmptyMessage.setValue(r.msg);
                mDataGoodPigeon.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }
}
