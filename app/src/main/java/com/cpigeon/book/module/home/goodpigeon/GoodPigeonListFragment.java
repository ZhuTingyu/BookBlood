package com.cpigeon.book.module.home.goodpigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.home.goodpigeon.adpter.GoodPigeonListAdapter;

/**
 * Created by Zhu TingYu on 2018/9/14.
 */

public class GoodPigeonListFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    GoodPigeonListAdapter mAdapter;

    private RelativeLayout mRlSearch;
    private TextView mTvSearch;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_good_pigeon_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarRight(Utils.getString(R.string.text_add), item -> {
            IntentBuilder.Builder().startParentActivity(getBaseActivity(), ApplyAddGoodPigeonFragment.class);
            return false;
        });
        mTvSearch = findViewById(R.id.tvSearch);
        mTvSearch.setText(R.string.text_input_foot_number_search);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setListPadding(15, 0, 15, 0);
        mRlSearch = findViewById(R.id.rlSearch);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2
                , StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new GoodPigeonListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());
    }
}
