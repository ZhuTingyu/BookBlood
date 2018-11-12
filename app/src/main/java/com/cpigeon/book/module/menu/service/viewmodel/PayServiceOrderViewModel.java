package com.cpigeon.book.module.menu.service.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.ServiceModel;
import com.cpigeon.book.model.entity.OrderEntity;
import com.cpigeon.book.util.SendWX;

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
    public String mOrderId;

    public MutableLiveData<OrderEntity> mDataWXOrder = new MutableLiveData<>();

    public void payOder() {
        submitRequestThrowError(ServiceModel.payServiceOrder(mServiceId, mPayWay, mPassword), apiResponse -> {
            if (apiResponse.isOk()) {
                normalResult.setValue(apiResponse.msg);
                mDataWXOrder.setValue(apiResponse.data);
            } else throw new HttpErrorException(apiResponse);
        });
    }

    public void renewalPayOder() {
        submitRequestThrowError(ServiceModel.renewalServiceOrder(mServiceId, mPayWay, mPassword), apiResponse -> {
            if (apiResponse.isOk()) {
                normalResult.setValue(apiResponse.msg);
                mDataWXOrder.setValue(apiResponse.data);
            } else throw new HttpErrorException(apiResponse);
        });
    }

    public void getWXOrder(){
        submitRequestThrowError(ServiceModel.getWXOrder(mOrderId), r -> {
            if(r.isOk()){
                SendWX sendWX = new SendWX(getActivity());
                sendWX.payWeiXin(r.data.getPayReq());
            }
        });
    }

}
