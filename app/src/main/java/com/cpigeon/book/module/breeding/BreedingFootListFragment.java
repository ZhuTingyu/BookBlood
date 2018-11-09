
package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.base.util.picker.PickerUtil;
import com.base.util.utility.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.BreedEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breeding.adapter.BreedingFootAdapter;
import com.cpigeon.book.module.breeding.viewmodel.AddBreedingFragment;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.qqtheme.framework.picker.OptionPicker;


/**
 * 繁育信息   足环列表
 * Created by Administrator on 2018/9/10.
 */

public class BreedingFootListFragment extends BaseFootListFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(R.string.text_breed_info);

        setToolbarRight("▾ "+TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYY), item -> {
            int year=Integer.parseInt(TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYY));
            ArrayList<String> yearlist=new ArrayList<>();
            for(int i=0;i<10;i++)
            {
                yearlist.add(year+"");
                year--;
            }

            PickerUtil.showItemPicker(getBaseActivity(),yearlist
                    , 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            setToolbarRight("▾ "+item);
                            setProgressVisible(true);
                            mPairingInfoListAdapter.getData().clear();
                            mPairingInfoListViewModel.year=item;
                            mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();


                        }
                    });
            return true;
        });

    }

    public static void start(Activity activity) {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        PigeonEntity.TIME=year+"";
        Log.d("songshuaishuai", "start: "+year);
        Bundle bundle = new Bundle();
        bundle.putString(BaseFootListFragment.YEARS, PigeonEntity.TIME);
        SearchFragmentParentActivity.
                start(activity, BreedingFootListFragment.class, false, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPairingInfoListViewModel = new PairingInfoListViewModel();
        initViewModels(mPairingInfoListViewModel);
    }

    @Override
    protected void initBreedData() {
        super.initBreedData();
        mTvOk.setVisibility(View.VISIBLE);
        view_placeholder.setVisibility(View.VISIBLE);
        mTvOk.setText(R.string.array_pairing_add);
        mTvOk.setOnClickListener(v -> {
            //添加配对
            AddBreedingFragment.start(getBaseActivity());

        });
        setStartSearchActvity(SearchBreedingFootActivity.class);//搜索页面
        setProgressVisible(true);
        //mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();
        mRecyclerView.setRefreshListener(() -> {
            Log.d("songshuaishuai", "66666: ");
            setProgressVisible(true);
            mPairingInfoListViewModel.pi=1;
            mPairingInfoListAdapter.getData().clear();
            mPairingInfoListAdapter.notifyDataSetChanged();
            mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();
        });
        mPairingInfoListAdapter = new BreedingFootAdapter(R.layout.breed_manneger_item,null);
        mRecyclerView.addItemDecorationLine(R.color.White,20);
        mRecyclerView.setAdapter(mPairingInfoListAdapter);
        mPairingInfoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    BreedEntity mBreedEntity = mPairingInfoListAdapter.getData().get(position);
                  PairingInfoListFragment.start(getBaseActivity(), mBreedEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        setProgressVisible(true);
        mPairingInfoListViewModel.pi=1;
        mPairingInfoListAdapter.getData().clear();
        mPairingInfoListAdapter.notifyDataSetChanged();
        mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();

    }
}