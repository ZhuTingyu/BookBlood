package com.cpigeon.book.module.select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.AssEntity;
import com.cpigeon.book.model.entity.SearchHistoryEntity;
import com.cpigeon.book.module.select.adpter.SearchAssAdapter;
import com.cpigeon.book.module.select.viewmodel.SelectAssViewModel;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class SearchAssActivity extends BaseSearchActivity {


    SelectAssViewModel mViewModel;

    SearchAssAdapter mSearchAssAdapter;

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mSearchAssAdapter = new SearchAssAdapter();
        return mSearchAssAdapter;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new SelectAssViewModel();
        initViewModel(mViewModel);

        mViewModel.liveAss.observe(this, assEntities -> {
            mSearchAssAdapter.setNewData(assEntities);
            SearchHistoryEntity historyEntity = new SearchHistoryEntity();
            historyEntity.searchTitle = mViewModel.getKey();
            AppDatabase.getInstance(getBaseActivity()).saveData(historyEntity
                    ,AppDatabase.TYPE_SEARCH_ASS_HISTORY, UserModel.getInstance().getUserId());
            mRlHistory.setVisibility(View.GONE);
        });

        List<DbEntity> history = AppDatabase.getInstance(getBaseActivity())
                .DbEntityDao().getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_ASS_HISTORY);

        if(!Lists.isEmpty(history)){
            mRlHistory.setVisibility(View.VISIBLE);
            mSearchHistoryAdapter.setNewData(AppDatabase.getDates(history, SearchHistoryEntity.class));
        }else {
            mRlHistory.setVisibility(View.GONE);
        }

        mSearchAssAdapter.setOnItemClickListener((adapter, view, position) -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mSearchAssAdapter.getData().get(position).getISOCName())
                    .finishForResult(getBaseActivity());
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            mViewModel.setKey(mSearchHistoryAdapter.getData().get(position).searchTitle);
            mViewModel.getAssList();
        });

        mSearchHistoryAdapter.setOnDeleteClickListener(p -> {
            AppDatabase.getInstance(getBaseActivity()).DbEntityDao().delete(history.get(p));
            if(mSearchHistoryAdapter.getData().isEmpty()){
                mRlHistory.setVisibility(View.GONE);
            }
        });

        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                mViewModel.setKey(key);
                mViewModel.getAssList();
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mTvCleanHistory.setOnClickListener(v -> {
            AppDatabase.getInstance(getBaseActivity()).delectAll(history);
            mSearchHistoryAdapter.getData().clear();
            mRlHistory.setVisibility(View.GONE);
        });

    }

}
