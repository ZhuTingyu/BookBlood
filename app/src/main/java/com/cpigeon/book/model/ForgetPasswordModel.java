package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ForgetPasswordModel {
    //找回密码
    public static Observable<ApiResponse> retrievePassword(String u, String p, String y) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.user_forget_psd)
                .addBody("sjhm", u)//手机号码
                .addBody("p", p) //登录密码，使用32位MD5加密
                .addBody("y", y)//手机验证码
                .request();
    }

}
