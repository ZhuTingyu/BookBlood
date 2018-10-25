package com.cpigeon.book.module.menu.smalltools.shootvideo.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.ShootModel;
import com.cpigeon.book.model.entity.ShootInfoEntity;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class ShootViewModel extends BaseViewModel {

    //我的鸽币
    public MutableLiveData<ShootInfoEntity> mShootInfo = new MutableLiveData<>();
    public ShootInfoEntity mShootInfoEntity = new ShootInfoEntity.Builder().build();
    private boolean isOneTag = true;//是否第一次请求

    //  设置用户头像和鸽舍名称
    public void getTXGP_SetTouXiangGeSheMingChengData() {

        if (StringUtil.isStringValid(mShootInfoEntity.getImgurl())) {
            if (mShootInfoEntity.getImgurl().contains("http:")) {
                mShootInfoEntity.setImgurl("");
            }
        }

        submitRequestThrowError(ShootModel.getTXGP_SetTouXiangGeSheMingCheng(mShootInfoEntity.getImgurl(), mShootInfoEntity.getSszz()), r -> {
            if (r.isOk()) {
                hintDialog(r.msg);
                getTXGP_GetTouXiangGeSheMingChengData();
            } else throw new HttpErrorException(r);
        });
    }

    // 获取用户头像和鸽舍名称
    public void getTXGP_GetTouXiangGeSheMingChengData() {
        submitRequestThrowError(ShootModel.getTXGP_GetTouXiangGeSheMingCheng(), r -> {
            if (r.isOk()) {
                mShootInfo.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
