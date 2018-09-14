package com.cpigeon.book.module.menu.service.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.AnnouncementNoticeModel;
import com.cpigeon.book.model.ServerReportModel;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;
import com.cpigeon.book.model.entity.ServerReportEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/14 0014.
 */
public class ServerReportViewModel extends BaseViewModel {

    public int pi = 1;
    public int ps = 15;

    public MutableLiveData<List<ServerReportEntity>> mServerReportData = new MutableLiveData<>();
    //获取  服务开通续费纪录
    public void getTXGP_GetServiceListData() {
        submitRequestThrowError(ServerReportModel.getTXGP_GetServiceList(pi, ps), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mServerReportData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }
}
