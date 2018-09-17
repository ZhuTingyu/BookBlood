package com.cpigeon.book.module.feedpigeon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.FragmentAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/8.
 */

public class FeedPigeonRecordActivity extends BaseBookActivity {

    private TextView mTvUserVaccine;
    private TextView mTvStatusIllnessRecord;
    private TextView mTvDrugUseCase;
    private TextView mTvCareDrug;
    private ViewPager mViewPager;

    private LinearLayout mLl1;
    private LinearLayout mLl2;
    private LinearLayout mLl3;
    private LinearLayout mLl4;

    private int mCPosition = 0;

    List<TextView> mTextViews = Lists.newArrayList();
    List<LinearLayout> mLinearLayouts = Lists.newArrayList();


    @Override
    protected int getContentView() {
        return R.layout.activity_add_feed_pigeon_record;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.text_add_feed_pigeon_record);

        mTvUserVaccine = findViewById(R.id.tvUserVaccine);
        mTvStatusIllnessRecord = findViewById(R.id.tvStatusIllnessRecord);
        mTvDrugUseCase = findViewById(R.id.tvDrugUseCase);
        mTvCareDrug = findViewById(R.id.tvCareDrug);
        mViewPager = findViewById(R.id.viewPager);
        mLl1 = findViewById(R.id.ll1);
        mLl2 = findViewById(R.id.ll2);
        mLl3 = findViewById(R.id.ll3);
        mLl4 = findViewById(R.id.ll4);


        mTextViews.add(mTvUserVaccine);
        mTextViews.add(mTvStatusIllnessRecord);
        mTextViews.add(mTvDrugUseCase);
        mTextViews.add(mTvCareDrug);

        mLinearLayouts.add(mLl1);
        mLinearLayouts.add(mLl2);
        mLinearLayouts.add(mLl3);
        mLinearLayouts.add(mLl4);

        //疫苗注射
        UseVaccineFragment addUseVaccineFragment = new UseVaccineFragment();
        //病情记录
        StatusIllnessRecordFragment addStatusIllnessRecordFragment = new StatusIllnessRecordFragment();
        //用药情况
        DrugUseCaseFragment addDrugUseCaseFragment = new DrugUseCaseFragment();
        //保健品
        CareDrugFragment addCareDrugFragment = new CareDrugFragment();

        List<Fragment> fragments = Lists.newArrayList();
        fragments.add(addUseVaccineFragment);
        fragments.add(addStatusIllnessRecordFragment);
        fragments.add(addDrugUseCaseFragment);
        fragments.add(addCareDrugFragment);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, null);

        mViewPager.setAdapter(adapter);

        mViewPager.setCurrentItem(mCPosition);
        setTextStatus(mCPosition, true);
        for (int i = 0, len = mTextViews.size(); i < len; i++) {
            int finalI = i;
            mTextViews.get(i).setOnClickListener(v -> {
                mViewPager.setCurrentItem(finalI);
            });
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTextStatus(mCPosition, false);
                mCPosition = position;
                setTextStatus(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setTextStatus(int position, boolean isSelect) {

        TextView view = mTextViews.get(position);

        if (isSelect) {
            view.setBackgroundResource(R.drawable.shape_bg_add_feed_pigeon_record_select);
            view.setTextColor(Utils.getColor(R.color.colorPrimary));
//            new CrazyShadow.Builder()
//                    .setContext(getBaseActivity())
//                    .setBaseShadowColor(Utils.getColor(R.color.colorPrimary))
//                    .setDirection(CrazyShadowDirection.BOTTOM)
//                    .setShadowRadius(ScreenTool.dip2px(5))
//                    .setCorner(ScreenTool.dip2px(5))
//                    .setBackground(Utils.getColor(R.color.white))
//                    .setImpl(CrazyShadow.IMPL_DRAW)
//                    .action(mLinearLayouts.get(position));


        } else {
            view.setBackgroundResource(R.drawable.shape_bg_add_feed_pigeon_record_not_select);
            view.setTextColor(Utils.getColor(R.color.color_text_hint));
        }


    }
}
