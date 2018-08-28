package com.cpigeon.book.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.base.base.BaseActivity;
import com.base.base.FragmentAdapter;
import com.base.util.Lists;
import com.base.widget.CustomViewPager;
import com.base.widget.magicindicator.MagicIndicator;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/27.
 */

public abstract class BaseTabActivity extends BaseActivity{

    protected CustomViewPager mViewPager;
    protected MagicIndicator mIndicator;

    protected List<String> mTitles = Lists.newArrayList();
    protected List<Fragment> mFragments = Lists.newArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);
        findViewById(R.id.imgBack).setOnClickListener(v -> {
            finish();
        });
        initTitles();
        initFragments();

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager()
                ,mFragments,mTitles);

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);

    }

    protected abstract void initFragments();
    protected abstract void initTitles();
}
