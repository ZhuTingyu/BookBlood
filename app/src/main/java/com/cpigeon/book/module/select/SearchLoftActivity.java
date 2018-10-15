package com.cpigeon.book.module.select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.select.adpter.SearchLoftAdapter;
import com.cpigeon.book.module.select.viewmodel.SelectAssViewModel;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * 搜索公棚
 */

public class SearchLoftActivity extends BaseSearchActivity {


    SelectAssViewModel mViewModel;

    SearchLoftAdapter mSearchAssAdapter;

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mSearchAssAdapter = new SearchLoftAdapter();
        return mSearchAssAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSearchHint(R.string.text_input_loft_and_search);

        mViewModel = new SelectAssViewModel();
        initViewModel(mViewModel);

        mViewModel.liveLoft.observe(this, assEntities -> {
            mSearchAssAdapter.setNewData(assEntities);
            saveHistory(mViewModel.getKey(), AppDatabase.TYPE_SEARCH_ASS_HISTORY);
            mRlHistory.setVisibility(View.GONE);
        });

        mSearchAssAdapter.setOnItemClickListener((adapter, view, position) -> {
            try {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, mSearchAssAdapter.getData().get(position))
                        .finishForResult(getBaseActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            try {
                mViewModel.setKey(mSearchHistoryAdapter.getData().get(position).searchTitle);
                mViewModel.getLoftList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                mViewModel.setKey(key);
                mViewModel.getLoftList();
            }

            @Override
            public void cancel() {
                finish();
            }
        });

    }

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity())
                .DbEntityDao().getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_ASS_HISTORY);
    }

}
