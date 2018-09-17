package com.cpigeon.book.module.basepigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonListFragment;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/10.
 */

public class BaseFootListFagment extends BaseBookFragment {


    @BindView(R.id.list)
    protected XRecyclerView mRecyclerView;
    @BindView(R.id.tvOk)
    protected TextView mTvOk;
    @BindView(R.id.view_placeholder)
    protected View view_placeholder;


    protected BreedPigeonListAdapter mAdapter;


    protected SelectTypeViewModel mSelectTypeViewModel;

    protected BreedPigeonListModel mBreedPigeonListModel;

    public static void start(Activity activity, String pigeonType) {

        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, pigeonType);
        SearchFragmentParentActivity.
                start(activity, BreedPigeonListFragment.class, true, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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


        mBreedPigeonListModel.typeid = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
        mAdapter = new BreedPigeonListAdapter();

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setRefreshListener(() -> {
            setProgressVisible(true);
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.getPigeonList();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            setProgressVisible(true);
            mBreedPigeonListModel.pi++;
            mBreedPigeonListModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());

        setProgressVisible(true);
        mBreedPigeonListModel.getPigeonList();

        initData();
    }

    protected void initData() {

    }

    @Override
    protected void initObserve() {

        mBreedPigeonListModel.mPigeonListData.observe(this, datas -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mBreedPigeonListModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

    }
}
