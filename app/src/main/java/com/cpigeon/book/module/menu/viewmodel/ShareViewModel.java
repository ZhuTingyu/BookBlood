package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.ShareModel;
import com.cpigeon.book.model.entity.InviteCodeEntity;

/**
 * Created by Administrator on 2018/8/30.
 */

public class ShareViewModel extends BaseViewModel {


    //签到结果
    public MutableLiveData<InviteCodeEntity> mInviteCodeData = new MutableLiveData<>();

    //获取  邀请码
    public void getZGW_Users_SignGuiZeData() {
        submitRequestThrowError(ShareModel.getShareInviteCode(), r -> {
            if (r.isOk()) {
                mInviteCodeData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
