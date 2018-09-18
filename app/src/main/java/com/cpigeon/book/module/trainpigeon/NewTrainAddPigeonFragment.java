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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PopWindowBuilder;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.ScreenTool;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainAddPigeonAdapter;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainPigeonListAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.NewTrainAddPigeonViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;
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
    NewTrainAddPigeonViewModel mViewModel;
    View mPopView;
    AniManager mAniManager;
    PopupWindow mPopupWindow;
    List<PigeonEntity> mSelectYetPigeon;

    public static void start(Activity activity, ArrayList<PigeonEntity> pigeonEntities, int code) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentBuilder.KEY_DATA, pigeonEntities);
        SearchFragmentParentActivity.start(activity, NewTrainAddPigeonFragment.class, code,null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
        mViewModel = new NewTrainAddPigeonViewModel();
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

        mSelectYetPigeon = (List<PigeonEntity>) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

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
        mAdapter.setOnAddPigeonListener(this::startAnim);
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
            mSelectAdapter.addData(mAdapter.getNotSelectAll());
            mAdapter.notifyDataSetChanged();
            mBadgeView.setBadgeText(String.valueOf(mSelectAdapter.getData().size()));
        });

        mTvAddAtOnce.setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .putSerializableArrayListExtra(IntentBuilder.KEY_DATA, (ArrayList<? extends Serializable>) mSelectAdapter.getData())
                    .finishForResult(getBaseActivity());
        });

        setProgressVisible(true);
        mViewModel.getPigeonList();
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
            PigeonEntity selectEntity = mSelectAdapter.getItem(position);
            for (int i = 0, len = mAdapter.getData().size(); i < len; i++) {
                PigeonEntity entity = mAdapter.getData().get(i);
                if(selectEntity.getFootRingNum().equals(entity.getFootRingNum())){
                    mAdapter.setSelect(i, false);
                    break;
                }
            }
            mSelectAdapter.remove(position);
            mBadgeView.setBadgeText(String.valueOf(mSelectAdapter.getData().size()));
            if(mSelectAdapter.getData().size() == 0){
                mPopupWindow.dismiss();
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
                mAdapter.setSelect(position, true);
                mSelectAdapter.addData(mAdapter.getData().get(position));
                mBadgeView.setBadgeNumber(mSelectAdapter.getData().size());
            }
        });
    }

    @Override
    protected void initObserve() {
        mViewModel.mDataPigeon.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            mAdapter.setNewData(pigeonEntities);
        });
    }
}
