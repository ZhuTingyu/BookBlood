package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.util.PopWindowBuilder;
import com.base.util.system.ScreenTool;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainAddPigeonAdapter;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainPigeonListAdapter;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainAddPigeonFragment extends BaseBookFragment {
    RecyclerView addList;


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

        mBadgeView = new QBadgeView(getBaseActivity());
        mBadgeView.bindTarget(mTvChooseYet)
                .setBadgeGravity(Gravity.END
                        | Gravity.TOP)
                .setBadgePadding(2, true)
                .setBadgeTextSize(10, true)
                .setBadgeText("0");
        mAdapter = new NewTrainAddPigeonAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());

        mTvChooseYet.setOnClickListener(v -> {
            PopWindowBuilder.builder(getBaseActivity())
                    .setSize(ScreenTool.getScreenWidth(), ScreenTool.getScreenHeight())
                    .setBackgroundColor(R.color.color_black_30)
                    .setView(initPopView())
                    .setAnimationStyle(R.style.bottom_out_in_anim)
                    .showAtLocation(getBaseActivity().getRootView(), 0, 0, Gravity.CENTER);
        });
    }

    private View initPopView() {

        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.pop_add_pigeon_yet, null);

        addList  = view.findViewById(R.id.list);
        addItemDecorationLine(addList);
        addList.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        NewTrainPigeonListAdapter adapter =  new NewTrainPigeonListAdapter();
        addList.setAdapter(adapter);
        adapter.setOnDeleteListener(position -> {

        });
        adapter.setNewData(Lists.newTestArrayList());
        return view;
    }
}
