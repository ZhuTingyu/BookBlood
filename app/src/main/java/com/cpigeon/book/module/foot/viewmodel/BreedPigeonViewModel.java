package com.cpigeon.book.module.foot.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.BreedPigeonModel;

/**
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonViewModel extends BaseViewModel {


    public MutableLiveData<String> oneStartHintStr = new MutableLiveData<>();

    //获取  足环的类型  选择
    public void getTXGP_FootRingType_SelectData() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_FootRingType_Select(), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }


    public String foot = "2018-11-112233";
    public String money = "100";
    public String ringNum = "2";//挂环次数
    public String footType = "4";//足环类型
    public String footState = "2";//足环状态
    public String footSource = "2";//足环来源
    public String remark = "测试备注";//备注

    //添加足环（单个）
    public void getTXGP_FootRing_AddData() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_FootRing_Add(foot, money, ringNum, footType, footState, footSource, remark), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }


    public  String delFootId = "16";
    public void getTXGP_FootRing_DeleteData(){
        submitRequestThrowError(BreedPigeonModel.getTXGP_FootRing_Delete(delFootId), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }
}
