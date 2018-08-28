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


    //  修改支付密码 已知密码  不需要验证码
    public static Observable<ApiResponse<Object>> getRevisePlayPsd(String phoneStr, String imgVerCode, String phoneVerCode, String newPsdStr) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.user_modify_psd_play)
                .addBody("jmm", EncryptionTool.MD5_32(phoneStr))
                .addBody("xmm", imgVerCode)
                .addBody("rxmm", phoneVerCode)
                .addBody("rsxmm", newPsdStr)
                .request();
    }

}
