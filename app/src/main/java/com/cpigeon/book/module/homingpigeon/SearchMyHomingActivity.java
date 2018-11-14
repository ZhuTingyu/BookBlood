package com.cpigeon.book.module.homingpigeon;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;


/**
 * 我的全部信鸽  搜索
 * Created by Administrator on 2018/9/15 0015.
 */

public class SearchMyHomingActivity extends BaseSearchPigeonActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBreedPigeonListModel.stateid = StringUtil.emptyString();
        mRecyclerView.setListPadding(0, 0, 0, 0);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                try {
                    PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                    BreedPigeonDetailsFragment.start(getBaseActivity(),
                            mBreedPigeonEntity.getPigeonID(),
                            mBreedPigeonEntity.getFootRingID());
                    getBaseActivity().finish();
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
        SEARCH_HISTORY_KEY = "search_history_my_homing";
    }
}
