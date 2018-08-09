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
import com.cpigeon.book.module.menu.adapter.AnnouncementNoticeAdapter;
import com.cpigeon.book.module.menu.viewmodel.AnnouncementNoticeViewModel;

/**
 * 公告通知
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeFragment extends BaseBookFragment {

    private XRecyclerView mRecyclerView;

    private AnnouncementNoticeViewModel mViewModel;
    private AnnouncementNoticeAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AnnouncementNoticeFragment.class);
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

        mViewModel = new AnnouncementNoticeViewModel();
        initViewModel(mViewModel);

        mViewModel.announcementNoticeData.observe(this, logbookEntities -> {

            if (logbookEntities.isEmpty()) {
                mAdapter.setLoadMore(true);
            } else {
                mAdapter.setLoadMore(false);
                mAdapter.addData(logbookEntities);
            }
        });

        mAdapter = new AnnouncementNoticeAdapter(null);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getTXGP_GetGongGaoData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTXGP_GetGongGaoData();
        }, mRecyclerView.getRecyclerView());

        mViewModel.getTXGP_GetGongGaoData();
    }

}
