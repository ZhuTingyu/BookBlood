package com.cpigeon.book.module.feedpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.adpter.GrowthReportAdapter;
import com.cpigeon.book.module.feedpigeon.viewmodel.GrowthReportViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 成长记录
 * Created by Zhu TingYu on 2018/8/29.
 */

public class GrowthReportFragment extends BaseBookFragment {

    GrowthReportViewModel mViewModel;
    XRecyclerView mRecyclerView;
    GrowthReportAdapter mAdapter;

    public static void start(Activity activity, PigeonEntity mPigeonEntity,String  puid) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                .putExtra(IntentBuilder.KEY_DATA_2,puid)
                .startParentActivity(activity, GrowthReportFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new GrowthReportViewModel();
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

        mViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        mViewModel.puid = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);

        setTitle(mViewModel.mPigeonEntity.getFootRingNum());

        mRecyclerView = findViewById(R.id.list);
        composite.add(RxUtils.delayed(50, aLong -> {
            mRecyclerView.setListPadding(0, 32, 0, 32);
        }));

        mAdapter = new GrowthReportAdapter();

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

        });

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTXGP_Pigeon_SelectGrowAllData();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getTXGP_Pigeon_SelectGrowAllData();
        });

        //
        mViewModel.getTXGP_Pigeon_SelectGrowAllData();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mViewModel.mGrowthReportListData.observe(this, datas -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }
}
