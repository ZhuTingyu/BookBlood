package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.widget.BottomSheetAdapter;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.FootUpdateEvent;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.adapter.FootAdminListAdapter;
import com.cpigeon.book.module.basepigeon.StateListAdapter;
import com.cpigeon.book.module.foot.viewmodel.FootAdminListViewModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.FiltrateListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootAdminListFragment extends BaseBookFragment {


    private XRecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private FiltrateListView mFiltrate;

    private FootAdminListViewModel mViewModel;
    private SelectTypeViewModel mSelectTypeViewModel;
    private FootAdminListAdapter mAdapter;
    private TextView mTvOk;
    SearchFragmentParentActivity mActivity;
    RecyclerView mRvHeadView;
    StateListAdapter mHeadAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new FootAdminListViewModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        initViewModels(mViewModel, mSelectTypeViewModel);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
    }

    public static void start(Activity activity) {
        SearchFragmentParentActivity.start(activity, FootAdminListFragment.class, true, null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getActivity(), SearchFootActivity.class, new Bundle());
        });


        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mDrawerLayout = mActivity.getDrawerLayout();
        mFiltrate = mActivity.getFiltrate();

        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            if (mDrawerLayout != null) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
            return false;
        });

        mAdapter = new FootAdminListAdapter(getBaseActivity());

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mFiltrate.resetData();
            mViewModel.resetData();
            mViewModel.getFoodList();
            mViewModel.getFootRingStat();
        });

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getFoodList();
        }, mRecyclerView.getRecyclerView());

        String[] chooseWays = getResources().getStringArray(R.array.array_choose_input_foot_number);
        mTvOk.setText(R.string.text_add_foot_number);
        mTvOk.setOnClickListener(v -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                if (chooseWays[p].equals(Utils.getString(R.string.text_one_foot_input))) {
                    FootAdminSingleFragment.start(getBaseActivity());
                } else {
                    FootAdminAddMultipleFragment.start(getBaseActivity());
                }
            });
        });

        mFiltrate.setOnSureClickListener(selectItems -> {
            mViewModel.year = Lists.appendStringByList(SelectTypeEntity.getTypeNames(selectItems.get(0)));
            mViewModel.typeid = SelectTypeEntity.getTypeIds(selectItems.get(1));
            mViewModel.stateid = SelectTypeEntity.getTypeIds(selectItems.get(2));
            mAdapter.cleanList();
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            mViewModel.pi = 1;
            mViewModel.getFoodList();
            mViewModel.getFootRingStat();
        });

        setProgressVisible(true);
        mViewModel.getFoodList();
        mViewModel.getFootRingStat();

        mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_FOOT_RING, SelectTypeViewModel.STATE_FOOT_RING);
        mSelectTypeViewModel.getSelectTypes();

        initHeadView();

    }

    private void initHeadView() {
        mRvHeadView = new RecyclerView(getBaseActivity());
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, ScreenTool.dip2px(20), 0, ScreenTool.dip2px(20));
        mRvHeadView.setLayoutParams(params);
        mRvHeadView.setLayoutManager(new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.HORIZONTAL, false));
        mHeadAdapter = new StateListAdapter(Lists.newArrayList(Utils.getApp().getResources().getStringArray(R.array.array_foot_ring_type)));
        mRvHeadView.setAdapter(mHeadAdapter);
    }

    @Override
    protected void initObserve() {
        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.footAdminListData.observe(this, logbookEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, logbookEntities);
            setProgressVisible(false);
        });

        mViewModel.mDataFootStat.observe(this, footRingStatEntity -> {
            mAdapter.removeAllHeaderView();
            mHeadAdapter.setMaxCount(footRingStatEntity.getMaxCount());
            mHeadAdapter.setNewData(footRingStatEntity.getData());
            mAdapter.addHeaderView(mRvHeadView);
        });

        mSelectTypeViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            List<String> titles = Lists.newArrayList(Utils.getString(R.string.text_category)
                    , Utils.getString(R.string.text_is_hand_ring));

            if (mFiltrate != null) {
                mFiltrate.setData(true, selectTypeEntities, titles, mSelectTypeViewModel.whichIds);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(FootUpdateEvent event) {
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getFoodList();
        mViewModel.getFootRingStat();
    }
}
