package com.cpigeon.book.module.order;

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
import com.cpigeon.book.module.order.adpter.OrderListAdapter;
import com.cpigeon.book.module.order.viewmodel.OrderListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * Created by Administrator on 2018/8/19.
 */

public class OrderListFragment extends BaseBookFragment {

    public static final String TYPE_ALL = "TYPE_ALL";
    public static final String TYPE_UNPAID = "TYPE_UNPAID";
    public static final String TYPE_FINISH = "TYPE_FINISH";
    public static final String TYPE_OUT_OF_DATE = "TYPE_OUT_OF_DATE";

    XRecyclerView mRecyclerView;

    private OrderListViewModel mViewModel;
    private OrderListAdapter mAdapter;

    public String typeStr;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, OrderListFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new OrderListViewModel();
        initViewModels(mViewModel);
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
        mRecyclerView = findViewById(R.id.list);

        try {
            typeStr = getArguments().getString(IntentBuilder.KEY_TYPE);

            switch (typeStr) {
                case OrderListFragment.TYPE_ALL:
                    mViewModel.lx = "all";
                    break;
                case OrderListFragment.TYPE_UNPAID:
                    mViewModel.lx = "dzf";
                    break;

                case OrderListFragment.TYPE_FINISH:
                    mViewModel.lx = "ok";
                    break;

                case OrderListFragment.TYPE_OUT_OF_DATE:
                    mViewModel.lx = "ygq";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mAdapter = new OrderListAdapter();

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getTXGP_GetOrderListData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTXGP_GetOrderListData();
        }, mRecyclerView.getRecyclerView());
        setProgressVisible(true);
        mViewModel.getTXGP_GetOrderListData();

    }

    @Override
    protected void initObserve() {

        mViewModel.mOrderListData.observe(this, datas -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
            setProgressVisible(false);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }


}
