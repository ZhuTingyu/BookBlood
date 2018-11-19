package com.cpigeon.book.module.basepigeon;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.regex.RegexUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.AuthCodeModel;

import java.net.HttpRetryException;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/11/13.
 */

public class AuthCodeViewModel extends BaseViewModel {
    public static final String TYPE_REGISTER = "注册会员";
    public static final String TYPE_FIND_PASSWORD = "找回密码";
    public static final String TYPE_PAY_PASSWORD = "支付密码";

    public String mPhoneNumber;
    public String mType;

    public MutableLiveData<AuthCodeModel.Code> mDataCode = new MutableLiveData<>();

    public void getAuthCode(){

        if(!RegexUtils.isMobileExact(mPhoneNumber)){
            error(R.string.text_input_right_phone);
            return;
        }

        submitRequestThrowError(AuthCodeModel.getAuthCode(mPhoneNumber, mType), r -> {
            if(r.isOk()){
                mDataCode.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }

    public Consumer<String> setPhoneNumber(){
        return s -> {
            mPhoneNumber = s;
        };
    }
}
