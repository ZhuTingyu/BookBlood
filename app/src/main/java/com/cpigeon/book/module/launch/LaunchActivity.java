package com.cpigeon.book.module.launch;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;

import com.base.base.BaseActivity;
import com.base.util.BarUtils;
import com.base.util.IntentBuilder;
import com.base.util.SharedPreferencesUtil;
import com.base.util.Utils;
import com.base.util.glide.GlideUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.main.MainActivity;
import com.cpigeon.book.module.launch.viewmodel.LaunchViewModel;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.module.pigeonhouse.PigeonHouseInfoFragment;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class LaunchActivity extends BaseActivity {

    public static final String IS_FIRST_OPEN = "IS_FIRST_OPEN";

    Boolean mIsFirstOpen;

    LaunchViewModel mViewModel;

    private ImageView ic_img;

    CountDownTimer countDownTimer;
    Button btnSkip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarVisibility(this, false);
        BarUtils.setStatusBarAllAlpha(this);
        setContentView(R.layout.activity_lauch_layout);

        mIsFirstOpen = SharedPreferencesUtil.getBoolean(this
                , SharedPreferencesUtil.GUIDE_FILE, IS_FIRST_OPEN, true);


        ic_img = findViewById(R.id.ic_img);
        btnSkip = findViewById(R.id.btnSkip);
        countDown();

        btnSkip.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            btnSkip.setText("正在进入");
            new Handler().postDelayed(this::enterApp, 500);
        });

        mViewModel = new LaunchViewModel();
        initViewModel(mViewModel);
        mViewModel.launchImgStr.observe(this, s -> {
            GlideUtil.setGlideImageView(getBaseActivity(), s, ic_img);
        });

        mViewModel.getLaunchImg();

    }

    public void countDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                btnSkip.setText(String.format(Utils.getString(R.string.text_launch_sikp), millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                btnSkip.setText("跳过");
                enterApp();
            }
        };
        countDownTimer.start();
    }

    private void enterApp() {

       // LoginActivity.start(this);


        if (mIsFirstOpen) {
            SharedPreferencesUtil.setBoolean(this
                    , SharedPreferencesUtil.GUIDE_FILE, IS_FIRST_OPEN, false);
            IntentBuilder.Builder(this, GuideActivity.class)
                    .startActivity();
        } else {
            if (UserModel.getInstance().isLogin()) {
                if (UserModel.getInstance().isHaveHouseInfo()) {
                    MainActivity.start(getBaseActivity());
                } else {
                    PigeonHouseInfoFragment.start(getBaseActivity(), false);
                }
            } else {
                LoginActivity.start(this);
            }
        }
        finish();
    }

}
