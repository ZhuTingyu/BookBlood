package com.cpigeon.book.module.breeding.childfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
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

    public static final int resultCode = 0x000021;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PairingLineageFragment.class);
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


        mAdapter = new PairingLineageAdapter();
        list.setAdapter(mAdapter);


        list.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mPairingRecommendViewModel.pi = 1;
            mPairingRecommendViewModel.getTXGP_PigeonBreed_RecomBloodData();
        });

//        mAdapter.setOnLoadMoreListener(() -> {
//            mPairingRecommendViewModel.pi++;
//            mPairingRecommendViewModel.getTXGP_PigeonBreed_RecomBloodData();
//        }, list.getRecyclerView());

        setProgressVisible(true);
        mPairingRecommendViewModel.getTXGP_PigeonBreed_RecomBloodData();

    }


    @Override
    protected void initObserve() {
        super.initObserve();

        mPairingRecommendViewModel.mPriringRecommendData.observe(this, breedPigeonEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(list, mAdapter, breedPigeonEntities);
            setProgressVisible(false);
        });

        mPairingRecommendViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }
}
