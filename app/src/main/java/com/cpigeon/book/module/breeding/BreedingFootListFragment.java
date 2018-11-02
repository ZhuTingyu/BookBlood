
package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breeding.adapter.BreedingFootAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import java.util.Calendar;


/**
 * 繁育信息   足环列表
 * Created by Administrator on 2018/9/10.
 */

public class BreedingFootListFragment extends BaseFootListFragment {


    public static void start(Activity activity) {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        PigeonEntity.TIME=year+"";
        Log.d("songshuaishuai", "start: "+year);
        Bundle bundle = new Bundle();
        bundle.putString(BaseFootListFragment.YEARS, PigeonEntity.TIME);
        SearchFragmentParentActivity.
                start(activity, BreedingFootListFragment.class, true, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPairingInfoListViewModel = new PairingInfoListViewModel();
        initViewModels(mPairingInfoListViewModel);
    }

    @Override
    protected void initBreedData() {
        super.initBreedData();;
        setStartSearchActvity(SearchBreedingFootActivity.class);//搜索页面
        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);
        setProgressVisible(true);
        mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();
        mRecyclerView.setRefreshListener(() -> {
            Log.d("songshuaishuai", "66666: ");
            setProgressVisible(true);
            mPairingInfoListAdapter.getData().clear();
            mPairingInfoListAdapter.notifyDataSetChanged();
            mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();
        });
        mPairingInfoListAdapter = new BreedingFootAdapter(R.layout.breed_manneger_item,null);

        mRecyclerView.setAdapter(mPairingInfoListAdapter);


//        mActivity.setSearchClickListener(v -> {
//            //搜索
//            BaseSearchActivity.start(getBaseActivity(), .class, null);
//        });
//        mPairingInfoListAdapter.setOnLoadMoreListener(() -> {
//            setProgressVisible(true);
//            mPairingInfoListViewModel.pi++;
//            mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();;
//        }, mRecyclerView.getRecyclerView());



    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mPairingInfoListViewModel.mBreedingInfoListData.observe(this, breedPigeonEntities -> {
            Log.d("shuaishuai", "initObserve: "+breedPigeonEntities.size());
           RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mPairingInfoListAdapter, breedPigeonEntities);
            mPairingInfoListAdapter.notifyDataSetChanged();
            setProgressVisible(false);

        });

        mPairingInfoListViewModel.listEmptyMessage.observe(this, s -> {
            mPairingInfoListAdapter.setEmptyText(s);
        });
    }
}