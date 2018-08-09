package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.FeedbackModel;
import com.cpigeon.book.model.entity.FeedbackListEntity;

import java.util.List;

/**
 * 意见反馈
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackViewModel extends BaseViewModel {


    public int pi = 1;
    public int ps = 5;

    public MutableLiveData<List<FeedbackListEntity>> feedbackListData = new MutableLiveData<>();

    //获取  意见反馈列表
    public void getZGW_Users_Feedback_ListData() {
        submitRequestThrowError(FeedbackModel.getZGW_Users_Feedback_List(pi, ps), r -> {
            if (r.isOk()) {
                feedbackListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }



    //获取  意见反馈详情
    public void getZGW_Users_Feedback_DetailData() {
        submitRequestThrowError(FeedbackModel.getZGW_Users_Feedback_Detail(pi, ps), r -> {
            if (r.isOk()) {
//                FeedbackListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  意见反馈 提交
    public void getZGW_Users_Feedback_AddData() {
        submitRequestThrowError(FeedbackModel.getZGW_Users_Feedback_Add(pi, ps), r -> {
            if (r.isOk()) {
//                FeedbackListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
