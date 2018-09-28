package com.cpigeon.book.module.home.goodpigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.home.goodpigeon.adpter.GoodPigeonListAdapter;
import com.cpigeon.book.module.home.goodpigeon.viewmodel.GoodPigeonListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class SearchGoodPigeonActivity extends BaseSearchActivity {

    GoodPigeonListAdapter mAdapter;
    GoodPigeonListViewModel mViewModel;

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_GOOD_PIGEON);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new GoodPigeonListAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new GoodPigeonListViewModel();
        initViewModel(mViewModel);

        setSearchHint(R.string.text_input_foot_number_search);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2
                , StaggeredGridLayoutManager.VERTICAL));

        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                mAdapter.cleanList();
                mViewModel.pi = 1;
                mViewModel.foodNumber = key;
                mViewModel.getPigeon();
                saveHistory(key, AppDatabase.TYPE_SEARCH_GOOD_PIGEON);
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getPigeon();
        }, mRecyclerView.getRecyclerView());

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            setProgressVisible(true);
            mViewModel.foodNumber = mSearchHistoryAdapter.getItem(position).searchTitle;
            mViewModel.getPigeon();
        });

        mViewModel.mDataGoodPigeon.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, pigeonEntities);
        });
    }
}
