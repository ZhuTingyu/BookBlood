package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.base.util.EncryptionTool;
import com.base.util.Utils;
import com.base.util.utility.PhoneUtils;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.UserEntity;
import com.cpigeon.book.model.entity.UserHeadImgEntity;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/8/2.
 */

public class LoginModel {
    //用户登录
    public static Observable<ApiResponse<UserEntity>> login(String u, String p) {
        return RequestData.<ApiResponse<UserEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<UserEntity>>() {
                }.getType())
                .url(R.string.user_login)
                .addBody("u", u)//手机号码
                .addBody("p", EncryptionTool.MD5_32(p)) //登录密码，使用32位MD5加密
                .addBody("t", "1")//类型，1为安卓，2为苹果
                .addBody("devid", PhoneUtils.getCombinedDeviceID(Utils.getApp()))//设备ID
                .addBody("dev", PhoneUtils.getModel())//设备信息
                .addBody("ver", String.valueOf(PhoneUtils.getVersionCode(Utils.getApp())))//APP版本代码
                .addBody("appid", MyApp.getAppContext().getPackageName())//App ID
                .request();
    }

    //hl 获取用户头像
    public static Observable<ApiResponse<UserHeadImgEntity>> getUserHeadImgData(String u) {
        return RequestData.<ApiResponse<UserHeadImgEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<UserHeadImgEntity>>() {
                }.getType())
                .url(R.string.user_head_img)
                .addBody("u", u)//手机号码
                .request();
    }

    //hl 第一次启动获取鸽币
    public static Observable<ApiResponse<Object>> getUseroneStart() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.user_one_start_app)
//                .addBody("uid", UserModel.getInstance().getUserId())//登录用户ID
                .addBody("ly", "1")//来源，值等于安卓或苹果
                .addBody("sb", PhoneUtils.getModel())//设备信息
                .addBody("t", "1")//设备信息
                .request();
    }

    //hl 修改密码
    public static Observable<ApiResponse<Object>> getUseroneModifyPsd() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.modify_user_pad)
                .addBody("uid", UserModel.getInstance().getUserId())//登录用户ID
                .addBody("jmm", EncryptionTool.MD5_32(UserModel.getInstance().getUserData().password))//旧密码，32位MD5加密
                .addBody("xmm", "654321")//新密码，不加密
                .addBody("rxmm", "654321")//确认新密码，不加密
                .request();
    }

    //hl 退出登录
    public static Observable<ApiResponse<Object>> getOutLogin() {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.user_out_login)
                .addBody("u", UserModel.getInstance().getUserData().yonghuming)//用户名，或手机号码
                .addBody("p", EncryptionTool.MD5_32(UserModel.getInstance().getUserData().password))//登录密码，使用32位MD5加密
                .addBody("devid", PhoneUtils.getCombinedDeviceID(Utils.getApp()))//登录设备ID
                .request();
    }
}
