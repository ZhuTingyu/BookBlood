package com.cpigeon.book.module.select;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.select.adpter.SelectFootRingAdapter;
import com.cpigeon.book.module.select.viewmodel.SelectFootRingViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/13.
 */

public class SearchFootRingActivity extends BaseSearchActivity {

    SelectFootRingAdapter mAdapter;
    SelectFootRingViewModel mViewModel;

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_FOOT_RING);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectFootRingAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new SelectFootRingViewModel();
        initViewModel(mViewModel);
        mRecyclerView.addItemDecorationLine();

        mSearchTextView.setHint(R.string.text_input_foot_number_search);
        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                setProgressVisible(true);
                mViewModel.footNumber = key;
                mViewModel.getFootList();
                saveHistory(key, AppDatabase.TYPE_SEARCH_FOOT_RING);
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            try {
                mViewModel.footNumber = mSearchHistoryAdapter.getItem(position).searchTitle;
                mViewModel.getFootList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getFootList();
        },mRecyclerView.getRecyclerView());

        initObserve();
    }

    @Override
    protected void initObserve() {
        mViewModel.mDataFootList.observe(this, footEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, footEntities);
            setProgressVisible(false);
        });
    }
}
