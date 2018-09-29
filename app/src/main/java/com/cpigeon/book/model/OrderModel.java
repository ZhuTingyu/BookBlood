package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.OrderEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/29 0029.
 */

public class OrderModel {

    //hl 我的订单 列表
    public static Observable<ApiResponse<List<OrderEntity>>> getTXGP_GetOrderList(int pi, int ps) {
        return RequestData.<ApiResponse<List<OrderEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<OrderEntity>>>() {
                }.getType())
                .url(R.string.my_order_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }
}
