package com.cpigeon.book.module.menu.service;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.OpenServiceEvent;
import com.cpigeon.book.module.menu.service.adpter.OpenServiceAdapter;
import com.cpigeon.book.module.menu.service.viewmodel.OpenServiceViewModel;
import com.cpigeon.book.veiwholder.ServiceViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * hl  续费
 * Created by Administrator on 2018/8/20.
 */
public class OpenServiceFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    OpenServiceAdapter mAdapter;
    OpenServiceAdapter mRenewalAdapter;
    OpenServiceViewModel mViewModel;
    View mHeadView;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, OpenServiceFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new OpenServiceViewModel();
        initViewModel(mViewModel);
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
        setTitle(R.string.str_my_server);

//        setToolbarRight(R.string.text_open_report, item -> {
//            OpenServiceReportFragment.start(getBaseActivity());
//            return false;
//        });
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new OpenServiceAdapter(ServiceViewHolder.TYPE_OPEN, true);
        initHeadView();
        mAdapter.addHeaderView(mHeadView);
        mRecyclerView.setAdapter(mAdapter);

        setProgressVisible(true);
        mViewModel.getServiceInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(OpenServiceEvent event) {
        mViewModel.getServiceInfo();
    }

    @Override
    protected void initObserve() {
        mViewModel.mDataOpenServiceList.observe(this, serviceEntities -> {
            setProgressVisible(false);
            mRenewalAdapter.setNewData(serviceEntities);
        });

        mViewModel.mDataNotServiceList.observe(this, serviceEntities -> {
            setProgressVisible(false);
            mAdapter.setNewData(serviceEntities);
        });
    }

    private void initHeadView() {
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.include_renew_head, null);
        mRenewalAdapter = new OpenServiceAdapter(ServiceViewHolder.TYPE_RENEW, false);
        mRenewalAdapter.setEmptyText(Utils.getString(R.string.text_no_open_service));
        RecyclerView recyclerView = mHeadView.findViewById(R.id.openList);
        addItemDecorationLine(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.setAdapter(mRenewalAdapter);
    }
}
