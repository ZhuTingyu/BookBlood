package com.cpigeon.book.module.home.goodpigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.FragmentUtils;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.event.GoodPigeonEvent;
import com.cpigeon.book.model.entity.GoodPigeonCountEntity;
import com.cpigeon.book.module.home.goodpigeon.viewmodel.GoodPigeonHomeViewModel;
import com.cpigeon.book.widget.FragmentTabView;
import com.cpigeon.book.widget.stats.StatView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 铭鸽库
 * Created by Zhu TingYu on 2018/9/14.
 */

public class GoodPigeonHomeFragment extends BaseBookFragment {

    protected List<String> mTitles = Lists.newArrayList();
    protected List<Fragment> mFragments = Lists.newArrayList();

    private RelativeLayout mRlSearch;
    private TextView mTvSearch;
    private FrameLayout mFrameLayout;
    private FragmentTabView mTabView;
    GoodPigeonHomeViewModel mViewModel;

    private StatView mStat1;
    private StatView mStat2;
    private StatView mStat3;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new GoodPigeonHomeViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_good_pigeon_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setToolbarNotBack();
        setToolbarRightImage(R.mipmap.ic_adds, item -> {
            IntentBuilder.Builder().startParentActivity(getBaseActivity(), ApplyAddGoodPigeonFragment.class);
            return false;
        });

        mFrameLayout = findViewById(R.id.frame);
        mTabView = findViewById(R.id.tab);
        mTvSearch = findViewById(R.id.tvSearch);
        mRlSearch = findViewById(R.id.rlSearch);
        mStat1 = findViewById(R.id.stat1);
        mStat2 = findViewById(R.id.stat2);
        mStat3 = findViewById(R.id.stat3);

        mRlSearch.setOnClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchGoodPigeonActivity.class);
        });
        mTvSearch.setText(R.string.text_input_foot_number_search);

        initTitles();
        initFragments();

        composite.add(RxUtils.delayed(500, aLong -> {
            mTabView.setTitles(mTitles);
            mTabView.setSelect(0);
            mTabView.setOnSelectListener(position -> {
                FragmentUtils.showHide(position, mFragments);
                bindStateData(position);
            });
        }));

        FragmentUtils.add(getFragmentManager(), mFragments, R.id.frame, 0);

        mViewModel.getCount();
    }

    private void bindStateData(int position) {
        GoodPigeonCountEntity goodPigeonCountEntity = mViewModel.mGoodPigeonCountEntity;
        if (position == 0) {
            mStat1.bindData(goodPigeonCountEntity.getAllCount(), goodPigeonCountEntity.getAllCount());
            mStat2.bindData(goodPigeonCountEntity.getAllXiongCount(), goodPigeonCountEntity.getAllCount());
            mStat3.bindData(goodPigeonCountEntity.getAllCiCount(), goodPigeonCountEntity.getAllCount());
            if(goodPigeonCountEntity.getAllCiCount() == 0){
                setStatVisible(false);
            }else {
                setStatVisible(true);
            }
        } else if (position == 1) {
            mStat1.bindData(goodPigeonCountEntity.getWoCount(), goodPigeonCountEntity.getWoCount());
            mStat2.bindData(goodPigeonCountEntity.getWoXiongCount(), goodPigeonCountEntity.getWoCount());
            mStat3.bindData(goodPigeonCountEntity.getWoCiCount(), goodPigeonCountEntity.getWoCount());
            if(goodPigeonCountEntity.getWoCount() == 0){
                setStatVisible(false);
            }else {
                setStatVisible(true);
            }
        } else if (position == 2) {
            mStat1.bindData(goodPigeonCountEntity.getCount(), goodPigeonCountEntity.getCount());
            mStat2.bindData(goodPigeonCountEntity.getXiongCount(), goodPigeonCountEntity.getCount());
            mStat3.bindData(goodPigeonCountEntity.getCiCount(), goodPigeonCountEntity.getCount());
            if(goodPigeonCountEntity.getCount() == 0){
                setStatVisible(false);
            }else {
                setStatVisible(true);
            }
        }
    }

    @Override
    protected void initObserve() {
        mViewModel.mDataGoodPitgeonCount.observe(this, goodPigeonCountEntity -> {
            bindStateData(mTabView.getCPosition());
        });
    }

    public void setStatVisible(boolean visible) {
        if (visible) {
            mStat1.setVisibility(View.VISIBLE);
            mStat2.setVisibility(View.VISIBLE);
            mStat3.setVisibility(View.VISIBLE);
        } else {
            mStat1.setVisibility(View.GONE);
            mStat2.setVisibility(View.GONE);
            mStat3.setVisibility(View.GONE);
        }
    }

    protected void initFragments() {
        for (int i = 0; i < mTitles.size(); i++) {
            GoodPigeonListFragment pigeonListFragment = new GoodPigeonListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(IntentBuilder.KEY_DATA, i + 1);
            pigeonListFragment.setArguments(bundle);
            mFragments.add(pigeonListFragment);
        }
    }

    protected void initTitles() {
        mTitles = Lists.newArrayList(getBaseActivity().getResources().getStringArray(R.array.array_good_pigeon));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(GoodPigeonEvent event) {
        mViewModel.getCount();
    }
}
