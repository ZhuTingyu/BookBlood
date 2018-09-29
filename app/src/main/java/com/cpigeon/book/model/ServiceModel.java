package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.ServiceInfoEntity;
import com.google.gson.reflect.TypeToken;

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


    public static Observable<ApiResponse> payServiceOrder(String serviceId, String payWay) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.pay_service_order)
                .addBody("sid", serviceId)
                .addBody("uc", "android")
                .addBody("pm", payWay)
                .request();
    }
    public static Observable<ApiResponse> renewalServiceOrder(String serviceId, String payWay) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.pay_renewal_service_order)
                .addBody("sid", serviceId)
                .addBody("uc", "android")
                .addBody("pm", payWay)
                .request();
    }

}
