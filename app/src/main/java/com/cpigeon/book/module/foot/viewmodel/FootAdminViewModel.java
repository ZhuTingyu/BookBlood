package com.cpigeon.book.module.foot.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.FootAdminModel;

import io.reactivex.functions.Consumer;

/**
 * 足环管理
 * Created by Administrator on 2018/8/7.
 */

public class FootAdminViewModel extends BaseViewModel {

    public MutableLiveData<String> oneStartHintStr = new MutableLiveData<>();

    public String foot = "2018-11-112233";
    public String money = "100";
    public String footType = "4";//足环类型
    public String footState = "2";//足环状态
    public String footSource = "2";//足环来源
    public String remark = "测试备注";//备注


    public Consumer<String> setAddFootNum() {
        return s -> {
            Log.d("xiaohly", "setAddFootNum: " + s);
            foot = s;
        };
    }


    //添加足环（单个）
    public void getTXGP_FootRing_AddData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Add(foot, money, footType, footState, footSource, remark), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }


    public String footId = "16";

    public Consumer<String> setEditFootId() {
        return s -> {
            footId = s;
        };
    }

    //修改足环（单个）
    public void getTXGP_FootRing_EditData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Edit(footId, foot, money, footType, footState, footSource, remark), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }


    public String delFootId = "16";

    public Consumer<String> setDelFootId() {
        return s -> {
            delFootId = s;
        };
    }

    //删除足环（单个）
    public void getTXGP_FootRing_DeleteData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Delete(delFootId), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }





    public String footDetailsID = "16";

    public Consumer<String> setDetailsFootId() {
        return s -> {
            footDetailsID = s;
        };
    }

    //获取单个足环详细
    public void getTXGP_FootRing_SelectData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_Select(footDetailsID), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }


    public String segment_startFoot = "";
    public String segment_endFoot = "";
    public String segment_typeId = "7";//类型
    public String segment_stateId = "2";//状态
    public String segment_source = "购买";//来源
    public String segment_money = "";
    public String segment_usenum = "";
    public String segment_remark = "";

    public Consumer<String> setSegmentStartFoot() {
        return s -> {
            segment_startFoot = s;
        };
    }

    public Consumer<String> setSegmentEndFoot() {
        return s -> {
            segment_endFoot = s;
        };
    }

    public Consumer<String> setSegmentMoney() {
        return s -> {
            segment_money = s;
        };
    }


    public Consumer<String> setSegmentRemark() {
        return s -> {
            segment_remark = s;
        };
    }

    //添加足环号段
    public void getTXGP_FootRing_AddSectionData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_AddSection(segment_startFoot,
                segment_endFoot, segment_typeId, segment_stateId, segment_source, segment_money,
                segment_usenum, segment_remark), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }




    public int pi = 1;
    public int ps = 20;
    public String year = "";
    public String typeid = "";
    public String stateid = "";
    public String key = "";

    //足环号码 列表
    public void getTXGP_FootRing_SelectKeyAllData() {
        submitRequestThrowError(FootAdminModel.getTXGP_FootRing_SelectKeyAll(pi, ps, year, typeid, stateid, key), r -> {
            if (r.isOk()) {

            } else throw new HttpErrorException(r);
        });
    }
}
