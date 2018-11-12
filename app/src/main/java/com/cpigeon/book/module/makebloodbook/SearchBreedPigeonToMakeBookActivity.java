package com.cpigeon.book.module.makebloodbook;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.db.AppDatabase;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;

/**
 * * 血统书制作  足环搜索
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SearchBreedPigeonToMakeBookActivity extends BaseSearchPigeonActivity {


    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new BreedPigeonListAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                PreviewsBookActivity.start(getBaseActivity(), mAdapter.getItem(position));
            }
        });

        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = AppDatabase.TYPE_SEARCH_BREED_PIGEON;
    }
}
