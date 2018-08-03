package com.cpigeon.book.module.login;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.VideoView;

import com.base.base.BaseActivity;
import com.base.util.BarUtils;
import com.base.util.FragmentUtils;
import com.base.util.IntentBuilder;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class LoginActivity extends BaseActivity {

    private VideoView mVideoView;
    private LoginFragment mLoginFragment;
    private RegisterFragment mRegisterFragment;
    private ForgetPasswordFragment mForgetPasswordFragment;


    public static final String TYPE_LOGIN = "TYPE_LOGIN";
    public static final String TYPE_REGISTER = "TYPE_REGISTER";
    public static final String TYPE_FORGET_PASSWORD = "TYPE_FORGET_PASSWORD";

    public static void start(Activity activity){
        IntentBuilder.Builder(activity, LoginActivity.class)
                .startActivity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BarUtils.setStatusBarVisibility(this,false);
        BarUtils.setStatusBarAllAlpha(this);

        mVideoView = findViewById(R.id.video_player);

        String uri = "android.resource://" + getPackageName() + "/" + R.raw.login_video;
        mVideoView.setVideoURI(Uri.parse(uri));
        mVideoView.setOnPreparedListener(mp -> {
            mp.start();
            mp.setLooping(true);
        });

        mLoginFragment = new LoginFragment();
        mRegisterFragment = new RegisterFragment();
        mForgetPasswordFragment = new ForgetPasswordFragment();

        FragmentUtils.add(getSupportFragmentManager(),mRegisterFragment, R.id.rlMain);
        FragmentUtils.add(getSupportFragmentManager(),mForgetPasswordFragment, R.id.rlMain);
        FragmentUtils.add(getSupportFragmentManager(),mLoginFragment, R.id.rlMain);

        FragmentUtils.replace(getSupportFragmentManager(),mLoginFragment, R.id.rlMain);

    }

    public void replace(String type){
        if(TYPE_REGISTER.equals(type)){
            FragmentUtils.replace(getSupportFragmentManager(),mRegisterFragment, R.id.rlMain);
        }else if(TYPE_LOGIN.equals(type)){
            FragmentUtils.replace(getSupportFragmentManager(),mLoginFragment, R.id.rlMain);
        }else if(TYPE_FORGET_PASSWORD.equals(type)){
            FragmentUtils.replace(getSupportFragmentManager(),mForgetPasswordFragment, R.id.rlMain);
        }
    }
}
