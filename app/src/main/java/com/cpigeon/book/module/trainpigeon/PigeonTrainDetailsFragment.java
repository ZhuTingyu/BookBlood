package com.cpigeon.book.module.trainpigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.trainpigeon.adpter.FootNumberTrainDetailsAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.PigeonTrainDetailsViewModel;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class PigeonTrainDetailsFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    FootNumberTrainDetailsAdapter mAdapter;
    PigeonTrainDetailsViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new PigeonTrainDetailsViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("2018-22-1234567");
        setToolbarRight(R.string.text_details, item -> {
            BreedPigeonDetailsFragment.start(getBaseActivity(), mViewModel.pigeonId, mViewModel.footId);
            return false;
        });
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mRecyclerView.setRefreshListener(() -> {
            mViewModel.getFootNumberTrainDetails();
        });
        mAdapter = new FootNumberTrainDetailsAdapter();

        mRecyclerView.setAdapter(mAdapter);

        setProgressVisible(true);
        mViewModel.getFootNumberTrainDetails();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataPigeonTrainDetails.observe(this, pigeonTrainDetailsEntities -> {
            setProgressVisible(false);
            mRecyclerView.setRefreshing(false);
            mAdapter.setNewData(pigeonTrainDetailsEntities);
        });
    }
}
