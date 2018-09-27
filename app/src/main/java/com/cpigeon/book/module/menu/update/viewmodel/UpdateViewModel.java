package com.cpigeon.book.module.menu.update.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.ApiResponse;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.model.UpdateModel;
import com.cpigeon.book.model.entity.UpdateInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/9/27 0027.
 */

public class UpdateViewModel extends BaseViewModel {


    public MutableLiveData<ApiResponse<List<UpdateInfo>>> getUpdateInfoData = new MutableLiveData<>();

    public String id = MyApp.getAppContext().getPackageName();

    //获取  更新数据
    public void getUpdate_InfoData() {
        submitRequestThrowError(UpdateModel.getUpdate_Info(id), r -> {
            getUpdateInfoData.setValue(r);
        });
    }

}
