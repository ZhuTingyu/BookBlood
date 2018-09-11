package com.cpigeon.book.module.feedpigeon;

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
import com.cpigeon.book.module.feedpigeon.adapter.SelectIllnessRecordAdapter;

/**
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SelectIllnessRecordFragment extends BaseBookFragment{

    XRecyclerView mRecyclerView;
    SelectIllnessRecordAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = findViewById(R.id.list);
        mAdapter = new SelectIllnessRecordAdapter();
        mRecyclerView.addItemDecorationLine();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, "疾病名称")
                    .finishForResult(getBaseActivity());
        });
    }
}
