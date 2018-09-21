package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.UpdateTrainEvent;
import com.cpigeon.book.module.trainpigeon.adpter.TrainPigeonAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.TrainPigeonListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class TrainPigeonListFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    TrainPigeonAdapter mAdapter;
    TextView mtvOk;
    TrainPigeonListViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder().startParentActivity(activity, TrainPigeonListFragment.class);
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
        return inflater.inflate(R.layout.xreclyview_no_pading_with_bottom_btn, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_train_record);
        mRecyclerView = findViewById(R.id.list);
        mtvOk = findViewById(R.id.tvOk);
        mAdapter = new TrainPigeonAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            TrainProjectInListFragment.start(getBaseActivity());
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTrainPigeonList();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getTrainPigeonList();
        });

        setProgressVisible(true);
        mViewModel.getTrainPigeonList();

        mtvOk.setText(Utils.getString(R.string.text_new));
        mtvOk.setOnClickListener(v -> {
            NewTrainPigeonFragment.start(getBaseActivity());
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(UpdateTrainEvent event){
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getTrainPigeonList();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mTrainData.observe(this, trainEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, trainEntities);
        });
    }
}
