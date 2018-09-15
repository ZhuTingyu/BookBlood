package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.trainpigeon.adpter.TrainProjectListAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.TrainPigeonListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class TrainProjectInListFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    TrainProjectListAdapter mAdapter;
    TrainPigeonListViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder().startParentActivity(activity, TrainProjectInListFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new TrainPigeonListViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_project_name);
        setToolbarRight(R.string.text_train_analyze, item -> {
            IntentBuilder.Builder().startParentActivity(getBaseActivity(), SelectTrainProjectFragment.class);
            return false;
        });
        mRecyclerView = findViewById(R.id.list);
        mAdapter = new TrainProjectListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTrainPigeonList();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setRefreshListener(() -> {
            mViewModel.pi = 1;
            mViewModel.getTrainPigeonList();
        });

        setProgressVisible(true);
        mViewModel.getTrainPigeonList();

    }

    @Override
    protected void initObserve() {
        mViewModel.mTrainData.observe(this, trainEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, trainEntities);
        });
    }
}
