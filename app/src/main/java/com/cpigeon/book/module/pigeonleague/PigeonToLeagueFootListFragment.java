package com.cpigeon.book.module.pigeonleague;

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
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFagment;
import com.cpigeon.book.module.breeding.BreedingFootListFragment;
import com.cpigeon.book.module.breeding.PairingInfoListFragment;
import com.cpigeon.book.module.breeding.SearchBreedingFootActivity;
import com.cpigeon.book.module.pigeonleague.adpter.SelectPigeonToLeagueAdapter;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 信鸽赛绩  足环列表
 * Created by Zhu TingYu on 2018/9/12.
 */

public class PigeonToLeagueFootListFragment extends BaseFootListFagment {


    public static void start(Activity activity) {

        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, "");
        bundle.putString(IntentBuilder.KEY_TYPE_2, PigeonEntity.BIT_MATCH_NO);
        SearchFragmentParentActivity.
                start(activity, PigeonToLeagueFootListFragment.class, false, bundle);
    }

    @Override
    protected void initData() {
        super.initData();

        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mAdapter = new SelectPigeonToLeagueAdapter();

        mActivity.setSearchClickListener(v -> {
            //搜索
            Bundle bundle = new Bundle();
            bundle.putString(IntentBuilder.KEY_TYPE_2, PigeonEntity.BIT_MATCH_NO);
            BaseSearchActivity.start(getBaseActivity(), SearchPigeonToLeagueActivity.class, bundle);
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            PigeonMatchDetailsActivity.start(getBaseActivity(), mBreedPigeonEntity.getPigeonID());
        });

        mAdapter.addHeaderView(initHead());

    }


    private View initHead() {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_select_pigeon_to_league_head, null);
        return view;
    }
}
