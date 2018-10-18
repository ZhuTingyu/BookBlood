package com.cpigeon.book.service;

import android.util.Log;

import com.base.base.BaseViewModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/6.
 */

public class SingleLoginViewModel extends BaseViewModel {

    //hl 单点登录
    public void getSingleLogin(Consumer<String> consumer) {
        submitRequestThrowError(SingleLoginModel.getSingleLoginData().map(r -> {
            if (r.isOk()) {
                //请求成功
                Log.d("xiaohls", "onHandleIntent: 21");
                return "";
            } else if (r.errorCode == 90102) {
                //在别的设备上登录
                Log.d("xiaohls", "onHandleIntent: 22");
                return r.msg;
            } else {
                Log.d("xiaohls", "onHandleIntent: 23");
                return r.msg;
            }
        }), consumer);



    }

}
