package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PigeonFriendMsgModel;
import com.cpigeon.book.model.entity.PigeonFriendMsgListEntity;

import java.util.List;

/**
 * 鸽友消息
 * Created by Administrator on 2018/8/9.
 */

public class PigeonFriendMsgViewModel extends BaseViewModel {


    public int pi = 1;
    public int ps = 5;

    public MutableLiveData<List<PigeonFriendMsgListEntity>> pigeonFriendMsgListData = new MutableLiveData<>();

    //获取  鸽友消息
    public void getTXGP_GetMsgListData() {
        submitRequestThrowError(PigeonFriendMsgModel.getTXGP_GetMsgList(pi, ps), r -> {
            if (r.isOk()) {
                pigeonFriendMsgListData.setValue(null);
            } else throw new HttpErrorException(r);
        });
    }

}
