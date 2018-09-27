package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.UpdateInfo;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/27 0027.
 */

public class UpdateModel {

    //hl 获取更新数据
    public static Observable<ApiResponse<List<UpdateInfo>>> getUpdate_Info(String  id) {
        return RequestData.<ApiResponse<List<UpdateInfo>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<UpdateInfo>>>() {
                }.getType())
                .url(R.string.update_info)
                .addBody("id",id)
                .request();
    }


}
