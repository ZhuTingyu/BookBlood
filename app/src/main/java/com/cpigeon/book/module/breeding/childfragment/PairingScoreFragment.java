package com.cpigeon.book.module.breeding.childfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.PairingNestInfoListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingPlayAdapter;
import com.cpigeon.book.module.breeding.adapter.PairingScoreAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingRecommendViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 推荐配对--- 》按评分
 * Created by Administrator on 2018/9/11.
 */

public class PairingScoreFragment extends BaseListFragment {

    private PairingScoreAdapter mAdapter;
    public static final int resultCode = 0x000023;

    private PairingRecommendViewModel mPairingRecommendViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PairingScoreFragment.class);
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


        mPairingRecommendViewModel.mBreedPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        mPairingRecommendViewModel.pigeonid = mPairingRecommendViewModel.mBreedPigeonEntity.getPigeonID();
        if (mPairingRecommendViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雌")) {
            mPairingRecommendViewModel.sex = "雄";
        } else if (mPairingRecommendViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雄")) {
            mPairingRecommendViewModel.sex = "雌";
        } else {
            mPairingRecommendViewModel.sex = "未知";
        }
        mPairingRecommendViewModel.blood = mPairingRecommendViewModel.mBreedPigeonEntity.getPigeonBloodName();


        mAdapter = new PairingScoreAdapter();
        list.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            getBaseActivity().finish();
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {


        });

        list.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mPairingRecommendViewModel.pi = 1;
            mPairingRecommendViewModel.getTXGP_PigeonTrain_RecomSorceData();
        });

//        mAdapter.setOnLoadMoreListener(() -> {
//            mPairingRecommendViewModel.pi++;
//            mPairingRecommendViewModel.getTXGP_PigeonTrain_RecomSorceData();
//        }, list.getRecyclerView());

        setProgressVisible(true);
        mPairingRecommendViewModel.getTXGP_PigeonTrain_RecomSorceData();

    }


    @Override
    protected void initObserve() {
        super.initObserve();

        mPairingRecommendViewModel.mPriringRecommendData.observe(this, breedPigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(list, mAdapter, breedPigeonEntities);
        });

        mPairingRecommendViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });


    }
}
