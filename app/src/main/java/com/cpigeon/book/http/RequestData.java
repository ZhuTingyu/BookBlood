package com.cpigeon.book.http;

import com.base.http.RequestUtil;
import com.base.util.EncryptionTool;
import com.base.util.Utils;
import com.base.util.utility.PhoneUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;

/**
 * Created by Administrator on 2018/8/2.
 */

public class RequestData<T> extends RequestUtil {
    public static <T> RequestUtil<T> build() {
        RequestUtil<T> request = RequestUtil.builder();
        request.addHead("auth", EncryptionTool.encryptAES(getRequestHead()));

        request.setBaseUrl(MyApp.getAppContext().getString(R.string.baseUrl));

        request.headUrl(MyApp.getAppContext().getString(R.string.api_head));
        if (StringUtil.isStringValid(UserModel.getInstance().getUserId())) {
            request.addBody("uid", UserModel.getInstance().getUserId());
        }
        request.setSignString(Utils.getString(R.string.keySign));
//        LogUtil.print("请求头加密前-->" + getRequestHead());
//        LogUtil.print("请求头加密后-->" + EncryptionTool.encryptAES(getRequestHead()));
//        LogUtil.print("请求头解密后-->" + EncryptionTool.decryptAES(EncryptionTool.encryptAES(getRequestHead())));
        return request;
    }

    private static String getRequestHead() {
        StringBuilder builder = new StringBuilder();
        builder.append(UserModel.getInstance().getUserId());
        builder.append("|");
        builder.append(UserModel.getInstance().getUserToken());
        builder.append("|");
        builder.append(PhoneUtils.getCombinedDeviceID(Utils.getApp()));
        builder.append("|");
        builder.append(System.currentTimeMillis() / 1000);
       /* return UserModel.getInstance().getUserId() + "|" + UserModel.getInstance().getUserToken() + "|" +
                PhoneUtils.getCombinedDeviceID(Utils.getApp()) + "|" + System.currentTimeMillis() / 1000;*/
        return builder.toString();
    }
}

