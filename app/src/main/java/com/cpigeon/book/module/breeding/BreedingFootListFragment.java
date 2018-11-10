
package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.base.util.picker.PickerUtil;
import com.base.util.utility.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
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

public class BreedingFootListFragment extends BaseBookFragment {
    SearchFragmentParentActivity mActivity;
    PairingInfoListViewModel mViewModel;
    protected BreedingFootAdapter mAdapter;
    private LinearLayout search_bg;
    private RelativeLayout rlSearch;


    public static void start(Activity activity) {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        PigeonEntity.TIME = year + "";

        Bundle bundle = new Bundle();
        bundle.putString(BaseFootListFragment.YEARS, PigeonEntity.TIME);
        SearchFragmentParentActivity.
                start(activity, BreedingFootListFragment.class, false, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        search_bg = mActivity.findViewById(R.id.search_bg);
        rlSearch = mActivity.findViewById(R.id.rlSearch);
        search_bg.setBackgroundColor(getResources().getColor(R.color.Gray));
        mViewModel = new PairingInfoListViewModel();
        initViewModels(mViewModel);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_breeding_foot_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(R.string.text_breed_info);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchBreedInfoActivity.class);
        });
        mActivity.setSearchHint(R.string.text_input_foot_number_search);

        view.setBackground(getBaseActivity().getResources().getDrawable(R.color.Gray));
        setToolbarRight("▾ " + TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYY), item -> {
            int year = Integer.parseInt(TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYY));
            ArrayList<String> yearlist = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                yearlist.add(year + "");
                year--;
            }

            PickerUtil.showItemPicker(getBaseActivity(), yearlist
                    , 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            setToolbarRight("▾ " + item);
                            setProgressVisible(true);
                            mAdapter.getData().clear();
                            mViewModel.year = item;
                            mViewModel.getTXGP_PigeonBreed_SelectAll();


                        }
                    });
            return true;
        });

    }


//    protected void initBreedData() {
//        mTvOk.setVisibility(View.VISIBLE);
//        mTvOk.setText("添加配对");
//        mTvOk.setOnClickListener(v -> {
//            //添加配对
//            AddBreedingFragment.start(getBaseActivity());
//
//        });
//        setProgressVisible(true);
//        //mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();
//        mRecyclerView.setRefreshListener(() -> {
//            setProgressVisible(true);
//            mPairingInfoListViewModel.pi = 1;
//            mPairingInfoListAdapter.getData().clear();
//            mPairingInfoListAdapter.notifyDataSetChanged();
//            mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectAll();
//        });
//        mPairingInfoListAdapter = new BreedingFootAdapter();
//        mRecyclerView.addItemDecorationLine(R.color.Gray, 15);
//        mRecyclerView.setAdapter(mPairingInfoListAdapter);
//        mPairingInfoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                try {
//                    BreedEntity mBreedEntity = mPairingInfoListAdapter.getData().get(position);
//                    PairingInfoListFragment.start(getBaseActivity(), mBreedEntity);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mViewModel.mBreedingInfoListData.observe(this, breedPigeonEntities -> {
//            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mPairingInfoListAdapter, breedPigeonEntities);
            setProgressVisible(false);

        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setProgressVisible(true);
        mViewModel.pi = 1;
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        mViewModel.getTXGP_PigeonBreed_SelectAll();

    }
}