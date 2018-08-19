package com.cpigeon.book.module.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.util.BarUtils;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.foot.FootAdminListFragment;
import com.cpigeon.book.module.home.adapter.HomeTopAdapter;
import com.cpigeon.book.widget.SimpleTitleView;

/**
 * Created by Zhu TingYu on 2018/7/10.
 */

public class HomeFragment extends BaseBookFragment {

    RecyclerView mTopList;
    HomeTopAdapter mAdapter;

    private SimpleTitleView mSTvFootManager;
    private SimpleTitleView mSTvBreedPigeonManager;
    private SimpleTitleView mSTvTrainRecord;
    private SimpleTitleView mSTvMatchPigeonManger;
    private SimpleTitleView mSTvFeedPigeonRecord;
    private SimpleTitleView mSTvAiPigeonHouse;
    private ImageView mImgAd;
    private SimpleTitleView mSTvBloodBookMade;
    private SimpleTitleView mSTvMatchPigeonAnalyse;
    private SimpleTitleView mSTvBloodFind;
    private SimpleTitleView mSTvBreedInfo;
    private SimpleTitleView mSTvPigeonMatchInfo;
    private SimpleTitleView mSTvPigeonPhoto;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setImageTitle();
        setToolbarColor(R.color.white);
        BarUtils.setStatusBarLightMode(getBaseActivity(), true);

        setToolbarLeft(R.drawable.svg_home_my, v -> {

        });

        setToolbarRightImage(R.drawable.svg_home_message, item -> {
            return false;
        });

        mTopList = findViewById(R.id.topList);

        mSTvFootManager = findViewById(R.id.sTvFootManager);
        mSTvBreedPigeonManager = findViewById(R.id.sTvBreedPigeonManager);
        mSTvTrainRecord = findViewById(R.id.sTvTrainRecord);
        mSTvMatchPigeonManger = findViewById(R.id.sTvMatchPigeonManger);
        mSTvFeedPigeonRecord = findViewById(R.id.sTvFeedPigeonRecord);
        mSTvAiPigeonHouse = findViewById(R.id.sTvAiPigeonHouse);
        mImgAd = findViewById(R.id.imgAd);
        mSTvBloodBookMade = findViewById(R.id.sTvBloodBookMade);
        mSTvMatchPigeonAnalyse = findViewById(R.id.sTvMatchPigeonAnalyse);
        mSTvBloodFind = findViewById(R.id.sTvBloodFind);
        mSTvBreedInfo = findViewById(R.id.sTvBreedInfo);
        mSTvPigeonMatchInfo = findViewById(R.id.sTvPigeonMatchInfo);
        mSTvPigeonPhoto = findViewById(R.id.sTvPigeonPhoto);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTopList.setLayoutManager(manager);
        mAdapter = new HomeTopAdapter();
        mTopList.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newArrayList("",""));

        mSTvFootManager.setOnClickListener(v -> {
            FootAdminListFragment.start(getBaseActivity());
        });

    }
}
