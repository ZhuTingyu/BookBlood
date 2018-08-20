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
import com.cpigeon.book.module.menu.adapter.FeedbackAdapter;
import com.cpigeon.book.module.menu.viewmodel.FeedbackViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 意见反馈列表
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackListFragment extends BaseBookFragment {


    private XRecyclerView mRecyclerView;

    private FeedbackViewModel mViewModel;
    private FeedbackAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FeedbackListFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewModel = new FeedbackViewModel();
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
        mRecyclerView = findViewById(R.id.list);

        mAdapter = new FeedbackAdapter(null);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getZGW_Users_Feedback_ListData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getZGW_Users_Feedback_ListData();
        }, mRecyclerView.getRecyclerView());

        mViewModel.getZGW_Users_Feedback_ListData();


        setTitle("意见反馈");

        setToolbarRight("反馈", item -> {
            FeedbackAddFragment.start(getActivity());
            return true;
        });
    }

    @Override
    protected void initObserve() {
        mViewModel.feedbackListData.observe(this, logbookEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, logbookEntities);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }
}
