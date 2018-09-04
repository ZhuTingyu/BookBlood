package com.cpigeon.book.module.menu.message;

import android.app.Activity;
import android.content.Context;
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
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseTabActivity;
import com.cpigeon.book.widget.CustomTabView;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30.
 */

public class MsgActivity extends BaseTabActivity {

    private static CommonNavigatorAdapter mCommonNavigatorAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder(activity, MsgActivity.class).startActivity();
    }

    private static List<String> mData = Lists.newArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData = Lists.newArrayList("0", "0");

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        mCommonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {


                CustomTabView mCustomTabView = new CustomTabView(context);

                mCustomTabView.setContext(mTitles.get(index));
                mCustomTabView.setSum(mData.get(index));

                mCustomTabView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return mCustomTabView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        };

        commonNavigator.setAdapter(mCommonNavigatorAdapter);
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);

    }

    @Override
    protected void initFragments() {

        AnnouncementNoticeFragment mAnnouncementNoticeFragment = new AnnouncementNoticeFragment();
        mFragments.add(mAnnouncementNoticeFragment);

        PigeonFriendMsgFragment mPigeonFriendMsgFragment = new PigeonFriendMsgFragment();
        mFragments.add(mPigeonFriendMsgFragment);
    }

    @Override
    protected void initTitles() {
        String[] titles = getResources().getStringArray(R.array.array_msg);
        mTitles = Lists.newArrayList(titles);
    }


    public static List<String> getTobData() {
        return mData;
    }

    public static void initTobData(List<String> mData) {
        MsgActivity.mData = mData;
        mCommonNavigatorAdapter.notifyDataSetChanged();
    }
}
