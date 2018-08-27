package com.cpigeon.book.module.menu.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.RevisePsdModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/24.
 */

public class RevisePsdViewModel extends BaseViewModel {


    //  修改登录密码 已知密码  不需要验证码
    public void getZGW_Users_GetLoginData1() {
        submitRequestThrowError(RevisePsdModel.getZGW_Users_UpdatePWD(modifyOriginalPsd, modifyNewPsd, modifyNewPsd2), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    //  修改登录密码 需要验证码
    public void getZGW_Users_GetLoginData2() {
        submitRequestThrowError(RevisePsdModel.getReviseLoginPsd(phoneStr, imgVerCode, phoneVerCode, newPsdStr), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    //  修改支付密码 需要验证码
    public void getZGW_Users_GetPlayData() {
        submitRequestThrowError(RevisePsdModel.getRevisePlayPsd(phoneStr, imgVerCode, phoneVerCode, newPsdStr), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    //================================================需要验证码=====================================================================

    String phoneStr;//绑定的手机号
    String imgVerCode;//图片验证码
    String phoneVerCode;//手机验证码
    String newPsdStr;//设置新密码

    public Consumer<String> setPhoneStr() {
        return s -> {
            phoneStr = s;
            isCanCommit2();
        };
    }

    public Consumer<String> setImgVerCode() {
        return s -> {
            imgVerCode = s;
            isCanCommit2();
        };
    }

    public Consumer<String> setPhoneVerCode() {
        return s -> {
            phoneVerCode = s;
            isCanCommit2();
        };
    }

    public Consumer<String> setNewPsdStr() {
        return s -> {
            newPsdStr = s;
            isCanCommit2();
        };
    }

    public void isCanCommit2() {
        isCanCommit(phoneStr, imgVerCode, phoneVerCode, newPsdStr);
    }

    //================================================不需要验证码=====================================================================

    String modifyOriginalPsd;
    String modifyNewPsd;
    String modifyNewPsd2;

    public Consumer<String> setModifyOriginalPsd() {
        return s -> {
            modifyOriginalPsd = s;
            isCanCommit();
        };
    }


    public Consumer<String> setModifyNewPsd() {
        return s -> {
            modifyNewPsd = s;
            isCanCommit();
        };
    }

    public Consumer<String> setModifyNewPsd2() {
        return s -> {
            modifyNewPsd2 = s;
            isCanCommit();
        };
    }

    public void isCanCommit() {
        isCanCommit(modifyOriginalPsd, modifyNewPsd, modifyNewPsd2);
    }


}
