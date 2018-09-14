package com.cpigeon.book.module.pigeonleague;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.pigeonleague.adpter.SelectPigeonToLeagueAdapter;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * Created by Zhu TingYu on 2018/9/12.
 */

public class SelectPigeonToLeagueFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    SelectPigeonToLeagueAdapter mAdapter;

    SearchFragmentParentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarNotBack();
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchPigeonToLeagueActivity.class, null);
        });
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setBackgroundColor(Utils.getColor(R.color.white));
        mRecyclerView.addItemDecorationLine(20);
        mAdapter = new SelectPigeonToLeagueAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //mAdapter.setNewData(Lists.newTestArrayList());
        mRecyclerView.setRefreshListener(() -> {
            mAdapter.removeAllHeaderView();
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, Lists.newArrayList());
            mAdapter.addHeaderView(initHead());
        });

    }

    private View initHead() {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_select_pigeon_to_league_head, null);
        return view;
    }
}
