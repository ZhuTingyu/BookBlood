package com.cpigeon.book.http;

import com.base.http.RequestUtil;
import com.base.util.EncryptionTool;
import com.base.util.Utils;
import com.base.util.utility.LogUtil;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;

/**
 * Created by Administrator on 2018/8/2.
 */

public class RequestData<T> extends RequestUtil {
    public static <T> RequestUtil<T> build() {
        RequestUtil<T> request = RequestUtil.builder();
        request.addHead("u", EncryptionTool.encryptAES(getRequestHead()));
        request.setBaseUrl(MyApp.getAppContext().getString(R.string.baseUrl));
        request.headUrl(MyApp.getAppContext().getString(R.string.api_head));
        request.setUserId(UserModel.getInstance().getUserId());
        request.setSignString(Utils.getString(R.string.keySign));
        LogUtil.print("xiao-->" + getRequestHead());
        LogUtil.print("xiaohl-->" + EncryptionTool.encryptAES(getRequestHead()));
        return request;
    }

    private static String getRequestHead() {
        return UserModel.getInstance().getUserId() + "|" + UserModel.getInstance().getUserToken() + "|" + System.currentTimeMillis() / 1000;
    }
}
