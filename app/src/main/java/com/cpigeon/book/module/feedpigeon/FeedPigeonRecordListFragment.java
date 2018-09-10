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
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.feedpigeon.adapter.FeedPigeonRecordListAdapter;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonRecordListFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    FeedPigeonRecordListAdapter mAdapter;
    SearchFragmentParentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchFeedPigeonRecordActivity.class,null);
        });
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new FeedPigeonRecordListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            IntentBuilder.Builder().startParentActivity(getBaseActivity(), FeedPigeonDetailsFragment.class);
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());
    }
}
