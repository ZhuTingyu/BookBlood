package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.RecycleBinEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class RecycleBinModel {

    //回收站 列表
    public static Observable<ApiResponse<List<RecycleBinEntity>>> getTXGP_RecycleAllData(String pi, String ps) {
        return RequestData.<ApiResponse<List<RecycleBinEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<RecycleBinEntity>>>() {
                }.getType())
                .url(R.string.recycle_bin_list)
                .addBody("pi", pi)//
                .addBody("ps", ps) //
                .request();
    }

    //回收站  清空
    public static Observable<ApiResponse<List<RecycleBinEntity>>> getTXGP_EmptyData() {
        return RequestData.<ApiResponse<List<RecycleBinEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<RecycleBinEntity>>>() {
                }.getType())
                .url(R.string.recycle_bin_empty)
                .request();
    }

    //回收站  恢复
    public static Observable<ApiResponse<List<RecycleBinEntity>>> getTXGP_RecoveryData(String recyid) {
        return RequestData.<ApiResponse<List<RecycleBinEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<RecycleBinEntity>>>() {
                }.getType())
                .url(R.string.recycle_bin_restore)
                .addBody("recyid", recyid)
                .request();
    }
}
