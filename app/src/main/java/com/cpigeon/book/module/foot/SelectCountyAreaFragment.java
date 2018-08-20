package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.module.foot.adapter.SelectCountyAreaAdapter;
import com.cpigeon.book.module.foot.viewmodel.SelectCountyViewModel;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class SelectCountyAreaFragment extends BaseFragment {

    XRecyclerView recyclerView;
    SelectCountyAreaAdapter mAdapter;
    SelectCountyViewModel mViewModel;

    public static void start(Activity activity, String id){
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, id)
                .startParentActivity(activity, SelectCountyAreaFragment.class);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new SelectCountyViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(R.string.text_area_select);

        recyclerView = findViewById(R.id.list);
        recyclerView.addItemDecorationLine();
        mAdapter = new SelectCountyAreaAdapter();
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mAdapter.getItem(position))
                    .finishForResult(getBaseActivity());
        });

        setProgressVisible(true);
        mViewModel.getAreaList();
    }

    @Override
    protected void initObserve() {
        mViewModel.mAreaLiveData.observe(this, countyEntities -> {
            setProgressVisible(false);
            mAdapter.setNewData(countyEntities);
        });
    }
}
