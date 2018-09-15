package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingNestInfoListAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingNestInfoListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 窝次信息列表
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestInfoListFragment extends BaseListFragment {

    private PairingNestInfoListAdapter mAdapter;

    private PairingNestInfoListViewModel mPairingNestInfoListViewModel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPairingNestInfoListViewModel = new PairingNestInfoListViewModel();
        initViewModels(mPairingNestInfoListViewModel);
    }

    public static void start(Activity activity, PairingInfoEntity mPairingInfoEntity, BreedPigeonEntity mBreedPigeonEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPairingInfoEntity)
                .putExtra(IntentBuilder.KEY_DATA_2, mBreedPigeonEntity)
                .startParentActivity(activity, PairingNestInfoListFragment.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);


        mPairingNestInfoListViewModel.mPairingInfoEntity = (PairingInfoEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        mPairingNestInfoListViewModel.mBreedPigeonEntity = (BreedPigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA_2);

        setTitle("窝次信息");
        setToolbarRight("添加窝次", item -> {
            PairingNestAddFragment.start(getBaseActivity(), mPairingNestInfoListViewModel.mPairingInfoEntity,
                    mPairingNestInfoListViewModel.mBreedPigeonEntity,
                    mAdapter.getData().size());
            return true;
        });

        mAdapter = new PairingNestInfoListAdapter();
        list.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

        });

        list.setRefreshListener(() -> {
            setProgressVisible(true);
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            mPairingNestInfoListViewModel.pi = 1;
            mPairingNestInfoListViewModel.getgetTXGP_PigeonBreed_SelectIDAll();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            setProgressVisible(true);
            mPairingNestInfoListViewModel.pi++;
            mPairingNestInfoListViewModel.getgetTXGP_PigeonBreed_SelectIDAll();
        }, list.getRecyclerView());

        setProgressVisible(true);
        mPairingNestInfoListViewModel.getgetTXGP_PigeonBreed_SelectIDAll();
    }


    @Override
    protected void initObserve() {
        super.initObserve();

        mPairingNestInfoListViewModel.mPairingNestInfoData.observe(this, datas -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(list, mAdapter, datas);
        });

        mPairingNestInfoListViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

    }
}
