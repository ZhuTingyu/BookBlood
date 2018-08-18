package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.FeedbackListEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * 意见反馈
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackModel {

    //hl 意见反馈列表
    public static Observable<ApiResponse<List<FeedbackListEntity>>> getZGW_Users_Feedback_List(int pi, int ps) {
        return RequestData.<ApiResponse<List<FeedbackListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<FeedbackListEntity>>>() {
                }.getType())
                .url(R.string.list_feedback)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }


    //hl 意见反馈详情
    public static Observable<ApiResponse<List<FeedbackListEntity>>> getZGW_Users_Feedback_Detail(int pi, int ps) {
        return RequestData.<ApiResponse<List<FeedbackListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<FeedbackListEntity>>>() {
                }.getType())
                .url(R.string.detail_feedback)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }


    //hl 意见反馈添加提交
    public static Observable<ApiResponse<List<FeedbackListEntity>>> getZGW_Users_Feedback_Add(String content, String contact) {
        return RequestData.<ApiResponse<List<FeedbackListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<FeedbackListEntity>>>() {
                }.getType())
                .url(R.string.submit_feedback)
                .addBody("content", content)
                .addBody("contact", contact)
                .request();
    }

}
