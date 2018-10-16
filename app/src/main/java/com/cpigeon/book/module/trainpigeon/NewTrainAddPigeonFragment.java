package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PopWindowBuilder;
import com.base.util.Utils;
import com.base.util.db.AppDatabase;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.ScreenTool;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainAddPigeonAdapter;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainPigeonListAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.NewTrainPigeonAddViewModel;
import com.paradoxie.shopanimlibrary.AniManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainAddPigeonFragment extends BaseBookFragment {
    RecyclerView addList;


    SearchFragmentParentActivity mActivity;
    private TextView mTvChooseYet;
    private TextView mTvAllChoose;
    private TextView mTvAddAtOnce;
    QBadgeView mBadgeView;
    XRecyclerView mRecyclerView;
    NewTrainAddPigeonAdapter mAdapter;
    NewTrainPigeonListAdapter mSelectAdapter;
    NewTrainPigeonAddViewModel mViewModel;
    View mPopView;
    AniManager mAniManager;
    PopupWindow mPopupWindow;
    List<PigeonEntity> mSaveAllPigeon;
    List<PigeonEntity> mSelectPigeon;

    public static void start(Activity activity, ArrayList<PigeonEntity> pigeonEntities, int code) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentBuilder.KEY_DATA, pigeonEntities);
        SearchFragmentParentActivity.start(activity, NewTrainAddPigeonFragment.class, code, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
        mViewModel = getViewModel(NewTrainPigeonAddViewModel.class);
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_train_add_pigeon, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPigeon();
        mSelectPigeon = (List<PigeonEntity>) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        mAniManager = new AniManager();
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchTrainPigeonActivity.class, null);
        });

        mTvChooseYet = findViewById(R.id.tvChooseYet);
        mTvAllChoose = findViewById(R.id.tvAllChoose);
        mRecyclerView = findViewById(R.id.list);
        mTvAddAtOnce = findViewById(R.id.tvAddAtOnce);

        mRecyclerView.addItemDecorationLine();

        mBadgeView = new QBadgeView(getBaseActivity());
        mBadgeView.bindTarget(mTvChooseYet)
                .setBadgeGravity(Gravity.END
                        | Gravity.TOP)
                .setBadgePadding(2, true)
                .setBadgeTextSize(10, true)
                .setBadgeText("0");
        mAdapter = new NewTrainAddPigeonAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            startAnim(mAdapter.getViewByPosition(mRecyclerView.getRecyclerView(),position, R.id.imgAdd), position);
            mAdapter.setSelect(position, true);
            mViewModel.setSelect(position);
        });
        mRecyclerView.setAdapter(mAdapter);
        initPopView();
        mTvChooseYet.setOnClickListener(v -> {
            if (Lists.isEmpty(mSelectAdapter.getData())) {
                DialogUtils.createHintDialog(getBaseActivity(), Utils.getString(R.string.text_not_choose_pigeon));
                return;
            }
            mPopupWindow = PopWindowBuilder.builder(getBaseActivity())
                    .setSize(ScreenTool.getScreenWidth(), ScreenTool.getScreenHeight())
                    .setBackgroundColor(R.color.color_black_30)
                    .setView(mPopView)
                    .setAnimationStyle(R.style.anim_fade_in_out)
                    .showAtLocation(getBaseActivity().getRootView(), 0, 0, Gravity.CENTER);
        });

        mTvAllChoose.setOnClickListener(v -> {
            List<PigeonEntity> notSelect =  mAdapter.getNotSelectAll();
            if(!Lists.isEmpty(notSelect)){
                mViewModel.setSelectAll(notSelect);
            }

            mAdapter.notifyDataSetChanged();
        });

        mTvAddAtOnce.setOnClickListener(v -> {
            savePigeon();
            IntentBuilder.Builder()
                    .putSerializableArrayListExtra(IntentBuilder.KEY_DATA, (ArrayList<? extends Serializable>) mSelectAdapter.getData())
                    .finishForResult(getBaseActivity());
        });


        if (Lists.isEmpty(mSaveAllPigeon)) {
            setProgressVisible(true);
            mViewModel.getPigeonList();
        } else {
            mViewModel.mAllPigeon = mSaveAllPigeon;
            mViewModel.mDataAllPigeon.setValue(mSaveAllPigeon);
        }

        if (!Lists.isEmpty(mSelectPigeon)) {
            mViewModel.mSelectPigeon = mSelectPigeon;
            mViewModel.mDataSelectPigeon.setValue(mSelectPigeon);
        }
    }

    private void initPopView() {
        mPopView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.pop_add_pigeon_yet, null);
        mPopView.setOnClickListener(v -> {
            mPopupWindow.dismiss();
        });
        addList = mPopView.findViewById(R.id.list);
        addItemDecorationLine(addList);
        addList.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mSelectAdapter = new NewTrainPigeonListAdapter();
        addList.setAdapter(mSelectAdapter);
        mSelectAdapter.setOnDeleteListener(position -> {
            int p = mViewModel.removeSelect(position);
            if (p != -1) {
                mAdapter.setSelect(p, false);
            }
        });
    }

    public void startAnim(View v, int position) {
        int[] end_location = new int[2];
        int[] start_location = new int[2];
        v.getLocationInWindow(start_location);// 获取购买按钮的在屏幕的X、Y坐标（动画开始的坐标）
        mBadgeView.getLocationInWindow(end_location);// 这是用来存储动画结束位置，也就是购物车图标的X、Y坐标
        ImageView buyImg = new ImageView(getBaseActivity());// buyImg是动画的图片
        buyImg.setImageResource(R.mipmap.ic_blue_point);// 设置buyImg的图片

        mAniManager.setTime(500);//自定义时间
        mAniManager.setAnim(getBaseActivity(), buyImg, start_location, end_location);// 开始执行动画

        mAniManager.setOnAnimListener(new AniManager.AnimListener() {
            @Override
            public void setAnimBegin(AniManager a) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void setAnimEnd(AniManager a) {

            }
        });
    }

    public void savePigeon() {
        AppDatabase.getInstance(getBaseActivity())
                .saveData(mAdapter.getData()
                        , AppDatabase.TYPE_SELECT_PIGEON_TO_TRAINING
                        , UserModel.getInstance().getUserId());
    }

    public void getPigeon() {
        mSaveAllPigeon = AppDatabase.getDates(AppDatabase.getInstance(getBaseActivity()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId()
                        , AppDatabase.TYPE_SELECT_PIGEON_TO_TRAINING), PigeonEntity.class);
    }


    @Override
    protected void initObserve() {
        mViewModel.mDataAllPigeon.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            mAdapter.setNewData(pigeonEntities);
        });

        mViewModel.mDataSelectPigeon.observe(this, pigeonEntities -> {
            mSelectAdapter.setNewData(pigeonEntities);
            mBadgeView.setBadgeText(String.valueOf(mSelectAdapter.getData().size()));
            if (mSelectAdapter.getData().size() == 0) {
                if(mPopupWindow != null){
                    mPopupWindow.dismiss();
                }
            }
        });
    }
}
