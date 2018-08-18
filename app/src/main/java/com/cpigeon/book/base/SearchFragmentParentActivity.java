package com.cpigeon.book.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseFragment;
import com.base.FragmentParentActivity;
import com.base.base.BaseActivity;
import com.cpigeon.book.R;


/**
 * Created by Zhu TingYu on 2017/11/15.
 */

public class SearchFragmentParentActivity extends BaseActivity {

    public static String KEY_FRAGMENT = "KEY_FRAGMENT";

    BaseFragment baseFragment;

    private RelativeLayout mRlSearch;
    private TextView mTvSearch;

    public static void start(Activity activity, Class clz){
        Intent intent = new Intent();
        intent.setClass(activity, SearchFragmentParentActivity.class);
        intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }

    public static void start(Activity activity, Class clz, int requestCode){
        Intent intent = new Intent();
        intent.setClass(activity, SearchFragmentParentActivity.class);
        intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        Class clz = (Class) getIntent().getSerializableExtra(KEY_FRAGMENT);

        String cls = clz.getName();

        setContentView(R.layout.activity_with_search_toolbar_layout);
        Fragment fragment = Fragment.instantiate(getBaseActivity(), cls);
        if (fragment instanceof BaseFragment)
            baseFragment = (BaseFragment) fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_holder, fragment, cls);
        ft.commitAllowingStateLoss();

        mRlSearch = findViewById(R.id.rlSearch);
        mTvSearch = findViewById(R.id.tvSearch);

    }

    public void setSearchHint(@StringRes int resId){
        if(mTvSearch != null){
            mTvSearch.setText(resId);
        }
    }

    public void setSearchClickListener(View.OnClickListener onClickListener){
        if(mRlSearch != null){
            mRlSearch.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(baseFragment!=null)
            baseFragment.onActivityResult(requestCode,resultCode,data);
    }


}
