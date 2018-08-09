package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PigeonFriendMsgListEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/9.
 */

public class PigeonFriendMsgModel {


    //hl 鸽友消息
    public static Observable<ApiResponse<List<PigeonFriendMsgListEntity>>> getTXGP_GetMsgList(int pi, int ps) {
        return RequestData.<ApiResponse<List<PigeonFriendMsgListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonFriendMsgListEntity>>>() {
                }.getType())
                .url(R.string.pigeon_friend_msg)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }
}
