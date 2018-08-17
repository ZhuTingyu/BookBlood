package com.cpigeon.book.module.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.base.base.BaseActivity;
import com.base.util.BarUtils;
import com.base.util.RxUtils;
import com.base.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.module.launch.viewmodel.LaunchViewModel;
import com.cpigeon.book.module.login.LoginActivity;

import io.reactivex.disposables.Disposable;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class LaunchActivity extends BaseActivity {

    public static final String IS_FIRST_OPEN = "IS_FIRST_OPEN";

    Disposable mDisposable;
    Boolean mIsFirstOpen;

    LaunchViewModel mViewModel;

    private ImageView ic_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarVisibility(this, false);
        BarUtils.setStatusBarAllAlpha(this);
        setContentView(R.layout.activity_lauch_layout);

        mIsFirstOpen = SharedPreferencesUtil.getBoolean(this
                , SharedPreferencesUtil.GUIDE_FILE, IS_FIRST_OPEN, true);

        mDisposable = RxUtils.delayed(1500, aLong -> {
            enterApp();
        });
        composite.add(mDisposable);


        ic_img = findViewById(R.id.ic_img);

        mViewModel = new LaunchViewModel();
        initViewModel(mViewModel);
        mViewModel.launchImgStr.observe(this, s -> {
            Glide.with(this).load(s).into(ic_img);
        });

        mViewModel.getLaunchImg();
    }

    private void enterApp() {

        LoginActivity.start(this);

//        if(mIsFirstOpen){
//            SharedPreferencesUtil.setBoolean(this
//                    , SharedPreferencesUtil.GUIDE_FILE, IS_FIRST_OPEN, false);
//            IntentBuilder.Builder(this, GuideActivity.class)
//                    .startActivity();
//        }else {
//            if(UserModel.getInstance().isLogin()){
//                if(UserModel.getInstance().isHaveHouseInfo()){
//                    MainActivity.start(getBaseActivity());
//                }else {
//                    PigeonHouseInfoFragment.start(getBaseActivity(), false);
//                }
//            }else {
//                LoginActivity.start(this);
//            }
//        }
        finish();
    }
}
