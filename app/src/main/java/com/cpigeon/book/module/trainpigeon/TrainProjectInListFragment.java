package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.UpdateTrainEvent;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.adpter.TrainProjectListAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.TrainProjectInListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class TrainProjectInListFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    TrainProjectListAdapter mAdapter;
    TrainProjectInListViewModel mViewModel;
    TextView mTvOk;

    public static void start(Activity activity, TrainEntity entity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, entity)
                .startParentActivity(activity, TrainProjectInListFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new TrainProjectInListViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xreclyview_with_bottom_btn_not_white, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(mViewModel.mTrainEntity.getPigeonTrainName());
        setToolbarRight(R.string.text_train_analyze, item -> {
            if(!Lists.isEmpty(mViewModel.getEndTrain(mAdapter.getData()))){
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, mViewModel.mTrainEntity)
                        .putParcelableArrayListExtra(IntentBuilder.KEY_DATA_2, (ArrayList<? extends Parcelable>) mViewModel.getEndTrain(mAdapter.getData()))
                        .startParentActivity(getBaseActivity(), SelectTrainProjectFragment.class);
            }else {
                DialogUtils.createErrorDialog(getBaseActivity(), Utils.getString(R.string.text_not_have_end_yet_train));
            }
            return false;
        });
        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mTvOk.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTvOk.setText("再训");
        mAdapter = new TrainProjectListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getCountList();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getCountList();
        });

        mTvOk.setBackgroundResource(R.drawable.selector_bg_cancel_btn);

        mTvOk.setOnClickListener(v -> {
            NewTrainPigeonFragment.start(getBaseActivity(), mViewModel.mTrainEntity);
        });

        setProgressVisible(true);
        mViewModel.getCountList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(UpdateTrainEvent event) {
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getCountList();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataCountList.observe(this, trainEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, trainEntities);

            if(Lists.isEmpty(mAdapter.getData())){
                return;
            }

            if(mAdapter.getData().get(0).getTrainStateName()
                    .equals(Utils.getString(R.string.text_end_yet))){
                mTvOk.setVisibility(View.VISIBLE);
                findViewById(R.id.view1).setVisibility(View.VISIBLE);
            }else {
                mTvOk.setVisibility(View.GONE);
                findViewById(R.id.view1).setVisibility(View.GONE);
            }

            setProgressVisible(false);
        });
    }


}
