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
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.FlyBackAddRecordEvent;
import com.cpigeon.book.event.UpdateTrainEvent;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.adpter.FlyBackRecordAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.FlyBackRecordViewModel;
import com.cpigeon.book.util.TextViewUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

/**
 * Created by Zhu TingYu on 2018/9/6.
 * //归巢记录
 */

public class FlyBackRecordFragment extends BaseBookFragment {

    boolean isEnd = false;

    XRecyclerView mRecyclerView;
    FlyBackRecordAdapter mAdapter;
    TextView mTvOk;
    FlyBackRecordViewModel mViewModel;

    public static void start(Activity activity, TrainEntity trainEntity, boolean isEnd) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_BOOLEAN, isEnd)
                .putExtra(IntentBuilder.KEY_DATA, trainEntity)
                .startParentActivity(activity, FlyBackRecordFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new FlyBackRecordViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(mViewModel.mTrainEntity.getPigeonTrainName());

        isEnd = getBaseActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);
        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mAdapter = new FlyBackRecordAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setRefreshListener(() -> {
            mViewModel.getFlyBackRecord();
        });

        if (isEnd) {
            mTvOk.setVisibility(View.GONE);
        } else {
            setToolbarRight(R.string.text_add, item -> {
                if(mViewModel.isHaveNotBack()){
                    AddFlyBackRecordFragment.start(getBaseActivity(), mViewModel.mTrainEntity);
                }else {
                    DialogUtils.createHintDialog(getBaseActivity(), Utils.getString(R.string.text_all_pigeon_back_yet));
                }
                return false;
            });
            TextViewUtil.setCancle(mTvOk);
            mTvOk.setText(R.string.text_end_train);
            mTvOk.setOnClickListener(v -> {
                DialogUtils.createDialogWithLeft(getBaseActivity(),Utils.getString(R.string.text_is_end_train), sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    setProgressVisible(true);
                    mViewModel.endTrain();
                });
            });
        }

        setProgressVisible(true);
        mViewModel.getFlyBackRecord();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(FlyBackAddRecordEvent event) {
        mViewModel.getFlyBackRecord();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataFlyBack.observe(this, flyBackRecordEntities -> {
            setProgressVisible(false);
            if (Lists.isEmpty(flyBackRecordEntities)) {
                mAdapter.setNewData(Lists.newArrayList());
            } else {
                Collections.sort(flyBackRecordEntities, new FlyBackRecordComparator());
                for (int i = 0, len = flyBackRecordEntities.size(); i < len; i++) {
                    flyBackRecordEntities.get(i).order = i + 1;
                }
                mAdapter.setNewData(mAdapter.get(flyBackRecordEntities));
            }
        });

        mViewModel.mDataDeleteR.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new UpdateTrainEvent());
                finish();
            });
        });
    }
}
