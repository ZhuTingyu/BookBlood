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
import com.cpigeon.book.event.FeedbackUpdateEvent;
import com.cpigeon.book.model.entity.FeedbackListEntity;
import com.cpigeon.book.module.menu.adapter.FeedbackAdapter;
import com.cpigeon.book.module.menu.viewmodel.FeedbackListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 意见反馈列表
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackListFragment extends BaseBookFragment {


    private XRecyclerView mRecyclerView;

    private FeedbackListViewModel mViewModel;
    private FeedbackAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FeedbackListFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewModel = new FeedbackListViewModel();
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
            mViewModel.getFeedbackList();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getFeedbackList();
        }, mRecyclerView.getRecyclerView());
        setProgressVisible(true);
        mViewModel.getFeedbackList();

        setTitle("意见反馈");

        setToolbarRight("反馈", item -> {
            FeedbackAddFragment.start(getActivity());
            return true;
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            FeedbackListEntity mFeedbackListEntity = (FeedbackListEntity) adapter.getData().get(position);

            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mFeedbackListEntity.getId())
                    .startParentActivity(getActivity(), FeedbackDetailsFragment.class);
        });
    }

    @Override
    protected void initObserve() {
        mViewModel.feedbackListData.observe(this, logbookEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, logbookEntities);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(FeedbackUpdateEvent event){
        mAdapter.getData().clear();
        mViewModel.pi = 1;
        mViewModel.getFeedbackList();
    }
}
