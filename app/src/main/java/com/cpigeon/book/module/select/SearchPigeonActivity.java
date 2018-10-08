package com.cpigeon.book.module.select;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.select.adpter.SelectPigeonAdapter;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/28.
 */

public class SearchPigeonActivity extends BaseSearchActivity {

    int mRequestCode;
    String mType;

    SelectPigeonAdapter mAdapter;
    BreedPigeonListModel mViewModel;

    public static void start(Activity activity, String type, int code) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_DATA, type);
        bundle.putInt(SelectPigeonFragment.REQUEST_CODE, code);
        BaseSearchActivity.start(activity, SearchPigeonActivity.class, SelectPigeonFragment.CODE_SEARCH, bundle);
    }

    public static void start(Activity activity, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_DATA, type);
        BaseSearchActivity.start(activity, SearchPigeonActivity.class, bundle);
    }

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_SELECT_PIGEON);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectPigeonAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new BreedPigeonListModel();
        initViewModel(mViewModel);
        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                setProgressVisible(true);
                saveHistory(key, AppDatabase.TYPE_SEARCH_SELECT_PIGEON);
                mAdapter.cleanList();
                mViewModel.pi = 1;
                mViewModel.searchStr = key;
                mViewModel.getPigeonList();
            }

            @Override
            public void cancel() {
                finish();
            }
        });

        mRequestCode = getIntent().getIntExtra(SelectPigeonFragment.REQUEST_CODE, 0);
        mType = getIntent().getStringExtra(IntentBuilder.KEY_DATA);

        if (SelectPigeonFragment.TYPE_SHARE_PIGEON_TO_SHARE.equals(mType)) {
            mViewModel.bitshare = BreedPigeonListModel.CODE_IN_NOT_SHARE_HALL;
        }

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity pigeonEntity = mAdapter.getItem(position);
            if (mRequestCode != 0) {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, pigeonEntity)
                        .finishForResult(getBaseActivity());
            } else {
                if (SelectPigeonFragment.TYPE_SHARE_PIGEON_TO_SHARE.equals(mType)) {
                    BreedPigeonDetailsFragment.start(getBaseActivity(), pigeonEntity.getPigeonID()
                            , pigeonEntity.getFootRingID(), BreedPigeonDetailsFragment.TYPE_SHARE_PIGEON, pigeonEntity.getUserID());
                }
            }
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            setProgressVisible(true);
            mViewModel.searchStr = mSearchHistoryAdapter.getItem(position).searchTitle;
            mViewModel.getPigeonList();
        });

        initObserve();
    }

    @Override
    protected void initObserve() {
        mViewModel.mPigeonListData.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, pigeonEntities);
        });
    }
}
