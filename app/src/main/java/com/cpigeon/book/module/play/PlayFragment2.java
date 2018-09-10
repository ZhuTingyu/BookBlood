package com.cpigeon.book.module.play;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.model.entity.PlayAdditionalInfoEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonDetailsViewModel;
import com.cpigeon.book.module.play.adapter.PlayAddInfoAdapter;
import com.cpigeon.book.module.play.viewmodel.PlayListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import butterknife.BindView;

/**
 * 附加信息列表
 * Created by Administrator on 2018/9/5.
 */

public class PlayFragment2 extends BaseBookFragment {

    private BreedPigeonDetailsViewModel mBreedPigeonDetailsViewModel;
    private PlayListViewModel mPlayListViewModel;

    @BindView(R.id.list)
    XRecyclerView mRecyclerView;
    private PlayAddInfoAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBreedPigeonDetailsViewModel = new BreedPigeonDetailsViewModel(getBaseActivity());
        mPlayListViewModel = new PlayListViewModel();
        initViewModels(mBreedPigeonDetailsViewModel, mPlayListViewModel);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_fragment2, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPlayListViewModel.footid = mBreedPigeonDetailsViewModel.footId;
        mPlayListViewModel.pigeonid = mBreedPigeonDetailsViewModel.pigeonId;

        initPlayListData();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPlayListViewModel.mPlayAdditionalInfoListData.observe(this, datas -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mPlayListViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }

    private void initPlayListData() {
        mAdapter = new PlayAddInfoAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mPlayListViewModel.pi = 1;
            mPlayListViewModel.getPlayAdditionalInfoList();
        });


        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PlayAdditionalInfoEntity mPigeonPlayEntity = (PlayAdditionalInfoEntity) adapter.getData().get(position);

            PlayAddFragment.start(getBaseActivity(), new PigeonEntryEntity.Builder()
                    .PigeonID(String.valueOf(mPigeonPlayEntity.getPigeonID()))
                    .FootRingID(String.valueOf(mPigeonPlayEntity.getFootRingID()))
                    .MatchInfoID(String.valueOf(mPigeonPlayEntity.getMatchInfoID()))
                    .MatchInfo(mPigeonPlayEntity.getMatchInfo())
                    .build(), 2);
        });


        mAdapter.setOnLoadMoreListener(() -> {
            mPlayListViewModel.pi++;
            mPlayListViewModel.getPlayAdditionalInfoList();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setAdapter(mAdapter);

        mPlayListViewModel.getPlayAdditionalInfoList();
    }


}
