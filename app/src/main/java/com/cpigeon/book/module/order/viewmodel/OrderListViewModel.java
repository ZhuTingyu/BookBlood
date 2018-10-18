package com.cpigeon.book.module.order.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.OrderModel;
import com.cpigeon.book.model.entity.OrderEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/29 0029.
 */

public class OrderListViewModel extends BaseViewModel {

    public int pi = 1;
    public int ps = 50;
    public String lx  = "all";

    public MutableLiveData<List<OrderEntity>> mOrderListData = new MutableLiveData<>();

    //获取  充值明细列表
    public void getTXGP_GetOrderListData() {
        submitRequestThrowError(OrderModel.getTXGP_GetOrderList(lx,pi, ps), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mOrderListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


}
