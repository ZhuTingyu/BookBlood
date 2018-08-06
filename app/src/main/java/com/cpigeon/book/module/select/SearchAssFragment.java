package com.cpigeon.book.module.select;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.base.BaseSearchFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.AssEntity;
import com.cpigeon.book.module.select.adpter.SearchAssAdapter;
import com.cpigeon.book.module.select.adpter.SearchAssHistoryAdapter;
import com.cpigeon.book.widget.SearchTextView;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class SearchAssFragment extends BaseSearchFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    SearchAssAdapter mSearchAssAdapter;
    SearchAssHistoryAdapter mAssHistoryAdapter;

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mSearchAssAdapter = new SearchAssAdapter();
        return mSearchAssAdapter;
    }

    @Override
    protected BaseQuickAdapter getHistoryAdapter() {
        mAssHistoryAdapter = new SearchAssHistoryAdapter();
        return mAssHistoryAdapter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SelectAssActivity activity = (SelectAssActivity) getBaseActivity();

        List<DbEntity> history = AppDatabase.getInstance(getBaseActivity())
                .DbEntityDao().getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_ASS_HISTORY);

        if(!Lists.isEmpty(history)){
            mRlHistory.setVisibility(View.VISIBLE);
            mAssHistoryAdapter.setNewData(AppDatabase.getDatas(history, AssEntity.class));
        }else {
            mRlHistory.setVisibility(View.GONE);
        }



        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {

            }

            @Override
            public void cancel() {
                activity.hideSearch();
            }
        });
    }


}
