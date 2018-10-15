package com.cpigeon.book.module.basepigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.LogUtil;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breeding.SearchBreedingFootActivity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.FiltrateListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/10.
 */

public class BaseFootListFragment extends BaseBookFragment {


    @BindView(R.id.list)
    protected XRecyclerView mRecyclerView;
    @BindView(R.id.tvOk)
    protected TextView mTvOk;
    @BindView(R.id.view_placeholder)
    protected View view_placeholder;

    protected BasePigeonListAdapter mAdapter;
    protected SelectTypeViewModel mSelectTypeViewModel;

    protected BreedPigeonListModel mBreedPigeonListModel;
    protected SearchFragmentParentActivity mActivity;

    protected FiltrateListView mFiltrate;
    protected DrawerLayout mDrawerLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) context;

        mSelectTypeViewModel = new SelectTypeViewModel();
        mBreedPigeonListModel = new BreedPigeonListModel();

        initViewModels(mSelectTypeViewModel, mBreedPigeonListModel);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setSearchHint(R.string.text_input_foot_number_search);

        mBreedPigeonListModel.typeid = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
        mBreedPigeonListModel.bitmatch = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE_2);

        mRecyclerView.setRefreshListener(() -> {
            setProgressVisible(true);
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.getPigeonList();
        });


        setProgressVisible(true);
        mBreedPigeonListModel.getPigeonList();

        mActivity.setSearchClickListener(v -> {
            //搜索
            Bundle bundle = new Bundle();
            bundle.putString(IntentBuilder.KEY_TYPE, mBreedPigeonListModel.typeid);
            BaseSearchActivity.start(getBaseActivity(), SearchBreedingFootActivity.class, bundle);
        });

        mAdapter = new BreedPigeonListAdapter();

        initData();

        mRecyclerView.addItemDecorationLine(20);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(() -> {
            setProgressVisible(true);
            mBreedPigeonListModel.pi++;
            mBreedPigeonListModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());


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

            setProgressVisible(true);
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.isSearch = false;
            mAdapter.cleanList();

            //年份
            List<SelectTypeEntity> mSelectTypeYear = selectItems.get(0);
            mBreedPigeonListModel.year = SelectTypeEntity.getTypeName(mSelectTypeYear);

            //性别
            List<SelectTypeEntity> mSelectTypeSex = selectItems.get(1);
            mBreedPigeonListModel.sexid = SelectTypeEntity.getTypeIds(mSelectTypeSex);

            //状态
            List<SelectTypeEntity> mSelectTypeStatus = selectItems.get(2);
            mBreedPigeonListModel.stateid = SelectTypeEntity.getTypeIds(mSelectTypeStatus);

            //血统
            List<SelectTypeEntity> mSelectTypeLineage = selectItems.get(3);
            mBreedPigeonListModel.bloodid = SelectTypeEntity.getTypeIds(mSelectTypeLineage);

            mBreedPigeonListModel.getPigeonList();
            mBreedPigeonListModel.getPigeonCount();

        });

        mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_SEX, SelectTypeViewModel.STATE_STATE, SelectTypeViewModel.TYPE_PIGEON_BLOOD);
        mSelectTypeViewModel.getSelectTypes();

    }


    protected void initData() {

    }

    @Override
    protected void initObserve() {

        mSelectTypeViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            List<String> titles = Lists.newArrayList(Utils.getString(R.string.text_sex)
                    , Utils.getString(R.string.text_pigeon_status), Utils.getString(R.string.text_pigeon_blood));

            if (mFiltrate != null) {
                mFiltrate.setData(true, selectTypeEntities, titles, mSelectTypeViewModel.whichIds);
            }
        });

        mBreedPigeonListModel.mPigeonListData.observe(this, datas -> {

            if (datas.isEmpty() || datas.size() == 0) {

            } else {
                if (mAdapter.getHeaderViewsCount() == 0) {
                    initHeadView();
                }
            }

            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);

            setProgressVisible(false);
        });

        mBreedPigeonListModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }

    protected void initHeadView() {
    }
}
