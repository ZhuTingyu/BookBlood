package com.cpigeon.book.module.trainpigeon;

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
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.adpter.SelectTrainProjectAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class SelectTrainProjectFragment extends BaseBookFragment {

    private TextView mTvAll;
    private TextView mTvSure;
    private XRecyclerView mRecyclerView;
    SelectTrainProjectAdapter mAdapter;
    List<TrainEntity> mTrainEntities;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_train_project, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrainEntity trainEntity = getBaseActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
        mTrainEntities = getBaseActivity().getIntent().getParcelableArrayListExtra(IntentBuilder.KEY_DATA_2);
        setTitle(trainEntity.getPigeonTrainName());

        mTvAll = findViewById(R.id.tvAll);
        mTvSure = findViewById(R.id.tvSure);
        mRecyclerView = findViewById(R.id.list);

        mAdapter = new SelectTrainProjectAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                mAdapter.setMultiSelectItem(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mRecyclerView.setAdapter(mAdapter);


        mTvAll.setOnClickListener(v -> {
            mAdapter.isChooseAll(true);
        });

        mTvSure.setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, trainEntity)
                    .putParcelableArrayListExtra(IntentBuilder.KEY_DATA_2, (ArrayList<? extends Parcelable>) mAdapter.getSelectedEntity())
                    .startParentActivity(getBaseActivity(), TrainAnalyzeFragment.class);
        });

        mRecyclerView.setRefreshing(false);

        mAdapter.setNewData(mTrainEntities);
    }
}
