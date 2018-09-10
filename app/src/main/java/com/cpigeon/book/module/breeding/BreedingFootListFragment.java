package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFagment;
import com.cpigeon.book.module.breedpigeon.SearchBreedPigeonActivity;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 繁育信息   足环列表
 * Created by Administrator on 2018/9/10.
 */

public class BreedingFootListFragment extends BaseFootListFagment {


    private SearchFragmentParentActivity mActivity;

    public static void start(Activity activity) {
        SearchFragmentParentActivity.
                start(activity, BreedingFootListFragment.class, false, null);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) context;
    }

    @Override
    protected void initData() {
        super.initData();
        setProgressVisible(false);
        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mActivity.setSearchHint(R.string.text_input_foot_number_search);

        mActivity.setSearchClickListener(v -> {
            //搜索
            Bundle bundle = new Bundle();
            bundle.putString(IntentBuilder.KEY_TYPE, "8");

            BaseSearchActivity.start(getBaseActivity(), SearchBreedPigeonActivity.class, bundle);
        });

        RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, Lists.newArrayList(new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build(),
                new BreedPigeonEntity.Builder().build()
        ));


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            BreedPigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            PairingInfoListFragment.start(getBaseActivity(),
                    mBreedPigeonEntity.getPigeonID(),
                    mBreedPigeonEntity.getFootRingID());
        });
    }
}