package com.cpigeon.book.module.login.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.ApiResponse;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.LoginModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.UserEntity;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/8/2.
 */

public class LoginViewModel extends BaseViewModel {
    String mPhone;
    String mPassword;

    public MutableLiveData<UserEntity> loginR = new MutableLiveData<>();
    public MutableLiveData<String> head = new MutableLiveData<>();
    public MutableLiveData<String> oneStartHintStr = new MutableLiveData<>();

    public void login() {
        submitRequestThrowError(LoginModel.login(mPhone, mPassword), r -> {
            if (r.isOk()) {
                UserModel.getInstance().setUserInfo(r.data, mPassword);
                loginR.setValue(r.data);
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    public Consumer<String> setPhone() {
        return s -> {
            this.mPhone = s;
            getUserHeadImg();
        };
    }

    public Consumer<String> setPassword() {
        return s -> {
            this.mPassword = s;
        };
    }

    //获取用户头像
    public void getUserHeadImg() {
        submitRequestThrowError(LoginModel.getUserHeadImgData(mPhone), r -> {
            if (r.isOk()) {
                head.setValue(r.data.touxiangurl);
            } else throw new HttpErrorException(r);
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

}
