package com.cpigeon.book.module.play.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PlayModel;
import com.cpigeon.book.model.entity.PlayInportListEntity;

import java.util.List;

/**
 * 赛绩导入
 * Created by Administrator on 2018/9/28 0028.
 */

public class PlayInportViewModel extends BaseViewModel {


    public int pi;
    public int ps;
    public String orgId;

    //直播赛绩列表
    public MutableLiveData<List<PlayInportListEntity>> mPlayListData = new MutableLiveData<>();

    //导入赛绩结果回调
    public MutableLiveData<Object> mPlayInporttData = new MutableLiveData<>();

    //获取中鸽直播赛绩 列表
    public void getLivePlay() {
        submitRequestThrowError(PlayModel.getLivePlay(String.valueOf(pi),
                String.valueOf(ps),
                orgId), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPlayListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //标准的赛绩 导入中鸽直播赛绩
    public void getLivePlayInputData() {
        submitRequestThrowError(PlayModel.getLivePlayInput(orgId), r -> {
            if (r.isOk()) {
                mPlayInporttData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


}
