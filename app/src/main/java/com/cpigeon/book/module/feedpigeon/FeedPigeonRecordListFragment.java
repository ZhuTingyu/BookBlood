package com.cpigeon.book.module.feedpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.feedpigeon.adapter.FeedPigeonRecordListAdapter;
import com.cpigeon.book.module.pigeonleague.PigeonToLeagueFootListFragment;

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

        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, "");
        SearchFragmentParentActivity.
                start(activity, PigeonToLeagueFootListFragment.class, false, bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecorationLine();

    }

    @Override
    protected void initData() {
        super.initData();
        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mActivity.setSearchClickListener(v -> {
            Bundle mBundle = new Bundle();
            mBundle.putString(IntentBuilder.KEY_TYPE, "");
            BaseSearchActivity.start(getBaseActivity(), SearchFeedPigeonRecordActivity.class, mBundle);
        });

        mAdapter = new FeedPigeonRecordListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            FeedPigeonDetailsFragment.start(getBaseActivity(), mBreedPigeonEntity);
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecorationLine();
    }
}
