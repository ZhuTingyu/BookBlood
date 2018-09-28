package com.cpigeon.book.module.order.balance;

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
import com.cpigeon.book.module.order.adpter.BalanceDetailsAdapter;
import com.cpigeon.book.module.order.viewmodel.BalanceViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 余额明细
 * Created by Administrator on 2018/8/24.
 */

public class AccountBalanceDetailsFragment extends BaseBookFragment {

    private XRecyclerView mRecyclerView;

    private BalanceViewModel mViewModel;
    private BalanceDetailsAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AccountBalanceDetailsFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewModel = new BalanceViewModel();
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

        setTitle("余额明细");

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();

        mAdapter = new BalanceDetailsAdapter(null);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getAccountBalanceListData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getAccountBalanceListData();
        }, mRecyclerView.getRecyclerView());
        setProgressVisible(true);
        mViewModel.getAccountBalanceListData();

    }


    @Override
    protected void initObserve() {

        mViewModel.mAccountBalanceListData.observe(this, datas -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }

}
