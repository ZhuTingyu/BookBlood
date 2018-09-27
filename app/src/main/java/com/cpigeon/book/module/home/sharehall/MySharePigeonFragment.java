package com.cpigeon.book.module.home.sharehall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.ShareHallEvent;
import com.cpigeon.book.module.home.sharehall.adpter.ShareHallHomeAdapter;
import com.cpigeon.book.module.home.sharehall.viewmodel.ShareHallViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class MySharePigeonFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    TextView mTvOk;
    ShareHallHomeAdapter mAdapter;
    SearchFragmentParentActivity mActivity;
    ShareHallViewModel mViewModel;
    int delectPosition = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
        mViewModel = new ShareHallViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xreclyview_with_bottom_btn_not_white, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.type = ShareHallViewModel.TYPE_MY;
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            SearchSharePigeonActivity.start(getBaseActivity(), true);
        });
        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mAdapter = new ShareHallHomeAdapter(true);
        mAdapter.setOnDletetClickLisenter((position, pigeonEntity) -> {
            DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_is_cancel_share),sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                setProgressVisible(true);
                delectPosition = position;
                mViewModel.pigeonId = pigeonEntity.getPigeonID();
                mViewModel.footId = pigeonEntity.getFootRingID();
                mViewModel.cancelApplyShareHall();
            });
        });
        mRecyclerView.setAdapter(mAdapter);
        mTvOk.setText(R.string.text_add_share_pigeon);
        mTvOk.setOnClickListener(v -> {
            SearchPigeonFragment.start(getBaseActivity());
        });

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getSharePigeons();
        });


        setProgressVisible(true);
        mViewModel.getSharePigeons();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataSharePigeon.observe(this, sharePigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, sharePigeonEntities);
        });

        mViewModel.mDataCancelShareR.observe(this, s -> {
            setProgressVisible(false);
            mAdapter.remove(delectPosition);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ShareHallEvent event) {
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getSharePigeons();
    }
}
