package com.cpigeon.book.module.breeding;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.breeding.adapter.BreedingFootAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/11/10.
 */

public class SearchBreedInfoActivity extends BaseSearchActivity {

    BreedingFootAdapter mAdapter;
    PairingInfoListViewModel mViewModel;


    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity())
                .DbEntityDao().getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_BREED_INFO);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new BreedingFootAdapter(mViewModel);
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setListPadding(0, 0, 0, 0);
        mViewModel = new PairingInfoListViewModel();
        mSearchTextView.setHint(R.string.text_input_foot_number_search);
        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                setProgressVisible(true);
                mAdapter.cleanList();
                mViewModel.footnum = key;
                mViewModel.getTXGP_PigeonBreed_SelectAll();
                saveHistory(key, AppDatabase.TYPE_SEARCH_BREED_INFO);
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            mViewModel.footnum = mSearchHistoryAdapter.getItem(position).searchTitle;
            setProgressVisible(true);
            mViewModel.getTXGP_PigeonBreed_SelectAll();
        });

        mViewModel.mBreedingInfoListData.observe(this, breedPigeonEntities -> {
            setProgressVisible(false);
            mAdapter.setNewData(breedPigeonEntities);
        });

        mViewModel.mDataSetTogetherR.observe(this, aBoolean -> {
            setProgressVisible(true);
            mViewModel.getTXGP_PigeonBreed_SelectAll();
        });

        mViewModel.mDataDeletR.observe(this, aBoolean -> {
            setProgressVisible(true);
            mViewModel.getTXGP_PigeonBreed_SelectAll();
        });
    }
}
