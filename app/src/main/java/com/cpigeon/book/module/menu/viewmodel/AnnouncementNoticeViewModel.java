package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.AnnouncementNoticeModel;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;
import com.cpigeon.book.model.entity.MsgCountEntity;

import java.util.List;

/**
 * 公告通知
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeViewModel extends BaseViewModel {


    public int pi = 1;
    public int ps = 15;

    public int changePosition;

    public MutableLiveData<List<AnnouncementNoticeEntity>> announcementNoticeData = new MutableLiveData<>();
    public MutableLiveData<MsgCountEntity> mMsgCountData = new MutableLiveData<>();

    //获取  公告通知列表
    public void getTXGP_GetGongGaoData() {
        submitRequestThrowError(AnnouncementNoticeModel.getTXGP_GetGongGao(pi, ps), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                announcementNoticeData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    //获取  公告通知统计
    public void getTXGP_GongGao_CountData() {
        submitRequestThrowError(AnnouncementNoticeModel.getTXGP_GongGao_Count(), r -> {
            if (r.isOk()) {
                hintDialog("未读公告条数:" + r.data.getCount());
            } else throw new HttpErrorException(r);
        });
    }

    //获取  公告通知详细
    public void getTXGP_GongGao_DetailData(String detailId) {
        submitRequestThrowError(AnnouncementNoticeModel.getTXGP_GongGao_Detail(detailId), r -> {
            if (r.isOk()) {
                mMsgCountData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }
}
