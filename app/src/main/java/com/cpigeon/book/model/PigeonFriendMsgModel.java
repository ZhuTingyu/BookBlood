package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.MsgCountEntity;
import com.cpigeon.book.model.entity.PigeonFriendMsgListEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/9.
 */

public class PigeonFriendMsgModel {


    //hl 鸽友消息  列表
    public static Observable<ApiResponse<List<PigeonFriendMsgListEntity>>> getTXGP_GetMsgList(int pi, int ps) {
        return RequestData.<ApiResponse<List<PigeonFriendMsgListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonFriendMsgListEntity>>>() {
                }.getType())
                .url(R.string.pigeon_friend_msg)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }


    //hl 鸽友消息详情
    public static Observable<ApiResponse<PigeonFriendMsgListEntity>> getTXGP_Msg_Detail(String id) {
        return RequestData.<ApiResponse<PigeonFriendMsgListEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonFriendMsgListEntity>>() {
                }.getType())
                .url(R.string.pigeon_friend_msg_detail)
                .addBody("id", id)
                .request();
    }


    //hl 鸽友消息 统计（未读消息）
    public static Observable<ApiResponse<MsgCountEntity>> getTXGP_Msg_Count() {
        return RequestData.<ApiResponse<MsgCountEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<MsgCountEntity>>() {
                }.getType())
                .url(R.string.pigeon_friend_msg_count)
                .request();
    }

}
