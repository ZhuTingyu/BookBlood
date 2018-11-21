package com.cpigeon.book.module.photo;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.PopWindowBuilder;
import com.base.util.dialog.DialogUtils;
import com.base.util.glide.GlideCacheUtil;
import com.base.util.picker.PickerUtil;
import com.base.widget.photoview.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
import com.luck.picture.lib.dialog.PictureDialog;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 图片详情
 * Created by Zhu TingYu on 2018/8/30.
 */

public class PigeonPhotoDetailsFragment extends BaseBookFragment
{
    PictureDialog dialog;
    private MZBannerView mBanner;
    private boolean Click=false;
    private SimpleTitleView mSTvMove;
    private SimpleTitleView mSTvSetFace;//设为封面
    private SimpleTitleView mSTvDelete;
    private SimpleTitleView sTvShare;
    private LinearLayout linearLayout;
    public static String RingNum;
    PigeonPhotoDetailsViewModel mViewModel;
    SelectTypeViewModel mTypeViewModel;
    int typePosition = 0;
    private PopupWindow mPopupWindow;
    private int position;
    private AppBarLayout appBarLayout;
    private ValueAnimator animator;//属性动画值
    private float curTranslationY;//动画Y轴移动距离
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

        RingNum=mViewModel.mPigeonEntity.getFootRingNum();
        mBanner = findViewById(R.id.banner);
        appBarLayout=getBaseActivity().findViewById(R.id.appbar);
        mSTvMove = findViewById(R.id.sTvMove);
        mSTvSetFace = findViewById(R.id.sTvSetFace);
        mSTvDelete = findViewById(R.id.sTvDelete);
        sTvShare = view.findViewById(R.id.sTvShare);
        linearLayout=view.findViewById(R.id.photo_ll);
        sTvShare.setOnClickListener(v -> {
            String shareUrl = mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPhotoUrl();
            showShareDialog(shareUrl);
        });

        dialogFragment = new ShareDialogFragment();

        mBanner.setPages(mViewModel.mPigeonPhotoData, () -> {
            return new BannerViewHolder();
        });
        mBanner.setIndicatorPadding(0,0,0,0);

        mBanner.getAdapter().setPageClickListener((view1, position1) -> {
//            startAnimator();


            // PictureSelectUtil.showImagePhoto(getBaseActivity(), view1.findViewById(R.id.img), Lists.newArrayList(mViewModel.mPigeonPhotoData.get(mBanner.getViewPager().getCurrentItem() % mBanner.getAdapter().getRealCount()).getPhotoUrl()), 0);
        });

if (mViewModel.mPigeonPhotoData.size()==0)
{
    setTitle("信鸽相册");
}else
{
    setTitle((position+1+"/"+ mViewModel.mPigeonPhotoData.size()));
}
        mBanner.getAdapter().setCurrentItem(position);
        mBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(((1+position)+"/"+ mViewModel.mPigeonPhotoData.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
            removeCache();
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
                mViewModel.PigeonId=mViewModel.mPigeonEntity.getPigeonID();
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
    public TranslateAnimation hide()
    {
        TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        hideAnim.setDuration(500);
        return hideAnim;
    }
    public TranslateAnimation show()
    {
        TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(500);

        return showAnim;
    }
    private void startAnimator() {
        if (!Click) {//向下移动
           // img_l_arrow.setRotation(0);
            curTranslationY = appBarLayout.getTranslationY();//获取当前空间Y方向上的值
            animator = ValueAnimator.ofFloat(0,-appBarLayout.getHeight() );
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    appBarLayout.setTranslationY(value);
                }
            });//设置监听

            animator.setDuration(700);//设置动画执行时间
            if (!animator.isRunning()) {
                animator.start();//开始动画
            }
        } else {//向上移动

            curTranslationY = appBarLayout.getTranslationY();
            animator = ValueAnimator.ofFloat(-appBarLayout.getHeight(), 0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    appBarLayout.setTranslationY(value);
                }
            });
            animator.setDuration(700);
            if (!animator.isRunning()) {
                animator.start();
            }
        }
    }
//    @Subscribe //订阅事件FirstEvent
//    public void onEventMainThread(String info) {
//        if (info.equals(EventBusService.PIGEON_PHOTO_DEL_REFRESH)) {
//
//        }
//    }
private void removeCache() {
    GlideCacheUtil.clearImageDiskCache(getActivity());//清除Glide图片加载缓存
}

   public class BannerViewHolder implements MZViewHolder<PigeonPhotoEntity> {

        private PhotoView mImg;
        private TextView mTvColor,mTvTime;
        private TextView mTvNumberAndTime;


        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_pigeon_photo_details, null);
            mImg = view.findViewById(R.id.img);
            mTvColor = view.findViewById(R.id.tvColor);
            mTvTime=view.findViewById(R.id.tvtime);
            mTvNumberAndTime = view.findViewById(R.id.tvNumberAndTime);

            return view;
        }

        @Override
        public void onBind(Context context, int position, PigeonPhotoEntity data) {
            // 数据绑定
            Glide.with(context).load(data.getPhotoUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).into(new SimpleTarget<Bitmap>(480, 800) {
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    mImg.setImageBitmap(resource);
                    getBaseActivity().setProgressVisible(false);
                }

            });
            String str = data.getAddTime();

            //图片类型
            mTvColor.setText(data.getTypeName());
            //时间

            mTvNumberAndTime.setText(PigeonPhotoDetailsFragment.RingNum);
            mTvTime.setText( str.substring(0, str.indexOf(" ")));
            mImg.setOnViewTapListener((view, x, y) -> {
                if (Click) {
                    //appBarLayout.setVisibility(View.VISIBLE);
                    Click=false;
                    linearLayout.startAnimation(show());
                    linearLayout.setVisibility(View.VISIBLE);

                }
                else
                {
                    Click=true;
                    // appBarLayout.setVisibility(View.GONE);
                    linearLayout.startAnimation(hide());
                    linearLayout.setVisibility(View.GONE);
                }

            });
mImg.setTag(position);
        }

    }
}


