package com.cpigeon.book.module.pigeonhouse.viewmodle;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.LoginModel;
import com.cpigeon.book.model.PigeonHouseModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class PigeonHouseViewModel extends BaseViewModel {


    public String mPigeonHomeName;
    public String mUsePigeonHomeNum;
    public String mPigeonHomePhone;
    public String mLatitude;
    public String mLongitude;
    public String mProvince;
    public String mPigeonISOCID;
    public String mPigeonHomeAdds;
    public String mPigeonMatchNum;

    public MutableLiveData<String> addR = new MutableLiveData<>();
    public MutableLiveData<String> modifyR = new MutableLiveData<>();
    public MutableLiveData<String> oneStartHintStr = new MutableLiveData<>();


    public void addPigeonHouse(){
        submitRequestThrowError(PigeonHouseModel.addPigeonHouse(mPigeonHomeName,mUsePigeonHomeNum,mPigeonHomePhone
                ,mLatitude,mLongitude,mProvince,mPigeonISOCID,mPigeonHomeAdds,mPigeonMatchNum),r -> {
            if(r.isOk()){
                addR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }

    //第一次启动 获取鸽币
    public void oneStartGetGeBi() {
        submitRequestThrowError(LoginModel.getUseroneStart(), r -> {
            if (r.isOk()) {
                oneStartHintStr.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    public Consumer<String> setPigeonHomeName() {
        return s -> {
            mPigeonHomeName = s;
        };
    }

    public Consumer<String> setUsePigeonHomeNum() {
        return s -> {
            mUsePigeonHomeNum = s;
        };
    }

    public Consumer<String> setPigeonHomePhone() {
        return s -> {
            mPigeonHomePhone = s;
        };
    }

    public Consumer<String> setLatitude() {
        return s -> {
            mLatitude = s;
        };
    }

    public Consumer<String> setLongitude() {
        return s -> {
            mLongitude = s;
        };
    }

    public Consumer<String> setProvince() {
        return s -> {
            mProvince = s;
        };
    }

    public Consumer<String> setPigeonISOCID() {
        return s -> {
            mPigeonISOCID = s;
        };
    }

    public Consumer<String> setPigeonHomeAdds() {
        return s -> {
            mPigeonHomeAdds = s;
        };
    }

    public Consumer<String> setPigeonMatchNum() {
        return s -> {
            mPigeonMatchNum = s;
        };
    }
}
