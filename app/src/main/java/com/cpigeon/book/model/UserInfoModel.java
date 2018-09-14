package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.SignRuleListEntity;
import com.cpigeon.book.model.entity.UserInfoEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class UserInfoModel {

    //hl 获取用户服务、帐户余额、鸽币
    public static Observable<ApiResponse<UserInfoEntity>> getTXGP_GetUserInfo() {
        return RequestData.<ApiResponse<UserInfoEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<UserInfoEntity>>() {
                }.getType())
                .url(R.string.get_user_info)
                .request();
    }
}
