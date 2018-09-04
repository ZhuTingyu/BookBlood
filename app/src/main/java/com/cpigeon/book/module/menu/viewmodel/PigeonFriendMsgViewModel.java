package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PigeonFriendMsgModel;
import com.cpigeon.book.model.entity.MsgCountEntity;
import com.cpigeon.book.model.entity.PigeonFriendMsgListEntity;

import java.util.List;

/**
 * 鸽友消息
 * Created by Administrator on 2018/8/9.
 */

public class PigeonFriendMsgViewModel extends BaseViewModel {


    public int pi = 1;
    public int ps = 5;

    public int changePosition;

    public MutableLiveData<List<PigeonFriendMsgListEntity>> pigeonFriendMsgListData = new MutableLiveData<>();
    public MutableLiveData<PigeonFriendMsgListEntity> mPigeonFriendMsgDetail = new MutableLiveData<>();
    public MutableLiveData<MsgCountEntity> mMsgCountData = new MutableLiveData<>();

    //获取  鸽友消息  列表
    public void getTXGP_GetMsgListData() {
        submitRequestThrowError(PigeonFriendMsgModel.getTXGP_GetMsgList(pi, ps), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                pigeonFriendMsgListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  鸽友消息详情
    public void getTXGP_Msg_DetailData(String id) {
        submitRequestThrowError(PigeonFriendMsgModel.getTXGP_Msg_Detail(id), r -> {
            if (r.isOk()) {
                mPigeonFriendMsgDetail.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //获取  鸽友消息 统计（未读消息）
    public void getTXGP_Msg_CountData() {
        submitRequestThrowError(PigeonFriendMsgModel.getTXGP_Msg_Count(), r -> {
            if (r.isOk()) {
                mMsgCountData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
