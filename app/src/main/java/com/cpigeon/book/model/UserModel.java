package com.cpigeon.book.model;


import com.base.http.ApiResponse;
import com.base.util.EncryptionTool;
import com.base.util.Utils;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.base.util.http.GsonUtil;
import com.base.util.utility.PhoneUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.UserEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.PUT;

/**
 * Created by Zhu TingYu on 2018/3/21.
 */

public class UserModel {

    private static UserModel userModel;

    private static UserEntity userEntity;

    public static UserModel getInstance() {
        if (userModel == null) {
            synchronized (UserModel.class) {
                userModel = new UserModel();
            }
        }
        return userModel;
    }

    public UserModel() {

        List<DbEntity> list = AppDatabase.getInstance(Utils.getApp()).DbEntityDao().getAll();

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
        return userEntity != null ? userEntity. userid: "";
    }

    public String getUserToken() {
        return userEntity != null ? userEntity.token : "";
    }

    public String getUserName() {
        return userEntity != null ? userEntity.yonghuming : "";
    }

    public boolean isLogin() {
        return StringUtil.isStringValid(getInstance().getUserId());
    }

    public boolean isHaveHouseInfo(){
        return Integer.valueOf(getInstance().getUserData().basicinfo) == 1;
    }

    public synchronized void setUserInfo(UserEntity userInfo, String password) {
        DbEntity entity = new DbEntity();
        userInfo.password = password;
        entity.setUserId(userInfo.userid);
        entity.setData(GsonUtil.toJson(userInfo));
        entity.setType(AppDatabase.TYPE_USER_DATA);
        AppDatabase.getInstance(Utils.getApp()).DbEntityDao().insert(entity);
        this.userEntity = userInfo;
    }

    public synchronized void save(){
        if(userEntity == null){
            return;
        }
        DbEntity entity = new DbEntity();
        entity.setId(1);
        entity.setUserId(userEntity.userid);
        entity.setData(GsonUtil.toJson(userEntity));
        entity.setType(AppDatabase.TYPE_USER_DATA);
        AppDatabase.getInstance(Utils.getApp()).DbEntityDao().updata(entity);
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

    private void cleanUserInfo() {
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
