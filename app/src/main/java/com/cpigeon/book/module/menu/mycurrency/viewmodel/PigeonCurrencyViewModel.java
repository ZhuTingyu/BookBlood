package com.cpigeon.book.module.menu.mycurrency.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PigeonCurrencyModel;
import com.cpigeon.book.model.entity.PigeonCurrencyEntity;

import java.util.List;

/**
 * 鸽币
 * Created by Administrator on 2018/9/13.
 */

public class PigeonCurrencyViewModel extends BaseViewModel {

    public int pi = 1;
    public int ps = 20;

    //鸽币明细
    public MutableLiveData<List<PigeonCurrencyEntity>> mPigeonCurrencyDetailsData = new MutableLiveData<>();
    //鸽币兑换回调
    public MutableLiveData<List<PigeonCurrencyEntity>> mPigeonCurrencyExchangeData = new MutableLiveData<>();
    //我的鸽币
    public MutableLiveData<PigeonCurrencyEntity> mGeBi = new MutableLiveData<>();

    //获取  鸽币明细  列表
    public void getTXGP_GeBi_ListData() {
        submitRequestThrowError(PigeonCurrencyModel.getTXGP_GeBi_List(pi, ps), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPigeonCurrencyDetailsData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //我的鸽币
    public void getTXGP_Account_GeBi() {
        submitRequestThrowError(PigeonCurrencyModel.getTXGP_Account_GeBi(), r -> {
            if (r.isOk()) {
                mGeBi.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    public int ecchangePi = 1;
    public int ecchangePs = 20;

    //获取  鸽币兑换  列表
    public void getTXGP_GeBi_DuiHuan_ListData() {
        submitRequestThrowError(PigeonCurrencyModel.getTXGP_GeBi_DuiHuan_List(ecchangePi, ecchangePs), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPigeonCurrencyExchangeData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
