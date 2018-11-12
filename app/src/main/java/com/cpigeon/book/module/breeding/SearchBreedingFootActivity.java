package com.cpigeon.book.module.breeding;

import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;


/**
 * Created by Administrator on 2018/9/15 0015.
 */

public class SearchBreedingFootActivity extends BaseSearchPigeonActivity {


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
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                BreedPigeonDetailsFragment.start(getBaseActivity(),
                        mBreedPigeonEntity.getPigeonID(),
                        mBreedPigeonEntity.getFootRingID());
                getBaseActivity().finish();
            }
        });

        return mAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        SEARCH_HISTORY_KEY = "search_history_breeding";
    }
}
