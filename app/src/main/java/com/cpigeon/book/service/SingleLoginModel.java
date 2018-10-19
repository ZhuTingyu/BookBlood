package com.cpigeon.book.service;

import com.base.http.ApiResponse;
import com.base.util.EncryptionTool;
import com.base.util.Utils;
import com.base.util.utility.PhoneUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.UserModel;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/6.
 */

public class SingleLoginModel {

    //hl 单点登录
    public static Observable<ApiResponse<Object>> getSingleLoginData() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.user_single_login)
                .addBody("u", UserModel.getInstance().getUserData().yonghuming)//用户名，或手机号码
                .addBody("p", EncryptionTool.MD5_32(UserModel.getInstance().getUserData().password))//登录密码，使用32位MD5加密
                .addBody("devid", PhoneUtils.getCombinedDeviceID(Utils.getApp()))//登录设备ID
                .request();
    }


    //hl 单点登录
    public static Observable<ApiResponse<Object>> getSingleLoginData2(String u, String p) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.user_single_login)
                .addBody("u", u)//用户名，或手机号码
                .addBody("p", EncryptionTool.MD5_32(p))//登录密码，使用32位MD5加密
                .addBody("devid", PhoneUtils.getCombinedDeviceID(Utils.getApp()))//登录设备ID
                .request();
    }

}
