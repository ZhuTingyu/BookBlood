package com.cpigeon.book.module.foot;

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
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.BottomSheetAdapter;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.module.foot.adapter.FootAdminListAdapter;
import com.cpigeon.book.module.foot.viewmodel.FootAdminListViewModel;
import com.cpigeon.book.module.foot.viewmodel.FootAdminViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootAdminListFragment extends BaseBookFragment {


    private XRecyclerView mRecyclerView;

    private FootAdminListViewModel mViewModel;
    private FootAdminListAdapter mAdapter;
    private TextView mTvOk;
    SearchFragmentParentActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new FootAdminListViewModel();
        initViewModels(mViewModel);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
    }

    public static void start(Activity activity) {
        SearchFragmentParentActivity.start(activity, FootAdminListFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {

        });

        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);

        mAdapter = new FootAdminListAdapter(null);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mViewModel.pi = 1;
            mViewModel.getFoodList();
        });

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecorationLine();

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getFoodList();
        }, mRecyclerView.getRecyclerView());


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            FootAdminSingleFragment.start(getBaseActivity(), String.valueOf(mAdapter.getItem(position).getFootRingID()));
        });
        String[] chooseWays = getResources().getStringArray(R.array.array_choose_input_foot_number);
        mTvOk.setText(R.string.text_add_foot_number);
        mTvOk.setOnClickListener(v -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays),p -> {
                if(chooseWays[p].equals(Utils.getString(R.string.text_one_foot_input))){
                    FootAdminSingleFragment.start(getBaseActivity());
                }else {

                }
            });
        });


        setProgressVisible(true);
        mViewModel.getFoodList();
    }

    @Override
    protected void initObserve() {
        mViewModel.footAdminListData.observe(this, logbookEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setRefreshingCallBack(mRecyclerView, mAdapter, logbookEntities);
        });
    }
}
