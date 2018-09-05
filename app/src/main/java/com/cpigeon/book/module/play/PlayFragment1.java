package com.cpigeon.book.module.play;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.module.breed.viewmodel.BreedPigeonDetailsViewModel;
import com.cpigeon.book.module.play.adapter.PlayListAdapter;
import com.cpigeon.book.module.play.viewmodel.PlayListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import butterknife.BindView;

/**
 * 赛绩列表
 * Created by Administrator on 2018/9/5.
 */

public class PlayFragment1 extends BaseBookFragment {

    private BreedPigeonDetailsViewModel mBreedPigeonDetailsViewModel;
    private PlayListViewModel mPlayListViewModel;

    @BindView(R.id.list)
    XRecyclerView mRecyclerView;
    private PlayListAdapter mPlayListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBreedPigeonDetailsViewModel = new BreedPigeonDetailsViewModel();
        mPlayListViewModel = new PlayListViewModel();
        initViewModels(mBreedPigeonDetailsViewModel, mPlayListViewModel);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_fragment1, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BreedPigeonEntity mBreedPigeonEntity = (BreedPigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_TYPE);

        if (mBreedPigeonEntity != null) {
            mBreedPigeonDetailsViewModel.footid = String.valueOf(mBreedPigeonEntity.getPigeonID());

            mPlayListViewModel.footid = String.valueOf(mBreedPigeonEntity.getFootRingID());
            mPlayListViewModel.pigeonid = String.valueOf(mBreedPigeonEntity.getPigeonID());
        }

        initPlayListData();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPlayListViewModel.mPigeonPlayListData.observe(this, pigeonPlayEntities -> {
            setProgressVisible(false);

//            mPlayListAdapter.getData().clear();;
//            mPlayListAdapter.setNewData(pigeonPlayEntities);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mPlayListAdapter, pigeonPlayEntities);
        });

        mPlayListViewModel.listEmptyMessage.observe(this, s -> {
            mPlayListAdapter.setEmptyText(s);
        });
    }


    private void initPlayListData() {
        mPlayListAdapter = new PlayListAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mRecyclerView.setRefreshListener(() -> {
            mPlayListAdapter.getData().clear();
            mPlayListViewModel.pi = 1;
            mPlayListViewModel.getZGW_Users_GetLogData();
        });

        mPlayListAdapter.setOnLoadMoreListener(() -> {
            mPlayListViewModel.pi++;
            mPlayListViewModel.getZGW_Users_GetLogData();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setAdapter(mPlayListAdapter);

        mPlayListViewModel.getZGW_Users_GetLogData();
    }


}
