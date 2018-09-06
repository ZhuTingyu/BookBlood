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

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class TrainProjectListFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    TrainProjectListAdapter mAdapter;

    public static void start(Activity activity){
        IntentBuilder.Builder().startParentActivity(activity, TrainProjectListFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        mRecyclerView = findViewById(R.id.list);
        mAdapter = new TrainProjectListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());
    }
}
