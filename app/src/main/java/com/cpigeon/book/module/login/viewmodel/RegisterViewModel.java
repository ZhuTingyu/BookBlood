package com.cpigeon.book.module.login.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.regex.RegexUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.RegisterModel;

import io.reactivex.functions.Consumer;


/**
 * Created by Zhu TingYu on 2018/8/2.
 */

public class RegisterViewModel extends BaseViewModel {

    public String phone;
    public String authCode;
    public String password;
    public String password2;
    public String inviteCode;

    public MutableLiveData<String> registerR = new MutableLiveData<>();

    public void register() {

        if(!RegexUtils.isMobileExact(phone)){
            error(R.string.text_input_right_phone);
            return;
        }

        if(!StringUtil.isStringValid(authCode) || authCode.length() != 4){
            error(R.string.text_input_right_auth_code);
            return;
        }

        if(!StringUtil.isPasswordValid(password)){
            error(R.string.text_input_right_password);
            return;
        }

        if(!password.equals(password2)){
            error(R.string.text_tiwce_password_not_same);
            return;
        }

        submitRequestThrowError(RegisterModel.userRegister(phone, password, authCode, inviteCode), r -> {
            if (r.isOk()) {
                registerR.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    public Consumer<String> setPhone() {
        return s -> {
            this.phone = s;
        };
    }

    public Consumer<String> setAuthCode() {
        return s -> {
            this.authCode = s;
        };
    }

    public Consumer<String> setPassword() {
        return s -> {
            this.password = s;
        };
    }

    public Consumer<String> setPassword2() {
        return s -> {
            this.password2 = s;
        };
    }

    public Consumer<String> setInviteCode() {
        return s -> {
            this.inviteCode = s;
        };
    }

}
