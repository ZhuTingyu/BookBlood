package com.cpigeon.book.module.pigeonleague;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.pigeonleague.adpter.SelectPigeonToLeagueAdapter;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * 信鸽赛绩  搜索足环列表
 * Created by Zhu TingYu on 2018/9/14.
 */

public class SearchPigeonToLeagueActivity extends BaseSearchPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectPigeonToLeagueAdapter();

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            PigeonMatchDetailsActivity.start(getBaseActivity(), mBreedPigeonEntity);
        });

        return mAdapter;
    }


    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_pigeon_to_league";
    }

}
