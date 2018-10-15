package com.cpigeon.book.module.feedpigeon;

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
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.StatusIllnessRecordEntity;
import com.cpigeon.book.module.feedpigeon.adapter.SelectIllnessRecordAdapter;
import com.cpigeon.book.module.feedpigeon.viewmodel.DrugUseCaseListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 病情记录
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SelectIllnessRecordFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    SelectIllnessRecordAdapter mAdapter;

    private DrugUseCaseListViewModel mDrugUseCaseListViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDrugUseCaseListViewModel = new DrugUseCaseListViewModel();
        initViewModels(mDrugUseCaseListViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setTitle("病情记录");

        mDrugUseCaseListViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

        mRecyclerView = findViewById(R.id.list);
        mAdapter = new SelectIllnessRecordAdapter();
        mRecyclerView.addItemDecorationLine();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            try {
                StatusIllnessRecordEntity mStatusIllnessRecordEntity = (StatusIllnessRecordEntity) adapter.getData().get(position);

                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, mStatusIllnessRecordEntity)
                        .finishForResult(getBaseActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        mRecyclerView.setRefreshListener(() -> {
            setProgressVisible(true);
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            mDrugUseCaseListViewModel.pi = 1;
            mDrugUseCaseListViewModel.getTXGP_PigeonDisease_SelectAllData();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            setProgressVisible(true);
            mDrugUseCaseListViewModel.pi++;
            mDrugUseCaseListViewModel.getTXGP_PigeonDisease_SelectAllData();
        }, mRecyclerView.getRecyclerView());


        setProgressVisible(true);
        mDrugUseCaseListViewModel.getTXGP_PigeonDisease_SelectAllData();

    }


    @Override
    protected void initObserve() {
        super.initObserve();

        mDrugUseCaseListViewModel.mStatusIllnessRecordData.observe(this, datas -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mDrugUseCaseListViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

    }
}
