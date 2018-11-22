package com.cpigeon.book.module.breeding;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;

import static com.cpigeon.book.module.breeding.viewmodel.AddBreedingFragment.CODE_ADD_BREEDING;

/**
 * Created by Zhu TingYu on 2018/11/13.
 */

public class SearchPigeonToAddBreedingActivity extends BaseSearchPigeonActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBreedPigeonListModel.bitTogether = PigeonEntity.STATUS_NOT_TOGETHER;
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                setProgressVisible(true);
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                try {
                    PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);

                    PairingInfoAddFragment.start(getBaseActivity(), mBreedPigeonEntity, null, CODE_ADD_BREEDING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_to_add_breeding";
    }


}
