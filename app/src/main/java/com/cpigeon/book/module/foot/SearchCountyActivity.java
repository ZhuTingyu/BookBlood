package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.module.foot.adapter.SelectCountyAdapter;
import com.cpigeon.book.module.foot.viewmodel.SelectCountyViewModel;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/22.
 */

public class SearchCountyActivity extends BaseSearchActivity {
    SelectCountyAdapter mAdapter;
    SelectCountyViewModel mViewModel;

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseContext()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId(),AppDatabase.TYPE_SEARCH_COUNTY_HISTORY);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectCountyAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSearchHint(R.string.text_input_county_name_and_search);
        mViewModel = new SelectCountyViewModel(getBaseActivity());
        initViewModel(mViewModel);

        mViewModel.mLiveData.observe(this, countyEntities -> {
            setProgressVisible(false);
            saveHistory(mViewModel.key, AppDatabase.TYPE_SEARCH_COUNTY_HISTORY);
            mAdapter.setNewData(countyEntities);
        });

        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                setProgressVisible(true);
                mViewModel.key = key;
                mViewModel.getCountyList();
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            goneHistroy();
            setProgressVisible(true);
            mViewModel.key = mSearchHistoryAdapter.getItem(position).searchTitle;
            mViewModel.getCountyList();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, entity)
                    .finishForResult(getBaseActivity());
        }
    }
}
