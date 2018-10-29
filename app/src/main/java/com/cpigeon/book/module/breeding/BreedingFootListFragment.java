
package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.view.View;

import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;


/**
 * 繁育信息   足环列表
 * Created by Administrator on 2018/9/10.
 */

public class BreedingFootListFragment extends BaseFootListFragment {

    public static void start(Activity activity) {
        SearchFragmentParentActivity.
                start(activity, BreedingFootListFragment.class, false, null);
    }

    @Override
    protected void initData() {
        super.initData();
        setStartSearchActvity(SearchBreedingFootActivity.class);//搜索页面
        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

//        mActivity.setSearchClickListener(v -> {
//            //搜索
//            BaseSearchActivity.start(getBaseActivity(), .class, null);
//        });
    mAdapter=new MyHomingPigeonAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                PairingInfoListFragment.start(getBaseActivity(), mBreedPigeonEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}