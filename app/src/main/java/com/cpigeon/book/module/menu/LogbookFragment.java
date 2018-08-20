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
import com.cpigeon.book.module.home.adapter.LogbookAdapter;
import com.cpigeon.book.module.home.viewmodel.LogbookViewModel;
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
        View view = inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("操作日志");

        mRecyclerView = findViewById(R.id.list);

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

        mViewModel.getZGW_Users_GetLogData();

    }


    @Override
    protected void initObserve() {

        mViewModel.logbookData.observe(this, datas -> {
            RecyclerViewUtils.setRefreshingCallBack(mRecyclerView, mAdapter, datas);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }

}
