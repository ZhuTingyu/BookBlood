package com.cpigeon.book.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseFragment;
import com.base.FragmentParentActivity;
import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.widget.FiltrateListView;


/**
 * Created by Zhu TingYu on 2017/11/15.
 */

public class SearchFragmentParentActivity extends BaseBookActivity {

    public static String KEY_FRAGMENT = "KEY_FRAGMENT";

    BaseFragment baseFragment;

    private RelativeLayout mRlSearch;
    private TextView mTvSearch;
    private DrawerLayout mDrawerLayout;
    private FiltrateListView mFiltrate;


    public static void start(Activity activity, Class clz, boolean isHaveMenu) {
        Intent intent = new Intent();
        intent.setClass(activity, SearchFragmentParentActivity.class);
        intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
        intent.putExtra(IntentBuilder.KEY_BOOLEAN, isHaveMenu);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }

    public static void start(Activity activity, Class clz, int requestCode, boolean isHaveMenu) {
        Intent intent = new Intent();
        intent.setClass(activity, SearchFragmentParentActivity.class);
        intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
        intent.putExtra(IntentBuilder.KEY_BOOLEAN, isHaveMenu);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }

    public static void start(Activity activity, Class clz, int requestCode) {
        start(activity, clz, requestCode, false);
    }

    public static void start(Activity activity, Class clz) {
        start(activity, clz, false);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getContentView() {

        boolean isHaveMenu = getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);

        if (isHaveMenu) {
            return R.layout.activity_with_search_and_menu_toolbar_layout;
        } else {
            return R.layout.activity_with_search_toolbar_layout;
        }
    }

    public void initView() {


        Class clz = (Class) getIntent().getSerializableExtra(KEY_FRAGMENT);
        String cls = clz.getName();
        Fragment fragment = Fragment.instantiate(getBaseActivity(), cls);
        if (fragment instanceof BaseFragment)
            baseFragment = (BaseFragment) fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_holder, fragment, cls);
        ft.commitAllowingStateLoss();

        mRlSearch = findViewById(R.id.rlSearch);
        mTvSearch = findViewById(R.id.tvSearch);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mFiltrate = findViewById(R.id.filtrate);

    }

    public void setSearchHint(@StringRes int resId) {
        if (mTvSearch != null) {
            mTvSearch.setText(resId);
        }
    }

    public void setSearchClickListener(View.OnClickListener onClickListener) {
        if (mRlSearch != null) {
            mRlSearch.setOnClickListener(onClickListener);
        }
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public FiltrateListView getFiltrate() {
        return mFiltrate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (baseFragment != null)
            baseFragment.onActivityResult(requestCode, resultCode, data);
    }


}
