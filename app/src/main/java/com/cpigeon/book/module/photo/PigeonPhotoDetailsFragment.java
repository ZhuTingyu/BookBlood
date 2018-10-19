package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.PopWindowBuilder;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.glide.GlideUtil;
import com.base.util.picker.PickerUtil;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonPhotoEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.viewmodel.PigeonPhotoDetailsViewModel;
import com.cpigeon.book.widget.ClickGetFocusEditText;
import com.cpigeon.book.widget.SimpleTitleView;
import com.cpigeon.book.widget.mydialog.ShareDialogFragment;
import com.cpigeon.book.widget.mydialog.ViewControlShare;
import com.cpigeon.book.widget.mzbanner.MZBannerView;
import com.cpigeon.book.widget.mzbanner.holder.MZViewHolder;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 图片详情
 * Created by Zhu TingYu on 2018/8/30.
 */

public class PigeonPhotoDetailsFragment extends BaseBookFragment {

    private MZBannerView mBanner;
    private SimpleTitleView mSTvMove;
    private SimpleTitleView mSTvSetFace;//设为封面
    private SimpleTitleView mSTvDelete;
    private SimpleTitleView sTvShare;

    PigeonPhotoDetailsViewModel mViewModel;
    SelectTypeViewModel mTypeViewModel;
    int typePosition = 0;
    private PopupWindow mPopupWindow;
    private int position;

    public static void start(Activity activity, PigeonEntity mPigeonEntity, List<PigeonPhotoEntity> mPigeonPhotoData, int position) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                .putExtra(IntentBuilder.KEY_DATA_2, (ArrayList) mPigeonPhotoData)
                .putExtra(IntentBuilder.KEY_DATA_3, position)
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

        mViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

        mViewModel.mPigeonPhotoData = getBaseActivity().getIntent().getParcelableArrayListExtra(IntentBuilder.KEY_DATA_2);

