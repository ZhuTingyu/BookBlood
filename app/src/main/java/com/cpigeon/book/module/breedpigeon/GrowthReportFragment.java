package com.cpigeon.book.module.breedpigeon;

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
import com.base.util.RxUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.breedpigeon.adpter.GrowthReportAdapter;
import com.cpigeon.book.module.breedpigeon.viewmodel.GrowthReportViewModel;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class GrowthReportFragment extends BaseBookFragment {

    GrowthReportViewModel mViewModel;
    XRecyclerView mRecyclerView;
    GrowthReportAdapter mAdapter;

    public static void start(Activity activity, String footNumber){
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, footNumber)
                .startParentActivity(activity, GrowthReportFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new GrowthReportViewModel(getBaseActivity());
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
        setTitle(mViewModel.footNumber);
        mRecyclerView = findViewById(R.id.list);
        composite.add(RxUtils.delayed(50,aLong -> {
            mRecyclerView.setListPadding(0, 32,0, 32);
        }));
        mAdapter = new GrowthReportAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());

    }
}
