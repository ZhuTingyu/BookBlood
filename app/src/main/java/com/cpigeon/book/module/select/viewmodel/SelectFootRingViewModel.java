package com.cpigeon.book.module.select.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.FootAdminModel;
import com.cpigeon.book.model.entity.FootEntity;
import com.haibin.calendarview.BaseView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/13.
 */

public class SelectFootRingViewModel extends BaseViewModel {

    public int pi = 1;
    public int ps;
    public String footNumber;
    public String sexId;
    public String staterId;

    public MutableLiveData<List<FootEntity>> mDataFootList = new MutableLiveData<>();

    public void getFootList() {
        submitRequestThrowError(FootAdminModel.getFootList(pi,
                ps,
                footNumber,
                sexId,
                staterId), r -> {
            if(r.isOk()){
                listEmptyMessage.setValue(r.msg);
                mDataFootList.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }
}
