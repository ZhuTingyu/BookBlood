package com.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.base.base.BaseActivity;
import com.base.http.R;


/**
 * Created by Zhu TingYu on 2017/11/15.
 */

public class FragmentParentActivity extends BaseActivity {

    public static String KEY_FRAGMENT = "KEY_FRAGMENT";
    public static String KEY_HAVE_TOOL_BAR = "KEY_HAVE_TOOL_BAR";

    BaseFragment baseFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        Class clz = (Class) getIntent().getSerializableExtra(KEY_FRAGMENT);
        boolean isHaveToolBar = getIntent().getBooleanExtra(KEY_HAVE_TOOL_BAR, true);
        String cls = clz.getName();
        if(isHaveToolBar){
            setContentView(R.layout.activity_with_toolbar_layout);
        }else {
            setContentView(R.layout.activity_not_toolbar_layout);
        }
        Fragment fragment = Fragment.instantiate(getBaseActivity(), cls);
        if (fragment instanceof BaseFragment)
            baseFragment = (BaseFragment) fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_holder, fragment, cls);
        ft.commitAllowingStateLoss();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(baseFragment!=null)
            baseFragment.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {
        if(!baseFragment.OnBackClick()){
            super.onBackPressed();
        }
    }
}
