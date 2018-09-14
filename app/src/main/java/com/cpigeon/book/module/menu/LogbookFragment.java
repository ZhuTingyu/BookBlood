package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.home.home.adapter.LogbookAdapter;
import com.cpigeon.book.module.menu.viewmodel.LogbookViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * hl 操作日志
 * Created by Administrator on 2018/8/8.
 */

public class LogbookFragment extends BaseBookFragment {


    private XRecyclerView mRecyclerView;

    private LogbookViewModel mViewModel;
    private LogbookAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, LogbookFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewModel = new LogbookViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xrecyclerview_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(R.string.text_operate_log);

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();

        mAdapter = new LogbookAdapter(null);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getZGW_Users_GetLogData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getZGW_Users_GetLogData();
        }, mRecyclerView.getRecyclerView());
        setProgressVisible(true);
        mViewModel.getZGW_Users_GetLogData();

    }


    @Override
    protected void initObserve() {

        mViewModel.logbookData.observe(this, datas -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }

}
