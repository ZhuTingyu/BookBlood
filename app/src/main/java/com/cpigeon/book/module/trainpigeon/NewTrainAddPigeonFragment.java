package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainAddPigeonAdapter;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainAddPigeonFragment extends BaseBookFragment {

    SearchFragmentParentActivity mActivity;
    private TextView mTvChooseYet;
    private TextView mTvAllChoose;
    QBadgeView mBadgeView;
    XRecyclerView mRecyclerView;
    NewTrainAddPigeonAdapter mAdapter;

    public static void start(Activity activity) {
        SearchFragmentParentActivity.start(activity, NewTrainAddPigeonFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_train_add_pigeon, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchTrainPigeonActivity.class);
        });

        mTvChooseYet = findViewById(R.id.tvChooseYet);
        mTvAllChoose = findViewById(R.id.tvAllChoose);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();

        mBadgeView = new QBadgeView(getBaseActivity());
        mBadgeView.bindTarget(mTvChooseYet)
                .setBadgeGravity(Gravity.END
                        |Gravity.TOP)
                .setBadgePadding(2, true)
                .setBadgeTextSize(10, true)
                .setBadgeText("0");
        mAdapter = new NewTrainAddPigeonAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());
    }
}
