package com.cpigeon.book.module.select;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.findblood.FindPigeonBloodFragment;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/28.
 */

public class SearchPigeonActivity extends BaseSearchActivity {

    String mType;

    MyHomingPigeonAdapter mAdapter;
    BreedPigeonListModel mViewModel;

    //
    public static void start(Activity activity, String type, Bundle bundleOther) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, type);

        if (bundleOther != null) {
            bundle.putAll(bundleOther);
        }
        BaseSearchActivity.start(activity, SearchPigeonActivity.class, BaseSelectPigeonFragment.CODE_SEARCH, bundle);
    }

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_SELECT_PIGEON);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new MyHomingPigeonAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mType = getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
        super.onCreate(savedInstanceState);
        mRecyclerView.setListPadding(0, 0, 0, 0);
        mViewModel = new BreedPigeonListModel();
        initViewModel(mViewModel);

        mSearchTextView.setHint(R.string.text_input_foot_number_search);
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

        //共享厅
        if (BaseSelectPigeonFragment.TYPE_SHARE_PIGEON_TO_SHARE.equals(mType)) {
            mViewModel.bitshare = BreedPigeonListModel.CODE_IN_NOT_SHARE_HALL;
        } else if (BaseSelectPigeonFragment.TYPE_SELECT_PIGEON_TO_ADD_BREED_PIGEON.equals(mType)) {
            mViewModel.sexid = getIntent().getStringExtra(IntentBuilder.KEY_DATA);
            mViewModel.pigeonidStr = getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);

        }

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());

        mAdapter.setOnInItemClickListener((myAdapter, view, position) -> {
            try {
                PigeonEntity pigeonEntity = mAdapter.getItem(position);
                //共享厅
                if (BaseSelectPigeonFragment.TYPE_SHARE_PIGEON_TO_SHARE.equals(mType)) {
                    BreedPigeonDetailsFragment.start(getBaseActivity(), pigeonEntity.getPigeonID()
                            , pigeonEntity.getFootRingID(), BreedPigeonDetailsFragment.TYPE_SHARE_PIGEON, pigeonEntity.getUserID());
                } else if(BaseSelectPigeonFragment.TYPE_SELECT_PIGEON_TO_FIND_BLOOD.equals(mType)){
                    FindPigeonBloodFragment.start(getBaseActivity(), pigeonEntity);
                }else {
                    IntentBuilder.Builder()
                            .putExtra(IntentBuilder.KEY_DATA, pigeonEntity)
                            .finishForResult(getBaseActivity());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            try {
                setProgressVisible(true);
                mViewModel.searchStr = mSearchHistoryAdapter.getItem(position).searchTitle;
                mViewModel.getPigeonList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        initObserve();
    }

    @Override
    protected void initObserve() {
        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mPigeonListData.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, pigeonEntities);
        });
    }

    @LayoutRes
    public int getAdapterLayout() {
        return R.layout.item_select_pigeon;
    }
}
