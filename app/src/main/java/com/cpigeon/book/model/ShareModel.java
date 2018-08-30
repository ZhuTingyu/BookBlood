package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.InviteCodeEntity;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/30.
 */

public class ShareModel {

    //hl 获取分享邀请码
    public static Observable<ApiResponse<InviteCodeEntity>> getShareInviteCode() {
        return RequestData.<ApiResponse<InviteCodeEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<InviteCodeEntity>>() {
                }.getType())
                .url(R.string.share_invite_code)
                .request();
    }
}
