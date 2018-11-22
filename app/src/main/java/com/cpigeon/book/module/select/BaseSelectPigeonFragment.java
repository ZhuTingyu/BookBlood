package com.cpigeon.book.module.select;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.LogUtil;
import com.base.util.utility.StringUtil;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;
import com.cpigeon.book.module.select.adpter.ChooseAdapter;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.filtrate.FiltrateListView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/26.
 */

public abstract class BaseSelectPigeonFragment extends BaseBookFragment {
    //选择鸽子去共享
    public static String TYPE_SHARE_PIGEON_TO_SHARE = "TYPE_SHARE_PIGEON_TO_SHARE";
    //选择鸽子添加种鸽
    public static String TYPE_SELECT_PIGEON_TO_ADD_BREED_PIGEON = "TYPE_SELECT_PIGEON_TO_ADD_BREED_PIGEON";
    //选择鸽子申请铭鸽库
    public static String TYPE_SELECT_PIGEON_TO_GOOD_PIGEON= "TYPE_SELECT_PIGEON_TO_GOOD_PIGEON";
    //选择鸽子去寻根
    public static String TYPE_SELECT_PIGEON_TO_FIND_BLOOD= "TYPE_SELECT_PIGEON_TO_GOOD_PIGEON";
    public static int CODE_SEARCH = 0x321;

    protected XRecyclerView mRecyclerView;
    protected FiltrateListView mFiltrate;
    protected DrawerLayout mDrawerLayout;
    protected MyHomingPigeonAdapter mAdapter;
    protected BreedPigeonListModel mViewModel;
    protected SearchFragmentParentActivity mActivity;
    protected SelectTypeViewModel mSelectTypeViewModel;
    protected String mType;

    @Override

    public void onAttach(Context context) {

        super.onAttach(context);

        mViewModel = new BreedPigeonListModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        initViewModels(mViewModel, mSelectTypeViewModel);
        mActivity = (SearchFragmentParentActivity) context;
        mViewModel.typeid = StringUtil.emptyString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            startSearchActivity();
        });

        mDrawerLayout = mActivity.getDrawerLayout();
        mFiltrate = mActivity.getFiltrate();

        if (mDrawerLayout != null && mFiltrate != null) {
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
                mViewModel.pi = 1;
                mViewModel.isSearch = false;
                mAdapter.cleanList();

                //年份
                List<SelectTypeEntity> mSelectTypeYear = selectItems.get(0);
                mViewModel.year = SelectTypeEntity.getTypeName(mSelectTypeYear);

                //性别
                List<SelectTypeEntity> mSelectTypeSex = selectItems.get(1);
                mViewModel.sexid = SelectTypeEntity.getTypeIds(mSelectTypeSex);

                //状态
                List<SelectTypeEntity> mSelectTypeStatus = selectItems.get(2);
                mViewModel.stateid = SelectTypeEntity.getTypeIds(mSelectTypeStatus);

                //血统
                List<SelectTypeEntity> mSelectTypeLineage = selectItems.get(3);
                mViewModel.bloodid = SelectTypeEntity.getTypeIds(mSelectTypeLineage);

                mViewModel.getPigeonList();
                mViewModel.getPigeonCount();

            });

            mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_SEX, SelectTypeViewModel.STATE_STATE, SelectTypeViewModel.TYPE_PIGEON_BLOOD);
            mSelectTypeViewModel.getSelectTypes();
        }

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setBackgroundColor(Utils.getColor(R.color.white));
        mRecyclerView.addItemDecorationLine(20);
        mAdapter = new MyHomingPigeonAdapter();
        mAdapter.setOnInItemClickListener(this::setAdapterClick);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getPigeonList();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());

        setProgressVisible(true);
        setTypeParam();
        mViewModel.getPigeonList();
    }

    protected void setAdapterClick(MyHomingPigeonAdapter adapter, View view, int position) {
        PigeonEntity pigeonEntity = mAdapter.getItem(position);
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonEntity)
                .finishForResult(getBaseActivity());
    }

    protected abstract void setTypeParam();


    public abstract void startSearchActivity();

    @Override
    protected void initObserve() {
        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mPigeonListData.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, pigeonEntities);
        });
        mSelectTypeViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            List<String> titles = Lists.newArrayList(Utils.getString(R.string.text_sex)
                    , Utils.getString(R.string.text_pigeon_status), Utils.getString(R.string.text_pigeon_blood));

            if (mFiltrate != null) {
                mFiltrate.setData(true, selectTypeEntities, titles, mSelectTypeViewModel.whichIds);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) return;
        PigeonEntity pigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonEntity)
                .finishForResult(getBaseActivity());

    }

    @LayoutRes
    public int getAdapterLayout() {
        return R.layout.item_select_pigeon;
    }
}
