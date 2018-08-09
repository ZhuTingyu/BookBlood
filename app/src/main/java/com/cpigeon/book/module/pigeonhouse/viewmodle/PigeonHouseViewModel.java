package com.cpigeon.book.module.pigeonhouse.viewmodle;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.LoginModel;
import com.cpigeon.book.model.PigeonHouseModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonHouseEntity;

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
    public String mCounty;
    public String mCity;
    public String mProvince;
    public String mPigeonISOCID;
    public String mPigeonHomeAdds;
    public String mPigeonMatchNum;
    public String mHeadUrl;

    public MutableLiveData<String> addR = new MutableLiveData<>();
    public MutableLiveData<String> modifyR = new MutableLiveData<>();
    public MutableLiveData<String> oneStartHintStr = new MutableLiveData<>();
    public MutableLiveData<PigeonHouseEntity> mHouseEntityInfo = new MutableLiveData<>();
    public MutableLiveData<String> setHeadUrlR = new MutableLiveData<>();


    public void getPigeonHouse(){
        submitRequestThrowError(PigeonHouseModel.getPigeonHouse(),r -> {
            if(r.isOk()){
                mHouseEntityInfo.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }

    public void setUserFace(){
        submitRequestThrowError(UserModel.setUserFace(mHeadUrl), r -> {
            if(r.isOk()){
                UserModel.getInstance().setUserHeadUrl(r.data.touxiangurl);
            }else throw new HttpErrorException(r);
        });
    }

    public void addPigeonHouse(){
        submitRequestThrowError(PigeonHouseModel.addPigeonHouse(mPigeonHomeName,mUsePigeonHomeNum,mPigeonHomePhone
                ,mLatitude,mLongitude,mProvince, mCounty, mCity,mPigeonISOCID,mPigeonHomeAdds,mPigeonMatchNum),r -> {
            if(r.isOk()){
                addR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }

    public void modifyPigeonHouse(){
        submitRequestThrowError(PigeonHouseModel.modifyPigeonHouse(mPigeonHomeName,mUsePigeonHomeNum,mPigeonHomePhone
                ,mLatitude,mLongitude,mProvince, mCounty, mCity,mPigeonISOCID,mPigeonHomeAdds,mPigeonMatchNum),r -> {
            if(r.isOk()){
                modifyR.setValue(r.msg);
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
            isCanCommit(mPigeonHomePhone, mLatitude, mPigeonMatchNum);
        };
    }

    public Consumer<String> setUsePigeonHomeNum() {
        return s -> {
            mUsePigeonHomeNum = s;
            isCanCommit(mPigeonHomePhone, mLatitude, mPigeonMatchNum);
        };
    }

    public Consumer<String> setPigeonHomePhone() {
        return s -> {
            mPigeonHomePhone = s;
            isCanCommit(mPigeonHomePhone, mLatitude, mPigeonMatchNum);
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
            isCanCommit(mPigeonHomePhone, mLatitude, mPigeonMatchNum);
        };
    }

    public Consumer<String> setPigeonHomeAdds() {
        return s -> {
            mPigeonHomeAdds = s;
            isCanCommit(mPigeonHomePhone, mLatitude, mPigeonMatchNum);
        };
    }

    public Consumer<String> setPigeonMatchNum() {
        return s -> {
            mPigeonMatchNum = s;
            isCanCommit(mPigeonHomePhone, mLatitude, mPigeonMatchNum);
        };
    }
}
