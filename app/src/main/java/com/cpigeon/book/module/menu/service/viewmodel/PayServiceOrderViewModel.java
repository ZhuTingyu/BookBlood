package com.cpigeon.book.module.menu.service.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.ServiceModel;

/**
 * Created by Zhu TingYu on 2018/9/29.
 */

public class PayServiceOrderViewModel extends BaseViewModel {

    public static final String WAY_SCORE = "gb";
    public static final String WAY_BALANCE = "ye";
    public static final String WAY_WX = "wx";

    public String mServiceId;
    public String mPayWay;
    public String mPassword;

    public void payOder() {
        submitRequestThrowError(ServiceModel.payServiceOrder(mServiceId, mPayWay, mPassword), apiResponse -> {
            if (apiResponse.isOk()) {
                normalResult.setValue(apiResponse.msg);
            } else throw new HttpErrorException(apiResponse);
        });
    }

    public void renewalPayOder() {
        submitRequestThrowError(ServiceModel.renewalServiceOrder(mServiceId, mPayWay, mPassword), apiResponse -> {
            if (apiResponse.isOk()) {
                normalResult.setValue(apiResponse.msg);
            } else throw new HttpErrorException(apiResponse);
        });
    }

}
