package com.cpigeon.book.module.basepigeon;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
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
    protected BreedPigeonListModel mBreedPigeonListModel;
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

        initParameter();//初始化请求的参数

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

    ////初始化请求的参数
    private void initParameter() {
        try {
            mBreedPigeonListModel.typeid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.TYPEID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bloodid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BLOODID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.sexid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.SEXID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.year = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.YEAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.stateid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.STATEID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.eyeid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.EYEID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.searchStr = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.FOOTNUM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bitmatch = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITMATCH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bitbreed = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITBREED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.pigeonidStr = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.PIGEONIDSTR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bitshare = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITSHARE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bitMotto = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITMOTTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
