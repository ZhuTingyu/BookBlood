package com.cpigeon.book.module.menu.smalltools.recyclebin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.dialog.DialogUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.RecycleBinEntity;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.menu.smalltools.recyclebin.adapter.RecycleBinAdapter;
import com.cpigeon.book.module.menu.smalltools.recyclebin.viewmodel.RecycleBinViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 回收站
 * Created by Administrator on 2018/10/11 0011.
 */

public class RecycleBinFragment extends BaseListFragment {


    private RecycleBinViewModel mRecycleBinViewModel;

    private RecycleBinAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, RecycleBinFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mRecycleBinViewModel = new RecycleBinViewModel();
        initViewModels(mRecycleBinViewModel);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(getString(R.string.str_recycle_bin));

        setToolbarRight(getString(R.string.str_recycle_bin_empty), item -> {

            getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), "确定清除回收站数据吗？清除数据后不可恢复", sweetAlertDialog -> {
                //确定
                sweetAlertDialog.dismiss();
                mRecycleBinViewModel.getTXGP_EmptyData();
            }, sweetAlertDialog -> {
                //取消
                sweetAlertDialog.dismiss();

            });

            return true;
        });

        tvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        list.addItemDecorationLine(20);
        mAdapter = new RecycleBinAdapter(null);

        list.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mRecycleBinViewModel.pi = 1;
            mRecycleBinViewModel.getZGW_Users_GetLogData();
        });

        list.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mRecycleBinViewModel.pi++;
            mRecycleBinViewModel.getZGW_Users_GetLogData();
        }, list.getRecyclerView());


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            try {
                //getTXGP_RecoveryData()

                RecycleBinEntity mRecycleBinEntity = (RecycleBinEntity) adapter.getItem(position);
                getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), "确定恢复本条数据吗？", sweetAlertDialog -> {
                    //确定
                    sweetAlertDialog.dismiss();

                    mRecycleBinViewModel.recyid = mRecycleBinEntity.getRecycleID();
                    mRecycleBinViewModel.getTXGP_RecoveryData();

                }, sweetAlertDialog -> {
                    //取消
                    sweetAlertDialog.dismiss();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setProgressVisible(true);

        mRecycleBinViewModel.getZGW_Users_GetLogData();
    }

    @Override
    protected void initObserve() {

        mRecycleBinViewModel.mRecycleBinData.observe(this, datas -> {
            RecyclerViewUtils.setLoadMoreCallBack(list, mAdapter, datas);
            setProgressVisible(false);
        });

        mRecycleBinViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }


}
