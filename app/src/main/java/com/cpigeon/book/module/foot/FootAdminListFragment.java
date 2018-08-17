package com.cpigeon.book.module.foot;

import android.app.Activity;
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
import com.cpigeon.book.model.entity.FootAdminListEntity;
import com.cpigeon.book.module.foot.adapter.FootAdminListAdapter;
import com.cpigeon.book.module.foot.viewmodel.FootAdminViewModel;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootAdminListFragment extends BaseBookFragment {


    private XRecyclerView mRecyclerView;

    private FootAdminViewModel mFootAdminModel;
    private FootAdminListAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FootAdminListFragment.class);
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

        mFootAdminModel = new FootAdminViewModel();
        initViewModels(mFootAdminModel);

        mFootAdminModel.footAdminListData.observe(this, logbookEntities -> {

            if (logbookEntities.isEmpty()) {
                mAdapter.setLoadMore(true);
            } else {
                mAdapter.setLoadMore(false);
                mAdapter.addData(logbookEntities);
            }
        });


        mAdapter = new FootAdminListAdapter(null);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mFootAdminModel.pi = 1;
            mFootAdminModel.getTXGP_FootRing_SelectKeyAllData();
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mFootAdminModel.pi++;
            mFootAdminModel.getTXGP_FootRing_SelectKeyAllData();
        }, mRecyclerView.getRecyclerView());

        mFootAdminModel.getTXGP_FootRing_SelectKeyAllData();

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
//            FootAdminDetailsSingleFragment.start(getActivity());
            FootAdminListEntity mFootAdminListEntity = (FootAdminListEntity) adapter.getData().get(position);


            IntentBuilder.Builder().putExtra(IntentBuilder.KEY_DATA,  mFootAdminListEntity)
                    .startParentActivity(getActivity(), FootAdminDetailsSingleFragment.class);
        });
    }
}
