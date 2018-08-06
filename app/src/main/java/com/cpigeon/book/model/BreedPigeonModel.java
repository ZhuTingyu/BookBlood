package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonModel {


    //hl 得到足环的类型
    public static Observable<ApiResponse<Object>> getTXGP_FootRingType_Select() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.foot_type_select)
                .request();
    }

    //hl 添加足环（单个）
    public static Observable<ApiResponse<Object>> getTXGP_FootRing_Add(String foot, String money, String ringNum, String footType, String footState, String footSource, String remark) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.foot_add_single)
                .addBody("FootRingNum", foot)//足环号码
                .addBody("FootRingMoney", money)//足环金额
                .addBody("UseFootRingNum", ringNum)// * 挂环次数
                .addBody("TypeID", footType)// 足环类型
                .addBody("StateID", footState)// 足环状态
                .addBody("SourceID", footSource)// 足环来源
                .addBody("Reamrk", remark)// 备注
                .request();
    }

    //hl 删除足环（单个）
    public static Observable<ApiResponse<Object>> getTXGP_FootRing_Delete(String delFootId) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.foot_del_single)
                .addBody("FootRingID", delFootId)//足环id
                .request();
    }
}
