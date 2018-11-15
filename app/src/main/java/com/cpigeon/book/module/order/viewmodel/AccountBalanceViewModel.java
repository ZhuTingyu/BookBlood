package com.cpigeon.book.module.order.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BalanceModel;
import com.cpigeon.book.model.entity.BalanceEntity;

/**
 * Created by Zhu TingYu on 2018/11/15.
 */

public class AccountBalanceViewModel extends BaseViewModel {

    public MutableLiveData<BalanceEntity> mDataBalance = new MutableLiveData<>();

    public void getBalance(){
        submitRequestThrowError(BalanceModel.getBalance(), r -> {
            if(r.isOk()){
                mDataBalance.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
