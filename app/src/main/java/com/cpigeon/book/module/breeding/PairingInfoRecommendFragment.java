package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.base.FragmentAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.CustomViewPager;
import com.base.widget.magicindicator.MagicIndicator;
import com.base.widget.magicindicator.ViewPagerHelper;
import com.base.widget.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.base.widget.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.base.widget.magicindicator.ext.titles.ScaleTransitionPagerTitleView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.module.breeding.childfragment.PairingLineageFragment;
import com.cpigeon.book.module.breeding.childfragment.PairingPlayFragment;
import com.cpigeon.book.module.breeding.childfragment.PairingScoreFragment;

import java.util.List;

import butterknife.BindView;

/**
 * 推荐配对
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoRecommendFragment extends BaseBookFragment {

    private int RECOMMEND_REQUEST = 0x002323;

    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.viewPager)
    CustomViewPager mViewPager;
    protected List<Fragment> mFragments = Lists.newArrayList();
    protected List<String> mTitles = Lists.newArrayList();

    public static void start(Activity activity, BreedPigeonEntity mBreedPigeonEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity)
                .startParentActivity(activity, PairingInfoRecommendFragment.class );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pairing_recommend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("推荐配对");

        initViewPager();
    }

    private void initViewPager() {

        PairingLineageFragment mPairingLineageFragment = new PairingLineageFragment();
        mFragments.add(mPairingLineageFragment);

        PairingPlayFragment mPairingPlayFragment = new PairingPlayFragment();
        mFragments.add(mPairingPlayFragment);

        PairingScoreFragment mPairingScoreFragment = new PairingScoreFragment();
        mFragments.add(mPairingScoreFragment);

        String[] titles = {"按血统", "按赛绩", "按评分"};
        mTitles = Lists.newArrayList(titles);

        FragmentAdapter adapter = new FragmentAdapter(getBaseActivity().getSupportFragmentManager()
                , mFragments, mTitles);

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
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
                simplePagerTitleView.setNormalColor(getBaseActivity().getResources().getColor(R.color.color_4c4c4c));
                simplePagerTitleView.setSelectedColor(getBaseActivity().getResources().getColor(R.color.colorPrimary));
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

}
