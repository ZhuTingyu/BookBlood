package com.cpigeon.book.module.pigeonleague;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.pigeonleague.adpter.SelectPigeonToLeagueAdapter;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/14.
 */

public class SearchPigeonToLeagueActivity extends BaseSearchActivity {

    SelectPigeonToLeagueAdapter mAdapter;

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity())
                .DbEntityDao().getDataByUserAndType(UserModel.getInstance().getUserId()
                        , AppDatabase.TYPE_SEARCH_PIGEON_TO_LEAGUE);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectPigeonToLeagueAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                mAdapter.setNewData(Lists.newTestArrayList());
            }

            @Override
            public void cancel() {
                finish();
            }
        });
    }
}
