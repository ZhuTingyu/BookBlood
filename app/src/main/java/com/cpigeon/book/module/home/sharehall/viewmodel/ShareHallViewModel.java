package com.cpigeon.book.module.home.sharehall.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.SharePigeonModel;
import com.cpigeon.book.model.entity.PigeonEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class ShareHallViewModel extends BaseViewModel {

    public static final String TYPE_MY = "1";
    public static final String TYPE_OTHER = "2";

    public int pi = 1;//页面
    public int ps;//每页条数
    public String type;//是否是我的共享（1:我的共享鸽子，2：其他为所有共享鸽子，不传则返回所有共享鸽子）
    public String blood;//血统（1,2）
    public String sex;//性别（1,2）
    public String eye;//眼沙（1,2）
    public String footNum;//足环号

    public MutableLiveData<List<PigeonEntity>> mDataSharePigeon = new MutableLiveData<>();
    public MutableLiveData<String> mDataCancelShareR = new MutableLiveData<>();
    public String pigeonId;
    public String footId;

    public void getSharePigeons() {
        submitRequestThrowError(SharePigeonModel.getSharePigeon(pi, 10, type, blood, sex, eye, footNum), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mDataSharePigeon.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

    public void cancelApplyShareHall() {
        submitRequestThrowError(BreedPigeonModel.cancelAddShareHall(pigeonId, footId), r -> {
            if(r.isOk()){
                mDataCancelShareR.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }
}
