package com.cpigeon.book.module.home.goodpigeon;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.GoodPigeonEvent;
import com.cpigeon.book.module.home.goodpigeon.adpter.GoodPigeonListAdapter;
import com.cpigeon.book.module.home.goodpigeon.viewmodel.GoodPigeonListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2018/9/19.
 */

public class GoodPigeonListFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    GoodPigeonListAdapter mAdapter;
    GoodPigeonListViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new GoodPigeonListViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2
                , StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new GoodPigeonListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getPigeon();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getPigeon();
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            int type = bundle.getInt(IntentBuilder.KEY_DATA, 0);
            mViewModel.type = type;
        }
        mViewModel.getPigeon();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(GoodPigeonEvent event){
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getPigeon();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataGoodPigeon.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, pigeonEntities);
        });
    }
}
