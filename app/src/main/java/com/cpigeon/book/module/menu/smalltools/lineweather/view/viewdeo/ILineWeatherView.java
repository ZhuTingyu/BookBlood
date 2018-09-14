package com.cpigeon.book.module.menu.smalltools.lineweather.view.viewdeo;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.AssociationListEntity;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.GetGongPengListEntity;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.GetSiFangDiEntity;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.UllageToolEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ILineWeatherView {

    //获取公棚坐标信息
    public static Observable<ApiResponse<List<GetGongPengListEntity>>> getTool_GetGongPengInfoData(String str) {
        return RequestData.<ApiResponse<List<GetGongPengListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<GetGongPengListEntity>>>() {
                }.getType())
                .url(R.string.api_tool_get_gong_peng_info)

                .addBody("s", str)
                .request();
    }

    //获取协会信息
    public static Observable<ApiResponse<List<AssociationListEntity>>> getAssociationInfoData(String str, String p, String c) {
        return RequestData.<ApiResponse<List<AssociationListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<AssociationListEntity>>>() {
                }.getType())
                .url(R.string.api_XH_GetUsersList)

                .addBody("skey", str)
                .addBody("c", c)
                .addBody("p", p)
                .request();
    }


    //获取公棚坐标信息
    public static Observable<ApiResponse<List<GetSiFangDiEntity>>> getTool_GetSiFangDiData(String str) {
        return RequestData.<ApiResponse<List<GetSiFangDiEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<GetSiFangDiEntity>>>() {
                }.getType())
                .url(R.string.api_tool_get_sfd_info)
                .addBody("s", str)
                .request();
    }

    //获取公棚坐标信息
    public static Observable<ApiResponse<UllageToolEntity>> getKongju(Map<String, String> body) {
        return RequestData.<ApiResponse<UllageToolEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<UllageToolEntity>>() {
                }.getType())
                .url(R.string.api_get_kongju)
                .addList(body)
                .request();
    }

}
