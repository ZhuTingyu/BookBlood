package com.cpigeon.book.module.home.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.base.BaseWebViewActivity;
import com.base.util.BarUtils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.FootUpdateEvent;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.event.PigeonUpdateEvent;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.breeding.BreedingFootListFragment;
import com.cpigeon.book.module.breedpigeon.BreedPigeonListFragment;
import com.cpigeon.book.module.feedpigeon.FeedPigeonRecordListFragment;
import com.cpigeon.book.module.foot.FootAdminListFragment;
import com.cpigeon.book.module.home.AdImageFragment;
import com.cpigeon.book.module.home.home.adapter.HomeTopAdapter;
import com.cpigeon.book.module.home.home.viewmodel.HomeViewModel;
import com.cpigeon.book.module.makebloodbook.SelectPigeonToMakeBookFragment;
import com.cpigeon.book.module.menu.SignFragment;
import com.cpigeon.book.module.menu.message.MsgFragment;
import com.cpigeon.book.module.photo.SelectFootToPhotoFragment;
import com.cpigeon.book.module.pigeonhouse.viewmodle.PigeonHouseViewModel;
import com.cpigeon.book.module.pigeonleague.PigeonToLeagueFootListFragment;
import com.cpigeon.book.module.play.PlayFootListFragment;
import com.cpigeon.book.module.trainpigeon.TrainPigeonListFragment;
import com.cpigeon.book.widget.SimpleTitleView;
import com.luck.picture.lib.tools.StringUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Zhu TingYu on 2018/7/10.
 */

public class HomeFragment extends BaseBookFragment {

    RecyclerView mTopList;
    HomeTopAdapter mAdapter;
    HomeViewModel mViewModel;
    PigeonHouseViewModel mPigeonHouseViewModel;

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
        mViewModel = new HomeViewModel();
        mPigeonHouseViewModel = new PigeonHouseViewModel();
        initViewModels(mViewModel, mPigeonHouseViewModel);
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

        setToolbarLeft(R.drawable.svg_home_sign, v -> {
            SignFragment.start(getBaseActivity());
        });

        setToolbarRightImage(R.drawable.svg_home_message, item -> {
            //公告通知 ，鸽友消息
//            MsgActivity.start(getBaseActivity());
            MsgFragment.start(getBaseActivity());
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

        int w = ScreenTool.getScreenWidth() - ScreenTool.dip2px(40);
        int h = (w / 10) * 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
        params.setMargins(ScreenTool.dip2px(20), 0, ScreenTool.dip2px(20), 0);
        mImgAd.setLayoutParams(params);


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTopList.setLayoutManager(manager);
        mAdapter = new HomeTopAdapter();
        mTopList.setAdapter(mAdapter);

        mSTvFootManager.setOnClickListener(v -> {
            //足环管理
            FootAdminListFragment.start(getBaseActivity());
        });
        mSTvBreedPigeonManager.setOnClickListener(v -> {
            //种鸽管理
            BreedPigeonListFragment.start(getBaseActivity());
        });

        mSTvMatchPigeonManger.setOnClickListener(v -> {
            //赛鸽管理
            PlayFootListFragment.start(getBaseActivity());
        });


        mSTvTrainRecord.setOnClickListener(v -> {
            //训鸽记录
            TrainPigeonListFragment.start(getBaseActivity());
        });

        mSTvBloodBookMade.setOnClickListener(v -> {
            //血统书制作
            SearchFragmentParentActivity.start(getBaseActivity(), SelectPigeonToMakeBookFragment.class
                    , false, null);
        });

        mSTvFeedPigeonRecord.setOnClickListener(v -> {
            //养鸽记录
            FeedPigeonRecordListFragment.start(getBaseActivity());
        });

        mSTvBreedInfo.setOnClickListener(v -> {
            //繁育信息
            BreedingFootListFragment.start(getBaseActivity());
        });

        mSTvPigeonMatchInfo.setOnClickListener(v -> {
            //信鸽赛绩
            PigeonToLeagueFootListFragment.start(getBaseActivity());
        });

        mSTvPigeonPhoto.setOnClickListener(v -> {
            //信鸽相册
            SelectFootToPhotoFragment.start(getBaseActivity());
        });

        mSTvAiPigeonHouse.setOnClickListener(v -> {
            AdImageFragment.start(getBaseActivity(), AdImageFragment.TYPE_AI_PIGEON_HOUSE);
        });

        mSTvBloodFind.setOnClickListener(v -> {
            AdImageFragment.start(getBaseActivity(), AdImageFragment.TYPE_FIND_BLOOD);
        });

        mSTvMatchPigeonAnalyse.setOnClickListener(v -> {
            AdImageFragment.start(getBaseActivity(), AdImageFragment.TYPE_MATCH_PIGEON_ANALYSE);
        });

        mViewModel.getHomeAd();
        mPigeonHouseViewModel.getPigeonHouseInHone();
        mViewModel.getHomeTop();
    }

    @Override
    protected void initObserve() {
        mViewModel.mLdHomeAd.observe(this, homeAdEntity -> {
            Glide.with(getBaseActivity()).load(homeAdEntity.getAdImageUrl())
                    .centerCrop()
                    .bitmapTransform(new RoundedCornersTransformation(context, 5, 0))
                    .into(mImgAd);
            if(StringUtil.isStringValid(homeAdEntity.getAdUrl())){
                mImgAd.setOnClickListener(v -> {
                    BaseWebViewActivity.start(getBaseActivity(), homeAdEntity.getAdUrl());
                });
            }

        });

        mPigeonHouseViewModel.mHouseEntityInfo.observe(this, pigeonHouseEntity -> {
            UserModel.getInstance().setPigeonHouseInfo(pigeonHouseEntity);
        });

        mViewModel.mDataHomeTop.observe(this, homeTopEntities -> {
            mAdapter.setNewData(homeTopEntities);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(FootUpdateEvent event) {
        mViewModel.getHomeTop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(PigeonAddEvent event) {
        mViewModel.getHomeTop();
    }

    public void OnEvent(PigeonUpdateEvent event) {
        mViewModel.getHomeTop();
    }


}
