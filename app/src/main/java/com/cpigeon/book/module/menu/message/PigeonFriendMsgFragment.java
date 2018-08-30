package com.cpigeon.book.module.menu.message;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonFriendMsgListEntity;
import com.cpigeon.book.module.menu.adapter.PigeonFriendMsgAdapter;
import com.cpigeon.book.module.menu.viewmodel.PigeonFriendMsgViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 鸽友消息
 * Created by Administrator on 2018/8/9.
 */

public class PigeonFriendMsgFragment extends BaseBookFragment {

    private XRecyclerView mRecyclerView;

    private PigeonFriendMsgViewModel mViewModel;
    private PigeonFriendMsgAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PigeonFriendMsgFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewModel = new PigeonFriendMsgViewModel();
        initViewModel(mViewModel);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("鸽友消息");

        setToolbarRight("统计", item -> {
            mViewModel.getTXGP_Msg_CountData();
            return true;
        });

        mRecyclerView = findViewById(R.id.list);

        mAdapter = new PigeonFriendMsgAdapter(null);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getTXGP_GetMsgListData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTXGP_GetMsgListData();
        }, mRecyclerView.getRecyclerView());

        mViewModel.getTXGP_GetMsgListData();

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            mViewModel.changePosition = position;

            PigeonFriendMsgListEntity mPigeonFriendMsgListEntity = (PigeonFriendMsgListEntity) adapter.getData().get(position);
            mViewModel.getTXGP_Msg_DetailData(mPigeonFriendMsgListEntity.getId());
        });

    }


    @Override
    protected void initObserve() {

        mViewModel.pigeonFriendMsgListData.observe(this, datas -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });


        mViewModel.mPigeonFriendMsgDetail.observe(this, pigeonFriendMsgListEntity -> {
            mAdapter.getData().get(mViewModel.changePosition).setIsread("1");
            mAdapter.notifyItemChanged(mViewModel.changePosition);
        });
    }
}
