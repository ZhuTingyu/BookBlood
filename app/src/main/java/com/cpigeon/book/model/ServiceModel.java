package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.base.util.EncryptionTool;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.OrderEntity;
import com.cpigeon.book.model.entity.ServiceInfoEntity;
import com.cpigeon.book.model.entity.WeiXinPayEntity;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.util.MD5;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/9/29.
 */

public class ServiceModel {
    public static Observable<ApiResponse<ServiceInfoEntity>> getServiceInfo() {
        return RequestData.<ApiResponse<ServiceInfoEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<ServiceInfoEntity>>() {
                }.getType())
                .url(R.string.get_service_info)
                .request();
    }


    public static Observable<ApiResponse<OrderEntity>> payServiceOrder(String serviceId, String payWay, String password) {
        return RequestData.<ApiResponse<OrderEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<OrderEntity>>() {
                }.getType())
                .url(R.string.pay_service_order)
                .addBody("sid", serviceId)
                .addBody("uc", "android")
                .addBody("pm", payWay)
                .addBody("pp", StringUtil.isStringValid(password) ? EncryptionTool.MD5(password)
                        : StringUtil.emptyString())
                .request();
    }

    public static Observable<ApiResponse<OrderEntity>> renewalServiceOrder(String serviceId, String payWay, String password) {
        return RequestData.<ApiResponse<OrderEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<OrderEntity>>() {
                }.getType())
                .url(R.string.pay_renewal_service_order)
                .addBody("sid", serviceId)
                .addBody("uc", "android")
                .addBody("pm", payWay)
                .addBody("pp", StringUtil.isStringValid(password) ? EncryptionTool.MD5(password)
                        : StringUtil.emptyString())
                .request();
    }

    public static Observable<ApiResponse<WeiXinPayEntity>> getWXOrder(String orderId) {
        return RequestData.<ApiResponse<WeiXinPayEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<WeiXinPayEntity>>() {
                }.getType())
                .url(R.string.get_wx_order)
                .addBody("oid", orderId)
                .request();
    }

}
