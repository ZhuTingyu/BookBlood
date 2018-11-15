
package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.picker.PickerUtil;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breeding.adapter.BreedingFootAdapter;
import com.cpigeon.book.module.breeding.viewmodel.AddBreedingFragment;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.Subscribe;

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
    protected BreedingFootAdapter mAdapter1;
    protected BreedingFootAdapter mAdapter2;
    private LinearLayout search_bg;
    private RelativeLayout rlSearch;

    private RelativeLayout mRlAddition1;
    private ImageView mImgExpand1;
    private RecyclerView mList1;
    private RelativeLayout mRlAddition2;
    private ImageView mImgExpand2;
    private RecyclerView mList2;
    private View mViewPlaceholder;
    private TextView mTvOk;

    private boolean mTIsExpand = true;
    private boolean mFIsExpand = true;


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
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
        search_bg = mActivity.findViewById(R.id.search_bg);
        rlSearch = mActivity.findViewById(R.id.rlSearch);
        search_bg.setBackgroundColor(getResources().getColor(R.color.Gray));
        mViewModel = new PairingInfoListViewModel();
        initViewModels(mViewModel);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_breeding_foot_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(R.string.text_breed_manager);
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
                            mAdapter1.getData().clear();
                            mAdapter2.getData().clear();
                            mViewModel.year = item;
                            mViewModel.getTXGP_PigeonBreed_SelectAll();


                        }
                    });
            return true;
        });

        mRlAddition1 = findViewById(R.id.rlAddition1);
        mImgExpand1 = findViewById(R.id.imgExpand1);
        mList1 = findViewById(R.id.list1);
        mRlAddition2 = findViewById(R.id.rlAddition2);
        mImgExpand2 = findViewById(R.id.imgExpand2);
        mList2 = findViewById(R.id.list2);
        mViewPlaceholder = findViewById(R.id.view_placeholder);
        mTvOk = findViewById(R.id.tvOk);

        mList1.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mList2.setLayoutManager(new LinearLayoutManager(getBaseActivity()));

        mAdapter1 = new BreedingFootAdapter(mViewModel);
        mAdapter2 = new BreedingFootAdapter(mViewModel);

        mList1.setAdapter(mAdapter1);
        mList2.setAdapter(mAdapter2);

        mTvOk.setText("添加配对");
        mTvOk.setOnClickListener(v -> {
            //添加配对
            AddBreedingFragment.start(getBaseActivity());
        });

        mRlAddition1.setOnClickListener(v -> {
            if (mTIsExpand) {
                mTIsExpand = false;
                mImgExpand1.setRotation(0);
                mList1.setVisibility(View.GONE);
            } else {
                mTIsExpand = true;
                mImgExpand1.setRotation(180);
                mList1.setVisibility(View.VISIBLE);
            }
        });

        mRlAddition2.setOnClickListener(v -> {
            if (mFIsExpand) {
                mFIsExpand = false;
                mImgExpand2.setRotation(0);
                mList2.setVisibility(View.GONE);
            } else {
                mFIsExpand = true;
                mImgExpand2.setRotation(180);
                mList2.setVisibility(View.VISIBLE);
            }
        });
        mImgExpand1.setRotation(180);
        mImgExpand2.setRotation(180);
        setProgressVisible(true);
        mViewModel.getTXGP_PigeonBreed_SelectAll();
    }

    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PAIRING_INFO_REFRESH)) {
            setProgressVisible(true);
            mViewModel.getTXGP_PigeonBreed_SelectAll();
        }
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mViewModel.mBreedingInfoListDataT.observe(this, breedPigeonEntities -> {
            setProgressVisible(false);
            mAdapter1.setNewData(breedPigeonEntities);
        });

        mViewModel.mBreedingInfoListDataF.observe(this, breedPigeonEntities -> {
            setProgressVisible(false);
            mAdapter2.setNewData(breedPigeonEntities);
        });

        mViewModel.mDataSetTogetherR.observe(this, aBoolean -> {
            setProgressVisible(true);
            mViewModel.getTXGP_PigeonBreed_SelectAll();
        });

        mViewModel.mDataDeletR.observe(this, aBoolean -> {
            setProgressVisible(true);
            mViewModel.getTXGP_PigeonBreed_SelectAll();
        });
    }
}