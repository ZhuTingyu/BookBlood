package com.cpigeon.book.module.play.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.http.GsonUtil;
import com.cpigeon.book.model.PlayModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PlayImportListEntity;

import java.util.List;

/**
 * 赛绩导入
 * Created by Administrator on 2018/9/28 0028.
 */

public class PlayImportViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;
    public String organizeType;//"xh";//类型，lx=gp或lx=xh
    public String organizeName;// = "山东冠县正信俱乐部";//赛事组织名称
    public String organizeId;//赛事组织名称
    public String name;//姓名
    public String houseNumber = "000466";//棚号
    public String matchNumber;//赛事编号
    public List<PlayImportListEntity> mPlayData;

    //直播赛绩列表
    public MutableLiveData<List<PlayImportListEntity>> mPlayListData = new MutableLiveData<>();

    //导入赛绩结果回调
    public MutableLiveData<String> mPlayInporttData = new MutableLiveData<>();

    //获取中鸽直播赛绩 列表
    public void getLivePlay() {
        submitRequestThrowError(PlayModel.getLivePlay(
                mPigeonEntity.getFootRingNum(),//足环号码
                organizeType,//类型，lx=gp或lx=xh
                organizeName,//赛事组织名称
                name,//姓名
                houseNumber,//棚号
                matchNumber//赛事编号
        ), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPlayListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //标准的赛绩 导入中鸽直播赛绩
    public void inputLivePlayData() {
        submitRequestThrowError(PlayModel.inputLivePlay(
                organizeId,
                mPigeonEntity.getPigeonID(),
                mPigeonEntity.getFootRingID(),
                getPlayData()
        ), r -> {
            if (r.isOk()) {
                mPlayInporttData.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public String getPlayData() {
        for (PlayImportListEntity playImportListEntity : mPlayData) {
            playImportListEntity.setRid(playImportListEntity.getId());
        }
        return GsonUtil.toJson(mPlayData);
    }
}
