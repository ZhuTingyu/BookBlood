package com.cpigeon.book.module.launch.viewmodel;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.UserModel;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/3.
 */

public class LaunchModel {
    //hl 启动页面图
    public static Observable<ApiResponse<Object>> getLaunchData() {
        return RequestData.<ApiResponse<Object>>build2()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.launch_img)
                .addBody("uidsss", UserModel.getInstance().getUserId())
                .request();
    }

}
