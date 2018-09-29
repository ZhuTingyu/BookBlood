package com.cpigeon.book.module.order;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.magicindicator.ViewPagerHelper;
import com.base.widget.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.base.widget.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.base.widget.magicindicator.ext.titles.ScaleTransitionPagerTitleView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseTabActivity;

/**
 * Created by Zhu TingYu on 2018/8/27.
 */

public class OrderListActivity extends BaseTabActivity {

    public static void start(Activity activity) {
        IntentBuilder.Builder(activity, OrderListActivity.class).startActivity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitles.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.WHITE);
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);

    }

    @Override
    protected void initFragments() {

        for (int i = 0; i < mTitles.size(); i++) {
            OrderListFragment orderListFragment = new OrderListFragment();
            Bundle bundle = new Bundle();
            switch (i){
                case 0:
                    bundle.putString(IntentBuilder.KEY_TYPE, OrderListFragment.TYPE_ALL);
                    break;
                case 1:
                    bundle.putString(IntentBuilder.KEY_TYPE, OrderListFragment.TYPE_UNPAID);
                    break;
                case 2:
                    bundle.putString(IntentBuilder.KEY_TYPE, OrderListFragment.TYPE_FINISH);
                    break;
                case 3:
                    bundle.putString(IntentBuilder.KEY_TYPE, OrderListFragment.TYPE_OUT_OF_DATE);
                    break;
            }
            orderListFragment.setArguments(bundle);
            mFragments.add(orderListFragment);
        }
    }

    @Override
    protected void initTitles() {
        String[] titles = getResources().getStringArray(R.array.array_order_status);
        mTitles = Lists.newArrayList(titles);
    }
}
