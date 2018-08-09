package com.cpigeon.book.model;


import android.arch.lifecycle.MutableLiveData;

import com.base.http.ApiResponse;
import com.base.util.Utils;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.base.util.http.GsonUtil;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.UserEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/3/21.
 */

public class UserModel {

    private static UserModel userModel;

    private static UserEntity userEntity;
    public MutableLiveData<UserEntity> mUserLiveData = new MutableLiveData<>();

    public static UserModel getInstance() {
        if (userModel == null) {
            synchronized (UserModel.class) {
                userModel = new UserModel();
            }
        }
        return userModel;
    }

    public UserModel() {

        List<DbEntity> list = AppDatabase.getInstance(Utils.getApp()).DbEntityDao()
                .getDataByType(AppDatabase.TYPE_USER_DATA);

        if (list != null && list.size() > 0) {
            for (DbEntity configBean : list) {
                try {
                    this.userEntity = GsonUtil.fromJson(configBean.getData(), new TypeToken<UserEntity>() {
                    }.getType());
                } catch (Exception e) {

                    break;
                }
            }
        }
    }

    public String getUserId() {
        try {
            return userEntity != null ? userEntity.userid : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getUserToken() {
        try {
            return userEntity != null ? userEntity.token : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getUserName() {
        return userEntity != null ? userEntity.yonghuming : "";
    }

    public boolean isLogin() {
        return StringUtil.isStringValid(getInstance().getUserId());
    }

    public boolean isHaveHouseInfo() {
        return Integer.valueOf(getInstance().getUserData().basicinfo) == 1;
    }

    public synchronized void setUserInfo(UserEntity userInfo, String password) {
        DbEntity entity = new DbEntity();
        userInfo.password = password;
        entity.setId(1);
        entity.setUserId(userInfo.userid);
        entity.setData(GsonUtil.toJson(userInfo));
        entity.setType(AppDatabase.TYPE_USER_DATA);
        AppDatabase.getInstance(Utils.getApp()).DbEntityDao().insert(entity);
        this.userEntity = userInfo;
    }

    public synchronized void save() {
        if (userEntity == null) {
            return;
        }
        DbEntity entity = new DbEntity();
        entity.setId(1);
        entity.setUserId(userEntity.userid);
        entity.setData(GsonUtil.toJson(userEntity));
        entity.setType(AppDatabase.TYPE_USER_DATA);
        AppDatabase.getInstance(Utils.getApp()).DbEntityDao().update(entity);
        mUserLiveData.setValue(userEntity);
    }

    public synchronized void setUserHeadUrl(String headUrl){
        if(StringUtil.isStringValid(headUrl)){
            return;
        }
        userEntity.touxiangurl = headUrl;
        save();
    }

    public void setIsHaveHouseInfo(boolean isHaveHouseInfo) {
         getInstance().getUserData().basicinfo = String.valueOf(isHaveHouseInfo ? 1 : 0);
         save();
    }

//    public static Observable<ApiResponse> loginOut() {
//        return AdminRequest.<ApiResponse>builder()
//                .setToJsonType(new TypeToken<ApiResponse>() {}.getType())
//                .url(R.string.api_login_out)
//                .request().map(r -> {
//                    if(r.status){
//                        getInstance().cleanUserInfo();
//                    }
//                    return r;
//                });
//
//    }

    public static Observable<ApiResponse<UserEntity>> setUserFace(String face) {
        return RequestData.<ApiResponse<UserEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<UserEntity>>() {
                }.getType())
                .url(R.string.api_set_user_face)
                .addImageFileBody("face", face)
                .request();

    }


    public void cleanUserInfo() {
        List<DbEntity> list = AppDatabase.getInstance(Utils.getApp()).DbEntityDao().getAll();
        if (list != null && !list.isEmpty()) {
            for (DbEntity dbEntity : list) {
                AppDatabase.getInstance(Utils.getApp()).DbEntityDao().delete(dbEntity);
            }
        }
        this.userEntity = null;
    }


    public UserEntity getUserData() {
        return userEntity;
    }

}
