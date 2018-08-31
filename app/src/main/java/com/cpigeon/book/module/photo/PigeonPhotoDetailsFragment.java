package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PopWindowBuilder;
import com.base.util.Utils;
import com.base.util.glide.GlideUtil;
import com.base.util.picker.PickerUtil;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.viewmodel.PigeonPhotoDetailsViewModel;
import com.cpigeon.book.widget.ClickGetFocusEditText;
import com.cpigeon.book.widget.SimpleTitleView;
import com.cpigeon.book.widget.mzbanner.MZBannerView;
import com.cpigeon.book.widget.mzbanner.holder.MZViewHolder;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by Zhu TingYu on 2018/8/30.
 */

public class PigeonPhotoDetailsFragment extends BaseBookFragment {

    private MZBannerView mBanner;
    private SimpleTitleView mSTvMove;
    private SimpleTitleView mSTvSetFace;
    private SimpleTitleView mSTvDelete;

    PigeonPhotoDetailsViewModel mViewModel;
    SelectTypeViewModel mTypeViewModel;
    int typePosition = 0;
    private PopupWindow mPopupWindow;

    public static void start(Activity activity, String footNumber, int position) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, position)
                .putExtra(IntentBuilder.KEY_TITLE, footNumber)
                .startParentActivity(activity, PigeonPhotoDetailsFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new PigeonPhotoDetailsViewModel();
        mTypeViewModel = new SelectTypeViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fagment_pigeon_photo_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setToolbarRightImage(R.drawable.svg_pigeon_photo_details, item -> {
            mPopupWindow = PopWindowBuilder.builder(getBaseActivity())
                    .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    .setView(initPopView())
                    .setBackgroundColor(R.color.white)
                    .setAnimationStyle(R.style.bottom_out_in_anim)
                    .showAtLocation(getBaseActivity().getRootView(), 0, 0, Gravity.CENTER);
            return false;
        });

        String footNumber = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TITLE);
        int position = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_DATA, 0);
        setTitle(footNumber);

        mBanner = findViewById(R.id.banner);
        mSTvMove = findViewById(R.id.sTvMove);
        mSTvSetFace = findViewById(R.id.sTvSetFace);
        mSTvDelete = findViewById(R.id.sTvDelete);

        mBanner.setPages(Lists.newTestArrayList(), () -> {
            return new BannerViewHolder();
        });

        mBanner.getAdapter().setCurrentItem(position);


        mSTvMove.setOnClickListener(v -> {
            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mPhotoType)
                    , typePosition, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            typePosition = index;
                        }
                    });
        });
        setProgressVisible(true);
        mTypeViewModel.getSelectType_ImgType();

    }

    private View initPopView() {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.pop_pigeon_photo_remark, null);
        ImageView mImgClose;
        ClickGetFocusEditText mFEdRemark;
        TextView mTvOk;
        mImgClose = view.findViewById(R.id.imgClose);
        mFEdRemark = view.findViewById(R.id.fEdRemark);
        mTvOk = view.findViewById(R.id.tvOk);

        mFEdRemark.setOnClickAndHaveFocusListener(() -> {
            mTvOk.setVisibility(View.VISIBLE);
        });

        mImgClose.setOnClickListener(v -> {
            mPopupWindow.dismiss();
        });

        return view;
    }

    @Override
    protected void initObserve() {
        mTypeViewModel.mSelectType_ImgType.observe(this, selectTypeEntities -> {
            setProgressVisible(false);
            mViewModel.mPhotoType = selectTypeEntities;
        });
    }
}

class BannerViewHolder implements MZViewHolder<String> {

    private ImageView mImg;
    private TextView mTvColor;
    private TextView mTvNumberAndTime;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pigeon_photo_details, null);

        mImg = view.findViewById(R.id.img);
        mTvColor = view.findViewById(R.id.tvColor);
        mTvNumberAndTime = view.findViewById(R.id.tvNumberAndTime);

        new CrazyShadow.Builder()
                .setContext(context)
                .setBaseShadowColor(Utils.getColor(R.color.gray))
                .setDirection(CrazyShadowDirection.RIGHT_BOTTOM_LEFT)
                .setShadowRadius(ScreenTool.dip2px(5))
                .setCorner(ScreenTool.dip2px(0))
                .setBackground(Utils.getColor(R.color.white))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(view.findViewById(R.id.llRoot));
        return view;
    }

    @Override
    public void onBind(Context context, int position, String data) {
        // 数据绑定
        GlideUtil.setGlideImageView(context, UserModel.getInstance().getUserData().touxiangurl, mImg);
    }
}
