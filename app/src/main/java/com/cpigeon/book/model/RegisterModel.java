package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/8/2.
 */

public class RegisterModel {
    //用户注册
    public static Observable<ApiResponse> userRegister(String phone, String password, String authCode, String inviteCode) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.user_register)
                .addBody("u", phone)//手机号码
                .addBody("p", password) //登录密码，使用32位MD5加密
                .addBody("y", authCode)//手机验证码
                .addBody("yqm", inviteCode)//邀请码
                .request();
    }
}
