package com.cpigeon.book.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseFragment;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.widget.SearchTextView;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public abstract class BaseSearchFragment extends BaseFragment {

    protected XRecyclerView mRecyclerView;
    protected RecyclerView mRecyclerViewHistory;
    protected SearchTextView mSearchTextView;
    protected RelativeLayout mRlHistory;
    protected TextView mTvCleanHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = findViewById(R.id.list);
        mSearchTextView = findViewById(R.id.key);
        mRlHistory = findViewById(R.id.rlHistory);
        mTvCleanHistory = findViewById(R.id.tvCleanHistory);
        mRecyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerViewHistory.setAdapter(getHistoryAdapter());
        
        mRecyclerView.setAdapter(getResultAdapter());
        
        
    }

    protected abstract BaseQuickAdapter getHistoryAdapter();

    protected abstract BaseQuickAdapter getResultAdapter();
}

