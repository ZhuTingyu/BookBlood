package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.base.base.BaseActivity;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFagment;
import com.cpigeon.book.module.photo.adpter.SelectFootToPhotoAdapter;
import com.cpigeon.book.module.pigeonleague.PigeonMatchDetailsActivity;
import com.cpigeon.book.module.pigeonleague.PigeonToLeagueFootListFragment;
import com.cpigeon.book.module.pigeonleague.SearchPigeonToLeagueActivity;
import com.cpigeon.book.module.pigeonleague.adpter.SelectPigeonToLeagueAdapter;

/**
 * 信鸽赛绩   足环列表
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SelectFootToPhotoFragment extends BaseFootListFagment {


    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, "");
        SearchFragmentParentActivity.
                start(activity, SelectFootToPhotoFragment.class, false, bundle);
    }


    @Override
    protected void initData() {
        super.initData();

        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mAdapter = new SelectFootToPhotoAdapter();

        mActivity.setSearchClickListener(v -> {
            //搜索
            BaseSearchActivity.start(getBaseActivity(), SearchFootToPhotoActivity.class, null);
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
            PigeonPhotoHomeActivity.start(getBaseActivity(), mPigeonEntity);

        });

        mAdapter.addHeaderView(initHead());

    }


    private View initHead() {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_select_foot_to_photo_head, null);
        ProgressBar progressBar = view.findViewById(R.id.progressPlace);
        progressBar.setMax(100);
        progressBar.setProgress(50);
        return view;
    }
}
