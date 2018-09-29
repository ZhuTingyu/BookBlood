package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.AccountBalanceListEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/24.
 */

public class BalanceModel {

    //hl 充值明细 列表
    public static Observable<ApiResponse<List<AccountBalanceListEntity>>> getAccountBalanceList(int pi, int ps) {
        return RequestData.<ApiResponse<List<AccountBalanceListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<AccountBalanceListEntity>>>() {
                }.getType())
                .url(R.string.account_balance_details)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }
}
