package com.cpigeon.book.module.menu.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.AnnouncementNoticeModel;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;

import java.util.List;

/**
 * 公告通知
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeViewModel extends BaseViewModel {


    public int pi = 1;
    public int ps = 5;

    public MutableLiveData<List<AnnouncementNoticeEntity>> announcementNoticeData = new MutableLiveData<>();

    //获取  公告通知列表
    public void getTXGP_GetGongGaoData() {
        submitRequestThrowError(AnnouncementNoticeModel.getTXGP_GetGongGao(pi, ps), r -> {
            if (r.isOk()) {
                announcementNoticeData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

}
