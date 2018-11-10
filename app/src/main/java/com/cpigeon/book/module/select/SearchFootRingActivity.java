package com.cpigeon.book.module.select;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
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
    String SexID;
    boolean mIsCanSetDeath = false;

    public static void start(Activity activity, boolean isCanSetDeath) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, isCanSetDeath);
        BaseSearchActivity.start(activity, SearchFootRingActivity.class, bundle);

    }
    public static void start(Activity activity, boolean isCanSetDeath,String sexID) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_DATA, sexID );
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, isCanSetDeath);
        BaseSearchActivity.start(activity, SearchFootRingActivity.class, bundle);

    }
    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_FOOT_RING);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectFootRingAdapter(mIsCanSetDeath);
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mIsCanSetDeath = getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);
        mViewModel = new SelectFootRingViewModel();
        mViewModel.sexId= getIntent().getStringExtra(IntentBuilder.KEY_DATA);

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

            footEntities.size();
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, footEntities);
            setProgressVisible(false);
        });
        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);

        });
    }
}
