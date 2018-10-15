package com.cpigeon.book.module.photo;

import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.photo.adpter.SelectFootToPhotoAdapter;

/**
 * 信鸽赛绩   足环搜索
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SearchFootToPhotoActivity extends BaseSearchPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectFootToPhotoAdapter();

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
                PigeonPhotoHomeActivity.start(getBaseActivity(), mPigeonEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_foot_to_photo";
    }

}