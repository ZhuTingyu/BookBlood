package com.cpigeon.book.module.home.sharehall;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.home.sharehall.adpter.ShareHallHomeAdapter;
import com.cpigeon.book.module.home.sharehall.viewmodel.ShareHallViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/26.
 */

public class SearchSharePigeonActivity extends BaseSearchActivity {

    ShareHallHomeAdapter mAdapter;
    ShareHallViewModel mViewModel;
    boolean isMyShare;

    public static void start(Activity activity, boolean isMyShare) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, isMyShare);
        BaseSearchActivity.start(activity, SearchSharePigeonActivity.class, bundle);
    }

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseContext()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_SHARE_PIGEON);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new ShareHallHomeAdapter(false);
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setListPadding(0, 0, 0, 0);
        mViewModel = new ShareHallViewModel();
        initViewModel(mViewModel);
        isMyShare = getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);
        mViewModel.type = isMyShare ? ShareHallViewModel.TYPE_MY : StringUtil.emptyString();

        mSearchTextView.setHint(R.string.text_input_foot_number_search);
        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                if (StringUtil.isStringValid(key)) {
                    saveHistory(key, AppDatabase.TYPE_SEARCH_SHARE_PIGEON);
                    mViewModel.footNum = key;
                    setProgressVisible(true);
                    mViewModel.getSharePigeons();
                }
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            setProgressVisible(true);
            mViewModel.footNum = mSearchHistoryAdapter.getData().get(position).searchTitle;
            mViewModel.getSharePigeons();
        });

        initObserve();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataSharePigeon.observe(this, sharePigeonEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, sharePigeonEntities);
            setProgressVisible(false);
        });
    }
}
