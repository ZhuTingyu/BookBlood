package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.AssEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/11/13.
 */

public class AuthCodeModel {
    public static Observable<ApiResponse<Code>> getAuthCode(String phoneNumber, String type) {
        return RequestData.<ApiResponse<Code>>build()
                .setToJsonType(new TypeToken<ApiResponse<Code>>() {
                }.getType())
                .url(R.string.get_auth_code)
                .addBody("sjhm", phoneNumber)
                .addBody("t", type)
                .request();
    }

    public static class Code{
        public String code;
    }
}
