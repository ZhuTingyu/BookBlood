package com.cpigeon.book.module.foot;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.foot.adapter.FootAdminListAdapter;
import com.cpigeon.book.module.foot.viewmodel.FootAdminListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class SearchFootActivity extends BaseSearchActivity {

    FootAdminListAdapter mAdapter;
    FootAdminListViewModel mViewModel;

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new FootAdminListAdapter(getBaseActivity());
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new FootAdminListViewModel();
        initViewModel(mViewModel);

        mViewModel.footAdminListData.observe(this, footEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, footEntities);
        });

        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                mViewModel.key = key;
                mViewModel.getFoodList();
                saveHistroy(key, AppDatabase.TYPE_SEARCH_FOOT_HISTORY);
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            mViewModel.key = mSearchHistoryAdapter.getItem(position).searchTitle;
            mViewModel.getFoodList();
        });

    }

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity())
                .DbEntityDao().getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_FOOT_HISTORY);
    }
}
