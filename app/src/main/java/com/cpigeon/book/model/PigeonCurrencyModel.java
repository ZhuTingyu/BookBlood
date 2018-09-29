package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.CurrencyExchangeEntity;
import com.cpigeon.book.model.entity.PigeonCurrencyEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/13.
 */

public class PigeonCurrencyModel {


    //hl 鸽币明细  列表
    public static Observable<ApiResponse<List<PigeonCurrencyEntity>>> getTXGP_GeBi_List(int pi, int ps) {
        return RequestData.<ApiResponse<List<PigeonCurrencyEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonCurrencyEntity>>>() {
                }.getType())
                .url(R.string.pigeon_currency_details_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }


    //hl 获取鸽币
    public static Observable<ApiResponse<PigeonCurrencyEntity>> getTXGP_Account_GeBi() {
        return RequestData.<ApiResponse<PigeonCurrencyEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonCurrencyEntity>>() {
                }.getType())
                .url(R.string.get_my_gebi)
                .request();
    }

    //hl 鸽币兑换  列表
    public static Observable<ApiResponse<List<CurrencyExchangeEntity>>> getTXGP_GeBi_DuiHuan_List(int pi, int ps) {
        return RequestData.<ApiResponse<List<CurrencyExchangeEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<CurrencyExchangeEntity>>>() {
                }.getType())
                .url(R.string.pigeon_currency_exchange_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }

}
