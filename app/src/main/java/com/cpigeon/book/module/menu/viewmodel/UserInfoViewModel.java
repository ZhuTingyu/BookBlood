package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.SignModel;
import com.cpigeon.book.model.UserInfoModel;
import com.cpigeon.book.model.entity.SignRuleListEntity;
import com.cpigeon.book.model.entity.UserInfoEntity;
import com.tencent.connect.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class UserInfoViewModel extends BaseViewModel {

    public MutableLiveData<UserInfoEntity> mUserInfoData = new MutableLiveData<>();

    //获取用户服务、帐户余额、鸽币
    public void getTXGP_GetUserInfoData() {
        submitRequestThrowError(UserInfoModel.getTXGP_GetUserInfo(), r -> {
            if (r.isOk()) {
                mUserInfoData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }
}
