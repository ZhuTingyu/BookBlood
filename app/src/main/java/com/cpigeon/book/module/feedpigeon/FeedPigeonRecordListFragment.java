package com.cpigeon.book.module.feedpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.feedpigeon.adapter.FeedPigeonRecordListAdapter;

/**
 * 养鸽记录   足环列表
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonRecordListFragment extends BaseFootListFragment {


    SearchFragmentParentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) getActivity();
    }

    public static void start(Activity activity) {

        SearchFragmentParentActivity.
                start(activity, FeedPigeonRecordListFragment.class, false, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();

        setStartSearchActvity(SearchFeedPigeonRecordActivity.class);//搜索页面

        mAdapter = new FeedPigeonRecordListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            try {
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                FeedPigeonDetailsFragment.start(getBaseActivity(), mBreedPigeonEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
