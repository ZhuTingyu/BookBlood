package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.FeedbackModel;
import com.cpigeon.book.model.entity.FeedbackListEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/23.
 */

public class FeedbackListViewModel extends BaseViewModel {
    public int pi = 1;
    public int ps = 15;
    public MutableLiveData<List<FeedbackListEntity>> feedbackListData = new MutableLiveData<>();

    //获取  意见反馈列表
    public void getFeedbackList() {
        submitRequestThrowError(FeedbackModel.getZGW_Users_Feedback_List(pi, ps), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                feedbackListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }
}
