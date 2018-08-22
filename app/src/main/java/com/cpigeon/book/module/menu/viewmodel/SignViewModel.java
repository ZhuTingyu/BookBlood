package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.SignModel;
import com.cpigeon.book.model.entity.SignInfoEntity;
import com.cpigeon.book.model.entity.SignRuleListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class SignViewModel extends BaseViewModel {


    public MutableLiveData<List<SignRuleListEntity>> pigeonFriendMsgListData = new MutableLiveData<>();

    //签到信息
    public MutableLiveData<SignInfoEntity> mSignInfoEntity = new MutableLiveData<>();

    //获取  签到规则
    public void getZGW_Users_SignGuiZeData() {
        submitRequestThrowError(SignModel.getZGW_Users_SignGuiZe(), r -> {
            if (r.isOk()) {
            } else throw new HttpErrorException(r);
        });
    }


    //获取  签到信息
    public void getUserSignInfoData() {
        submitRequestThrowError(SignModel.getUserSignInfo(), r -> {
            if (r.isOk()) {
                mSignInfoEntity.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  点击签到
    public void getZGW_Users_SignIn() {
        submitRequestThrowError(SignModel.getZGW_Users_SignIn(), r -> {
            if (r.isOk()) {
            } else throw new HttpErrorException(r);
        });
    }


}
