package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.base.base.pinyin.LetterSortModel;
import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;
import com.cpigeon.book.module.foot.adapter.SelectCountyAdapter;
import com.cpigeon.book.module.foot.viewmodel.SelectCountyViewModel;
import com.gjiazhe.wavesidebar.WaveSideBar;

/**
 * Created by Zhu TingYu on 2018/8/19.
 */

public class SelectCountyFragment extends BaseFragment {

    public static final int CODE_SELECT_COUNTY = 0x123;


    XRecyclerView mRecyclerView;
    WaveSideBar mWaveSideBar;
    LetterSortModel<CountyEntity> mModel = new LetterSortModel<>();
    SelectCountyViewModel mViewModel;
    SearchFragmentParentActivity mActivity;
    SelectCountyAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new SelectCountyViewModel(getBaseActivity());
        initViewModel(mViewModel);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_with_wave_bar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setSearchHint(R.string.text_input_county_name_and_search);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchCountyActivity.class, null);
        });

        mRecyclerView = findViewById(R.id.list);
        mWaveSideBar = findViewById(R.id.side_bar);

        mAdapter = new SelectCountyAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView.getRecyclerView());

        setProgressVisible(true);
        mViewModel.getCountyList();

    }

    @Override
    protected void initObserve() {
        mViewModel.mLiveData.observe(this, selectCountyEntities -> {
            setProgressVisible(false);
            mModel.setData(selectCountyEntities);
            mAdapter.initWave(mModel, mWaveSideBar);
            mAdapter.initHead(getBaseActivity());
            mRecyclerView.setRefreshing(false);
            mAdapter.setNewData(mModel.getData());
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, entity)
                        .finishForResult(getBaseActivity());
            } catch (Exception e) {
                CountyEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, entity)
                        .finishForResult(getBaseActivity());
            }
        }
    }
}
