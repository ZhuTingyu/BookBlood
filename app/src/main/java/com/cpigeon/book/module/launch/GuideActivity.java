package com.cpigeon.book.module.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.base.base.BaseActivity;
import com.base.base.FragmentAdapter;
import com.base.util.BarUtils;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.CustomViewPager;
import com.base.widget.magicindicator.MagicIndicator;
import com.base.widget.magicindicator.ViewPagerHelper;
import com.base.widget.magicindicator.ext.navigator.ScaleCircleNavigator;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class GuideActivity extends BaseActivity {

    private CustomViewPager viewPager;
    private MagicIndicator magicIndicator;
    List<Fragment> mFragments = Lists.newArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        BarUtils.setStatusBarVisibility(this, false);
        BarUtils.setStatusBarAllAlpha(this);
        viewPager = findViewById(R.id.viewPager);
        magicIndicator =  findViewById(R.id.magicIndicator);

        for (int i = 0; i < 4; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt(IntentBuilder.KEY_DATA, i);
            GuideFragment guideFragment = new GuideFragment();
            guideFragment.setArguments(bundle);
            mFragments.add(guideFragment);
        }

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager()
                ,mFragments, null);

        viewPager.setAdapter(adapter);

        initMagicIndicator();
    }

    private void initMagicIndicator() {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(mFragments.size());
        scaleCircleNavigator.setNormalCircleColor(getResources().getColor(com.base.http.R.color.darker_gray));
        scaleCircleNavigator.setSelectedCircleColor(getResources().getColor(com.base.http.R.color.white));
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }
}
