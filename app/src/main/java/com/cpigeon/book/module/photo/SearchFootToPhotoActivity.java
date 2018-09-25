package com.cpigeon.book.module.photo;

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
import com.cpigeon.book.module.photo.adpter.SelectFootToPhotoAdapter;
import com.cpigeon.book.module.pigeonleague.PigeonMatchDetailsActivity;
import com.cpigeon.book.module.pigeonleague.adpter.SelectPigeonToLeagueAdapter;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * 信鸽赛绩   足环搜索
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SearchFootToPhotoActivity extends BaseSearchPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectFootToPhotoAdapter();

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
            PigeonPhotoHomeActivity.start(getBaseActivity(), mPigeonEntity);
        });
        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_foot_to_photo";
    }

}