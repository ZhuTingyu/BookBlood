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
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;
import com.cpigeon.book.module.menu.adapter.AnnouncementNoticeAdapter;
import com.cpigeon.book.module.menu.viewmodel.AnnouncementNoticeViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 公告通知
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeFragment extends BaseBookFragment {

    private XRecyclerView mRecyclerView;

    private AnnouncementNoticeViewModel mViewModel;
    private AnnouncementNoticeAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AnnouncementNoticeFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new AnnouncementNoticeViewModel();
        initViewModels(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = findViewById(R.id.list);

        setTitle(getString(R.string.test_announcement_notice));

//        setToolbarRight("统计", item -> {
//            mViewModel.getTXGP_GongGao_CountData();
//            return true;
//        });

        mAdapter = new AnnouncementNoticeAdapter(null);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                mViewModel.changePosition = position;

                AnnouncementNoticeEntity mAnnouncementNoticeEntity = (AnnouncementNoticeEntity) adapter.getData().get(position);
                mViewModel.getTXGP_GongGao_DetailData(mAnnouncementNoticeEntity.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getTXGP_GetGongGaoData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getTXGP_GetGongGaoData();
        }, mRecyclerView.getRecyclerView());

        setProgressVisible(true);
        mViewModel.getTXGP_GetGongGaoData();

//        mViewModel.getTXGP_GongGao_CountData();
    }


    @Override
    protected void initObserve() {

        mViewModel.announcementNoticeData.observe(this, datas -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
            setProgressVisible(false);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
            setProgressVisible(false);
        });

        mViewModel.mDetailData.observe(this, msgCountEntity -> {
            mAdapter.getData().get(mViewModel.changePosition).setIsread("1");
            mAdapter.notifyItemChanged(mViewModel.changePosition);

//            mViewModel.getTXGP_GongGao_CountData();
        });

        mViewModel.mMsgCountData.observe(this, msgCountEntity -> {
            MsgActivity.initTobData(Lists.newArrayList(msgCountEntity.getCount(), MsgActivity.getTobData().get(1)));
        });
    }

}
