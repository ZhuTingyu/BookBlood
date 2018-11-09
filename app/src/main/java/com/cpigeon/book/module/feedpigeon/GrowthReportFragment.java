package com.cpigeon.book.module.feedpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.utility.LogUtil;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.adpter.GrowthReportAdapter;
import com.cpigeon.book.module.feedpigeon.viewmodel.GrowthReportViewModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.FiltrateListView;
import com.google.gson.Gson;

import java.util.List;

/**
 * 成长记录
 * Created by Zhu TingYu on 2018/8/29.
 */

public class GrowthReportFragment extends BaseBookFragment {
    protected FiltrateListView mFiltrate;
    protected DrawerLayout mDrawerLayout;
    protected SearchFragmentParentActivity mActivity;
    protected SelectTypeViewModel mSelectTypeViewModel;
    GrowthReportViewModel mViewModel;
    XRecyclerView mRecyclerView;
    GrowthReportAdapter mAdapter;

    public static void start(Activity activity, PigeonEntity mPigeonEntity,String  puid) {
        Gson gson=new Gson();
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_DATA, gson.toJson(mPigeonEntity));
        bundle.putString(IntentBuilder.KEY_DATA_2, puid);
        SearchFragmentParentActivity.
                start(activity, GrowthReportFragment.class, true, bundle);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) context;
        mSelectTypeViewModel = new SelectTypeViewModel();
        mViewModel = new GrowthReportViewModel();
        initViewModels(mViewModel,mSelectTypeViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String data=getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA);
        mViewModel.mPigeonEntity =new Gson().fromJson(data, PigeonEntity.class);
        mViewModel.puid = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);

        setTitle(mViewModel.mPigeonEntity.getFootRingNum());
        mDrawerLayout = mActivity.getDrawerLayout();
        mFiltrate = mActivity.getFiltrate();
        if (mDrawerLayout == null || mFiltrate == null) {
            return;
        }
        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            if (mDrawerLayout != null) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }

            return false;
        });

        mFiltrate.setOnSureClickListener(selectItems -> {
            LogUtil.print(selectItems);
            mDrawerLayout.closeDrawer(Gravity.RIGHT);


            //年份
            List<SelectTypeEntity> mSelectTypeYear = selectItems.get(0);
            mViewModel.year=SelectTypeEntity.getTypeIds(mSelectTypeYear);
            Log.d("shuaishuai", "onViewCreated: "+mViewModel.year);

            List<SelectTypeEntity> mSelectTypeClassify = selectItems.get(1);
            mViewModel.bitAll=SelectTypeEntity.getTypeIds(mSelectTypeClassify);
            setProgressVisible(true);
            mAdapter.cleanList();
            mViewModel.getTXGP_Pigeon_SelectGrowAllDataNew();

        });

        mSelectTypeViewModel.getClassify();
        mRecyclerView = findViewById(R.id.list);
        LinearLayout search_bg=mActivity.findViewById(R.id.search_bg);
        search_bg.setVisibility(View.GONE);
        composite.add(RxUtils.delayed(50, aLong -> {
            mRecyclerView.setListPadding(20, 0, 20, 0);
        }));
        mAdapter = new GrowthReportAdapter();
        mRecyclerView.setRefreshListener(() -> {
            setProgressVisible(true);
            mAdapter.cleanList();
            mViewModel.pi = 1;

            mViewModel.getTXGP_Pigeon_SelectGrowAllDataNew();
        });
        mRecyclerView.setAdapter(mAdapter);



        setProgressVisible(true);
        mViewModel.getTXGP_Pigeon_SelectGrowAllDataNew();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mViewModel.mGrowthReportListData.observe(this, datas -> {;
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);

            setProgressVisible(false);
        });
        mSelectTypeViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            List<String> titles = Lists.newArrayList("类型");

            if (mFiltrate != null) {
                mFiltrate.setData(true, selectTypeEntities, titles, mSelectTypeViewModel.whichIds);
            }
        });
        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText("没有相关信息！");
        });
    }
}
