package com.cpigeon.book.module.basepigeon;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BaseSearchPigeonActivity extends BaseSearchActivity {

    protected BasePigeonListAdapter mAdapter;

    private BreedPigeonListModel mBreedPigeonListModel;
    protected String SEARCH_HISTORY_KEY;

    @Override
    protected List<DbEntity> getHistory() {

        if (StringUtil.isStringValid(SEARCH_HISTORY_KEY)) {
            return AppDatabase.getInstance(getBaseContext()).DbEntityDao()
                    .getDataByUserAndType(UserModel.getInstance().getUserId(), SEARCH_HISTORY_KEY);
        } else {
            return Lists.newArrayList();
        }

    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new BreedPigeonListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                BreedPigeonDetailsFragment.start(getBaseActivity(), mBreedPigeonEntity.getPigeonID(), mBreedPigeonEntity.getFootRingID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initData();
        super.onCreate(savedInstanceState);

        mBreedPigeonListModel = new BreedPigeonListModel();
        initViewModel(mBreedPigeonListModel);

        try {
            mBreedPigeonListModel.typeid = getIntent().getExtras().getString(IntentBuilder.KEY_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mBreedPigeonListModel.bitmatch = getIntent().getExtras().getString(IntentBuilder.KEY_TYPE_2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBreedPigeonListModel.isSearch = true;
        mSearchTextView.setHint(R.string.text_input_foot_number_search);
        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                setRefreshData(key);
                if (StringUtil.isStringValid(SEARCH_HISTORY_KEY)) {
                    saveHistory(key, SEARCH_HISTORY_KEY);
                }
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            setProgressVisible(true);
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.getPigeonList();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            setProgressVisible(true);
            mBreedPigeonListModel.pi++;
            mBreedPigeonListModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());


        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            goneHistroy();
            setRefreshData(mSearchHistoryAdapter.getItem(position).searchTitle);
        });

        initObserve();

    }

    protected void initData() {
    }

    private void setRefreshData(String key) {
        setProgressVisible(true);
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        mBreedPigeonListModel.searchStr = key;
        mBreedPigeonListModel.pi = 1;
        mBreedPigeonListModel.getPigeonList();
    }


    protected void initObserve() {

        mBreedPigeonListModel.mPigeonListData.observe(this, datas -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
            setProgressVisible(false);
        });

        mBreedPigeonListModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }
}
