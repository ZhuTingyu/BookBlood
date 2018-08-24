package com.cpigeon.book.module.menu.feedback.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.FeedbackModel;
import com.cpigeon.book.model.entity.FeedbackDetailEntity;

import java.util.List;

/**
 * 意见反馈
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackDetailViewModel extends BaseViewModel {


    String id;

    public MutableLiveData<List<FeedbackDetailEntity>> mFeedbackDetaisLiveData = new MutableLiveData<>();

    public FeedbackDetailViewModel(Activity activity){
        id = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }


    //获取  意见反馈详情
    public void getFeedbackDetail() {
        submitRequestThrowError(FeedbackModel.getZGW_Users_Feedback_Detail(id), r -> {
            if (r.isOk()) {
                mFeedbackDetaisLiveData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }
}
