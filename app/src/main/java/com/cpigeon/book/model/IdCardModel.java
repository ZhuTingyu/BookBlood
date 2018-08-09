package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.LogbookEntity;
import com.cpigeon.book.model.entity.UserIdCardEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/8/9.
 */

public class IdCardModel {
    public static Observable<ApiResponse<String>> setUserIdCard(
            String idCardP
            ,String idCardN
            ,String name
            ,String sex
            ,String nation
            ,String address
            ,String cardNumber
            ,String authority
            ,String inDate

    ) {
        return RequestData.<ApiResponse<String>>build()
                .setToJsonType(new TypeToken<ApiResponse<String>>() {
                }.getType())
                .url(R.string.set_user_id_card)
                .addImageFileBody("sfzzm", idCardP)
                .addImageFileBody("sfzbm", idCardN)
                .addBody("xm", name)
                .addBody("xb", sex)
                .addBody("minzu", nation)
                .addBody("zhuzhi", address)
                .addBody("haoma", cardNumber)
                .addBody("qianfajiguan", authority)
                .addBody("youxiaoqi", inDate)
                .request();
    }

    public static Observable<ApiResponse<UserIdCardEntity>> getUserIdCard(){
        return RequestData.<ApiResponse<UserIdCardEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<UserIdCardEntity>>() {
                }.getType())
                .url(R.string.get_user_id_card)
                .request();
    }
}
