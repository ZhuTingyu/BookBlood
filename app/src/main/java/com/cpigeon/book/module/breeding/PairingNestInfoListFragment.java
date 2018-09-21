package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.adapter.OffspringInfoAdapter;
import com.cpigeon.book.module.breeding.adapter.PairingNestInfoListAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingNestInfoListViewModel;
import com.cpigeon.book.module.breeding.viewmodel.PairingNestViewModel;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 窝次信息列表
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestInfoListFragment extends BaseListFragment {

    private PairingNestInfoListAdapter mAdapter;

    private PairingNestInfoListViewModel mPairingNestInfoListViewModel;
    private PairingNestViewModel mPairingNestViewModel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPairingNestInfoListViewModel = new PairingNestInfoListViewModel();
        mPairingNestViewModel = new PairingNestViewModel();
        initViewModels(mPairingNestInfoListViewModel, mPairingNestViewModel);
    }

    public static void start(Activity activity, PairingInfoEntity mPairingInfoEntity, PigeonEntity mBreedPigeonEntity) {
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
        mPairingNestInfoListViewModel.mBreedPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA_2);

        setTitle(getString(R.string.str_pairing_nest_info));
        setToolbarRight(getString(R.string.str_pairing_nest_add), item -> {
            PairingNestAddFragment.start(getBaseActivity(), mPairingNestInfoListViewModel.mPairingInfoEntity,
                    mPairingNestInfoListViewModel.mBreedPigeonEntity,
                    mAdapter.getData().size());
            return true;
        });

        mAdapter = new PairingNestInfoListAdapter(mPairingNestViewModel);
        list.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

        });

        list.setRefreshListener(() -> {
            initData();
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


    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PAIRING_INFO_REFRESH)) {
            initData();
        }
    }

    private void initData() {
        setProgressVisible(true);
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        mPairingNestInfoListViewModel.pi = 1;
        mPairingNestInfoListViewModel.getgetTXGP_PigeonBreed_SelectIDAll();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PairingNestAddFragment.requestCode) {
            //选择子代后返回
            try {
                PigeonEntity mBreedPigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
                Log.d("hehheheheh", "onActivityResult: " + mBreedPigeonEntity.getFootRingNum());

                PairingNestInfoEntity.PigeonListBean mOffspringInfo = new PairingNestInfoEntity.PigeonListBean.Builder()
                        .FootRingID(mBreedPigeonEntity.getFootRingID())
                        .FootRingNum(mBreedPigeonEntity.getFootRingNum())
                        .PigeonID(mBreedPigeonEntity.getPigeonID())
                        .PigeonPlumeName(mBreedPigeonEntity.getPigeonPlumeName())
                        .build();

                PairingNestInfoEntity item = mAdapter.getData().get(mAdapter.addChildPosition);
                item.getPigeonList().add(mOffspringInfo);

                mPairingNestViewModel.mPairingNestInfoEntity = item;

                OffspringInfoAdapter mOffspringInfoAdapter = new OffspringInfoAdapter();
                mOffspringInfoAdapter.setNewData(item.getPigeonList());
                mPairingNestViewModel.mOffspringInfoAdapter = mOffspringInfoAdapter;

                getBaseActivity().setProgressVisible(true);
                mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
