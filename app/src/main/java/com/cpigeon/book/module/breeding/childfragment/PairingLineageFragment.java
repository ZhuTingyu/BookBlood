package com.cpigeon.book.module.breeding.childfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.PairingNestInfoListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingInfoListAdapter;
import com.cpigeon.book.module.breeding.adapter.PairingLineageAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingRecommendViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 推荐配对--- 》按血统
 * Created by Administrator on 2018/9/11.
 */

public class PairingLineageFragment extends BaseListFragment {

    private PairingLineageAdapter mAdapter;
    private PairingRecommendViewModel mPairingRecommendViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PairingNestInfoListFragment.class);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPairingRecommendViewModel = new PairingRecommendViewModel();
        initViewModels(mPairingRecommendViewModel);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mAdapter = new PairingLineageAdapter();
        list.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            getBaseActivity().finish();
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {


        });

        list.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mPairingRecommendViewModel.pi = 1;
            mPairingRecommendViewModel.getTXGP_PigeonBreed_RecomBloodData();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mPairingRecommendViewModel.pi++;
            mPairingRecommendViewModel.getTXGP_PigeonBreed_RecomBloodData();
        }, list.getRecyclerView());

        setProgressVisible(true);
        mPairingRecommendViewModel.getTXGP_PigeonBreed_RecomBloodData();

    }


    @Override
    protected void initObserve() {
        super.initObserve();

        mPairingRecommendViewModel.mPairingInfoListData.observe(this, breedPigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(list, mAdapter, breedPigeonEntities);
        });

        mPairingRecommendViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });


    }
}
