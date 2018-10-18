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
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonPlayEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonDetailsViewModel;
import com.cpigeon.book.module.play.adapter.PlayListAdapter;
import com.cpigeon.book.module.play.viewmodel.PlayListViewModel;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.Subscribe;

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
        mBreedPigeonDetailsViewModel = new BreedPigeonDetailsViewModel(getBaseActivity());
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

        mPlayListViewModel.footid = mBreedPigeonDetailsViewModel.footId;
        mPlayListViewModel.pigeonid = mBreedPigeonDetailsViewModel.pigeonId;

        initPlayListData();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPlayListViewModel.mPigeonPlayListData.observe(this, pigeonPlayEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mPlayListAdapter, pigeonPlayEntities);
            setProgressVisible(false);
        });

        mPlayListViewModel.listEmptyMessage.observe(this, s -> {
            mPlayListAdapter.setEmptyText(s);
        });
    }


    private void initPlayListData() {
        mPlayListAdapter = new PlayListAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        mPlayListAdapter.setOnItemClickListener((adapter, view, position) -> {
            try {
                PigeonPlayEntity mPigeonPlayEntity = (PigeonPlayEntity) adapter.getData().get(position);
                PlayAddFragment.start(getBaseActivity(), new PigeonEntity.Builder()
                        .FootRingNum(mPigeonPlayEntity.getFootRingNum())
                        .PigeonID(String.valueOf(mPigeonPlayEntity.getPigeonID()))
                        .FootRingID(String.valueOf(mPigeonPlayEntity.getFootRingID()))
                        .PigeonMatchID(String.valueOf(mPigeonPlayEntity.getPigeonMatchID()))
                        .build(), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mRecyclerView.setRefreshListener(() -> {
            dataRefresh();
        });

        mPlayListAdapter.setOnLoadMoreListener(() -> {
            mPlayListViewModel.pi++;
            mPlayListViewModel.getZGW_Users_GetLogData();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setAdapter(mPlayListAdapter);

        mPlayListViewModel.getZGW_Users_GetLogData();
    }


    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PIGEON_PLAY_LIST_REFRESH)) {
            dataRefresh();
        }
    }

    private void dataRefresh() {
        mPlayListAdapter.getData().clear();
        mPlayListAdapter.notifyDataSetChanged();
        mPlayListViewModel.pi = 1;
        mPlayListViewModel.getZGW_Users_GetLogData();
    }
}
