package com.cpigeon.book.module.menu.service;

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
import com.cpigeon.book.module.menu.service.adpter.OpenServiceReportAdapter;
import com.cpigeon.book.module.menu.service.viewmodel.ServerReportViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 开通续费记录
 * Created by Zhu TingYu on 2018/8/31.
 */

public class OpenServiceReportFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    OpenServiceReportAdapter mAdapter;
    private ServerReportViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, OpenServiceReportFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewModel = new ServerReportViewModel();
        initViewModels(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_open_report);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new OpenServiceReportAdapter();
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTXGP_GetServiceListData();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getTXGP_GetServiceListData();
        });


        setProgressVisible(true);
        mViewModel.getTXGP_GetServiceListData();
    }

    @Override
    protected void initObserve() {

        mViewModel.mServerReportData.observe(this, datas -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }

}
