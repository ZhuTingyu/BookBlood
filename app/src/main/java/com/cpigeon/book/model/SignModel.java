package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.SignInfoEntity;
import com.cpigeon.book.model.entity.SignRuleListEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/9.
 */

public class SignModel {


    //hl 签到规则
    public static Observable<ApiResponse<List<SignRuleListEntity>>> getZGW_Users_SignGuiZe() {
        return RequestData.<ApiResponse<List<SignRuleListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<SignRuleListEntity>>>() {
                }.getType())
                .url(R.string.sign_rule)
                .request();
    }

    //hl 签到信息
    public static Observable<ApiResponse<SignInfoEntity>> getUserSignInfo() {
        return RequestData.<ApiResponse<SignInfoEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<SignInfoEntity>>() {
                }.getType())
                .url(R.string.sign_info)
                .request();
    }


    //hl 点击签到
    public static Observable<ApiResponse<List<SignRuleListEntity>>> getZGW_Users_SignIn() {
        return RequestData.<ApiResponse<List<SignRuleListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<SignRuleListEntity>>>() {
                }.getType())
                .url(R.string.sign_click)
                .request();
    }

}