        position = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_DATA_3, 0);

        setTitle(mViewModel.mPigeonEntity.getFootRingNum());

        mBanner = findViewById(R.id.banner);
        mSTvMove = findViewById(R.id.sTvMove);
        mSTvSetFace = findViewById(R.id.sTvSetFace);
        mSTvDelete = findViewById(R.id.sTvDelete);
        sTvShare = view.findViewById(R.id.sTvShare);

        sTvShare.setOnClickListener(v -> {
            String shareUrl = mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPhotoUrl();
            showShareDialog(shareUrl);
        });

        dialogFragment = new ShareDialogFragment();

        mBanner.setPages(mViewModel.mPigeonPhotoData, () -> {
            return new BannerViewHolder();
        });

        mBanner.getAdapter().setPageClickListener((view1, position1) -> {
            PictureSelectUtil.showImagePhoto(getBaseActivity(), view1.findViewById(R.id.img), Lists.newArrayList(mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPhotoUrl()), 0);
        });

        mBanner.getAdapter().setCurrentItem(position);
        mSTvMove.setOnClickListener(v -> {
            //移动
            position = mBanner.getViewPager().getCurrentItem();
            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mPhotoType)
                    , typePosition, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            typePosition = index;

                            mViewModel.typeid = mViewModel.mPhotoType.get(index).getTypeID();
                            mViewModel.photoid = mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPigeonPhotoID();

                            mViewModel.currentImgTypeStr = mViewModel.mPhotoType.get(index).getTypeName();

                            setProgressVisible(true);
                            mViewModel.getTXGP_PigeonPhoto_UpdateData();
                        }
                    });
        });

        mSTvSetFace.setOnClickListener(v -> {
            //设为封面
            position = mBanner.getViewPager().getCurrentItem();
            setProgressVisible(true);
            mViewModel.photoid = mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPigeonPhotoID();
            mViewModel.getTXGP_PigeonPhoto_EideConverData();
        });

        mSTvDelete.setOnClickListener(v -> {
            //删除
            position = mBanner.getViewPager().getCurrentItem();
            getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), getString(R.string.text_delete_warning_hint), sweetAlertDialog -> {
                //确定
                sweetAlertDialog.dismiss();
                setProgressVisible(true);
                mViewModel.photoid = mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPigeonPhotoID();
                mViewModel.getTXGP_PigeonPhoto_DeleteData();
            }, sweetAlertDialog -> {
                //取消
                sweetAlertDialog.dismiss();
            });
        });

        mViewModel.photoid = mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPigeonPhotoID();

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

        mFEdRemark.setText(mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getRemark());

        mFEdRemark.setOnClickAndHaveFocusListener(() -> {
            mTvOk.setVisibility(View.VISIBLE);
        });

        mImgClose.setOnClickListener(v -> {
            mPopupWindow.dismiss();
        });

        mTvOk.setOnClickListener(v -> {
            mViewModel.remark = mFEdRemark.getText().toString();
            mViewModel.photoid = mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPigeonPhotoID();

            mViewModel.getTXGP_PigeonPhoto_EideRemarkData();
        });

        return view;
    }

    private ShareDialogFragment dialogFragment;

    //分享
    private void showShareDialog(String strUrl) {
        if (dialogFragment != null && dialogFragment.getDialog() != null && dialogFragment.getDialog().isShowing()) {
            dialogFragment.getDialog().dismiss();
            dialogFragment.dismiss();
        }

        if (dialogFragment != null) {
            dialogFragment.setShareTitle("分享");
            dialogFragment.setShareContentString("天下鸽谱图片分享");
            dialogFragment.setShareContent(strUrl);
            dialogFragment.setShareListener(ViewControlShare.getShareResultsDown(getBaseActivity(), dialogFragment, ""));
            dialogFragment.setShareType(2);
            dialogFragment.show(getBaseActivity().getFragmentManager(), "share");
        }
    }

    @Override
    protected void initObserve() {
        mTypeViewModel.mSelectType_ImgType.observe(this, selectTypeEntities -> {
            setProgressVisible(false);
            mViewModel.mPhotoType = selectTypeEntities;
        });

        mViewModel.imgEditCallBack.observe(this, datas -> {
            mViewModel.mPigeonPhotoData
                    .get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount())
                    .setTypeName(mViewModel.currentImgTypeStr);

            mBanner.getAdapter().getDatas().clear();
            mBanner.getAdapter().notifyDataSetChanged();
            mBanner.setPages(mViewModel.mPigeonPhotoData, () -> {
                return new BannerViewHolder();
            });

            mBanner.getAdapter().setCurrentItem(position);
        });

        mViewModel.imgRemarkEditCallBack.observe(this, o -> {
            mPopupWindow.dismiss();

            mViewModel.mPigeonPhotoData
                    .get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount())
                    .setRemark(mViewModel.remark);
        });

        mViewModel.imgDelCallBack.observe(this, o -> {
            mViewModel.mPigeonPhotoData.remove(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount());


            if (mViewModel.mPigeonPhotoData.size() == 0) {
                getBaseActivity().finish();
                return;
            }
            mBanner.getAdapter().getDatas().clear();
            mBanner.getAdapter().notifyDataSetChanged();
            mBanner.setPages(mViewModel.mPigeonPhotoData, () -> {
                return new BannerViewHolder();
            });

            mBanner.getAdapter().setCurrentItem(position);
        });
    }

//    @Subscribe //订阅事件FirstEvent
//    public void onEventMainThread(String info) {
//        if (info.equals(EventBusService.PIGEON_PHOTO_DEL_REFRESH)) {
//
//        }
//    }
}

class BannerViewHolder implements MZViewHolder<PigeonPhotoEntity> {

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
                .setShadowRadius(ScreenTool.dip2px(7))
                .setCorner(ScreenTool.dip2px(0))
                .setBackground(Utils.getColor(R.color.white))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(view.findViewById(R.id.llRoot));

        return view;
    }

    @Override
    public void onBind(Context context, int position, PigeonPhotoEntity data) {
        // 数据绑定
        GlideUtil.setGlideImageView(context, data.getPhotoUrl(), mImg);
        //图片类型
        mTvColor.setText(data.getTypeName());
        //时间
        mTvNumberAndTime.setText(data.getAddTime());


    }
}
