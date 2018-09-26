package com.cpigeon.book.module.photo.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PairingModel;
import com.cpigeon.book.model.PhotoAlbumModel;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonPhotoEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/30.
 */

public class PigeonPhotoViewModel extends BaseViewModel {

    public PigeonEntity mPigeonEntity;
    public List<SelectTypeEntity> mPhotoType;
    public String typeid;//

    public int pi = 1;
    public int ps = 15000;
    public int sort = 1;//排序（1，倒序，2顺序，默认倒序）

    //单个鸽子   相册列表
    public MutableLiveData<List<PigeonPhotoEntity>> mPigeonPhotoData = new MutableLiveData<>();
    //单只鸽子  相片统计
    public MutableLiveData<PigeonPhotoEntity> mPigeonPhotoCount = new MutableLiveData<>();


    //获取  获取图片列表
    public void getTXGP_PigeonPhoto_SelectData() {
        submitRequestThrowError(PhotoAlbumModel.getTXGP_PigeonPhoto_Select(String.valueOf(pi),
                String.valueOf(ps),
                mPigeonEntity.getPigeonID(),
                UserModel.getInstance().getUserId(),
                String.valueOf(sort),
                typeid
        ), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mPigeonPhotoData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    //单只鸽子  相片统计
    public void getTXGP_PigeonPhoto_CountPhotoData() {
        submitRequestThrowError(PhotoAlbumModel.getTXGP_PigeonPhoto_CountPhoto(
                "",
                ""
        ), r -> {
            if (r.isOk()) {
                mPigeonPhotoCount.postValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }

//    //单只鸽子  相片统计
//    public void getTXGP_PigeonPhoto_CountPhotoData() {
//        submitRequestThrowError(PhotoAlbumModel.getTXGP_PigeonPhoto_CountPhoto(
//                mPigeonEntity.getPigeonID(),
//                mPigeonEntity.getFootRingID()
//        ), r -> {
//            if (r.isOk()) {
//                mPigeonPhotoCount.postValue(r.data);
//            } else throw new HttpErrorException(r);
//        });
//    }
}
