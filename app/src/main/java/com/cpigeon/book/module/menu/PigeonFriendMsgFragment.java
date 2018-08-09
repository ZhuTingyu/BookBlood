package com.cpigeon.book.module.menu;

import android.app.Activity;
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
import com.cpigeon.book.module.menu.adapter.PigeonFriendMsgAdapter;
import com.cpigeon.book.module.menu.viewmodel.PigeonFriendMsgViewModel;

/**
 * 鸽友消息
 * Created by Administrator on 2018/8/9.
 */

public class PigeonFriendMsgFragment extends BaseBookFragment {

    private XRecyclerView mRecyclerView;

    private PigeonFriendMsgViewModel mViewModel;
    private PigeonFriendMsgAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PigeonFriendMsgFragment.class);
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

        mViewModel = new PigeonFriendMsgViewModel();
        initViewModel(mViewModel);

        mViewModel.pigeonFriendMsgListData.observe(this, logbookEntities -> {

            if (logbookEntities.isEmpty()) {
                mAdapter.setLoadMore(true);
            } else {
                mAdapter.setLoadMore(false);
                mAdapter.addData(logbookEntities);
            }
        });

        mAdapter = new PigeonFriendMsgAdapter(null);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getTXGP_GetMsgListData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTXGP_GetMsgListData();
        }, mRecyclerView.getRecyclerView());

        mViewModel.getTXGP_GetMsgListData();
    }

}
