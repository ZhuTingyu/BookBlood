package com.cpigeon.book.module.launch.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;

/**
 * Created by Administrator on 2018/8/3.
 */

public class LaunchViewModel extends BaseViewModel {


    public MutableLiveData<String> launchImgStr = new MutableLiveData<>();

    //启动页面图
    public void getLaunchImg() {
        submitRequestThrowError(LaunchModel.getLaunchData(), r -> {
            if (r.isOk()) {
                launchImgStr.setValue(r.data.getAdImageUrl());
            }
        });
    }
}
