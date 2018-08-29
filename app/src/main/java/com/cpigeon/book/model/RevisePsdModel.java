package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.base.util.EncryptionTool;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/24.
 */

public class RevisePsdModel {


    //  修改密码 已知密码
    public static Observable<ApiResponse<Object>> getZGW_Users_UpdatePWD(String modifyOriginalPsd, String modifyNewPsd, String modifyNewPsd2) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.user_modify_psd_know)
                .addBody("jmm", EncryptionTool.MD5_32(modifyOriginalPsd))
                .addBody("xmm", modifyNewPsd)
                .addBody("rxmm", modifyNewPsd2)
                .request();
    }


    //  修改支付密码 已知密码  不需要验证码
    public static Observable<ApiResponse<Object>> getReviseLoginPsd(String phoneStr, String imgVerCode, String phoneVerCode, String newPsdStr) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.user_modify_psd_need)
                .addBody("jmm", EncryptionTool.MD5_32(phoneStr))
                .addBody("xmm", imgVerCode)
                .addBody("rxmm", phoneVerCode)
                .addBody("rsxmm", newPsdStr)
                .request();
    }


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



    //  修改支付密码 需要验证码
    public static Observable<ApiResponse<Object>> getRevisePlayPsd(String phoneStr,  String phoneVerCode, String newPsdStr) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.user_modify_psd_play)
                .addBody("sjhm", phoneStr)//手机号码
                .addBody("y", phoneVerCode)//手机验证码
                .addBody("p", EncryptionTool.encryptAES(newPsdStr))//支付密码，AES加密
                .request();
    }

}
