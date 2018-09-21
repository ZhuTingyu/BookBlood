package com.cpigeon.book.module.feedpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFagment;
import com.cpigeon.book.module.feedpigeon.adapter.FeedPigeonRecordListAdapter;
import com.cpigeon.book.module.pigeonleague.PigeonToLeagueFootListFragment;

/**
 * 养鸽记录   足环列表
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonRecordListFragment extends BaseFootListFagment {


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
