package com.cpigeon.book.module.breeding;

import android.content.Intent;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;

/**
 * 子代  足环搜索
 * Created by Administrator on 2018/9/19 0019.
 */

public class OffspringSearchActivity extends BaseSearchPigeonActivity {

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new BreedPigeonListAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id=PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                Intent intent = new Intent();
                intent.putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity);
                getBaseActivity().setResult(PairingNestAddFragment.requestCode, intent);
                getBaseActivity().finish();
            }
        });

        return mAdapter;
    }


    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_offspring";
    }
}
