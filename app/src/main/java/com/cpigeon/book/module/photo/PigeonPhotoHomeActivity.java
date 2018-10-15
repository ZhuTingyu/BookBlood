package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseActivity;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.Utils;
import com.base.util.picker.PickerUtil;
import com.base.util.system.ScreenTool;
import com.base.widget.BottomSheetAdapter;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.adpter.ImageItemDecoration;
import com.cpigeon.book.module.photo.adpter.PigeonPhotoHomeAdapter;
import com.cpigeon.book.module.photo.viewmodel.PigeonPhotoViewModel;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.OrderTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 信鸽相册   图片列表 筛选
 * Created by Zhu TingYu on 2018/8/29.
 */

public class PigeonPhotoHomeActivity extends BaseBookActivity {

    private CollapsingToolbarLayout mCoordinator;
    private ImageView mImgHead;
    private TextView mTvFootNumber;
    private TextView mTvPhotoCount;
    private AppBarLayout mAppBar;

    private TextView mTvAllPhoto;
    private OrderTextView mOrderT;
    private ImageView imgAdd;


    PigeonPhotoHomeAdapter mAdapter;
    RecyclerView mRecyclerView;

    PigeonPhotoViewModel mViewModel;
    SelectTypeViewModel mTypeViewModel;
    private int typePosition = 0;

    public static void start(Activity activity, PigeonEntity mPigeonEntity) {
        IntentBuilder.Builder(activity, PigeonPhotoHomeActivity.class)
                .putExtra(BaseActivity.IS_BAR_IMMERSIVE, false)
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                .startActivity();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pigeon_photo_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//在当前界面注册一个订阅者

        mViewModel = new PigeonPhotoViewModel();
        mTypeViewModel = new SelectTypeViewModel();
        initViewModel(mViewModel);

        mViewModel.mPigeonEntity = (PigeonEntity) getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);


        mCoordinator = findViewById(R.id.coordinator);
        mImgHead = findViewById(R.id.imgHead);
        mTvFootNumber = findViewById(R.id.tvFootNumber);
        mTvPhotoCount = findViewById(R.id.tvPhotoCount);
        mAppBar = findViewById(R.id.appbar);
        mRecyclerView = findViewById(R.id.list);
        mTvAllPhoto = findViewById(R.id.tvAllPhoto);
        mOrderT = findViewById(R.id.orderT);
        imgAdd = findViewById(R.id.imgAdd);
        mAdapter = new PigeonPhotoHomeAdapter();


        initDatas();

        initRecyclerView();

        mViewModel.getTXGP_PigeonPhoto_CountPhotoData();

    }

    private void initDatas() {

        mTvFootNumber.setText(mViewModel.mPigeonEntity.getFootRingNum());

        Glide.with(this)
                .load(mViewModel.mPigeonEntity.getCoverPhotoUrl())
                .placeholder(R.drawable.ic_img_default)
                .into(mImgHead);//鸽子照片

        mViewModel.mPhotoType = new ArrayList<>();
        mViewModel.mPhotoType.add(new SelectTypeEntity.Builder()
                .TypeName(getString(R.string.text_all_photo))
                .TypeID("")
                .build());

        mTvAllPhoto.setOnClickListener(v -> {
            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mPhotoType)
                    , typePosition, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            typePosition = index;
                            mTvAllPhoto.setText(item);
                            mViewModel.typeid = mViewModel.mPhotoType.get(index).getTypeID();

                            initData(false);
                        }
                    });
        });

        mOrderT.setOnStatusListener(isP -> {
            if (isP) {
                mViewModel.sort = 1;
            } else {
                mViewModel.sort = 2;
            }
            initData(false);
        });

        mCoordinator.setTitle(mViewModel.mPigeonEntity.getFootRingNum());
        //通过CollapsingToolbarLayout修改字体颜色
        mCoordinator.setExpandedTitleColor(Color.TRANSPARENT);//设置还没收缩时状态下字体颜色
        mCoordinator.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mAppBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mCoordinator.setTitle(null);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mCoordinator.setTitle(mViewModel.mPigeonEntity.getFootRingNum());
                } else {
                    //中间状态
                    mCoordinator.setTitle(mViewModel.mPigeonEntity.getFootRingNum());
                }
            }
        });

        imgAdd.setOnClickListener(v -> {
            String[] chooseWays = getResources().getStringArray(R.array.array_choose_photo);
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                String way = chooseWays[p];
                if (Utils.getString(R.string.text_open_gallery).equals(way)) {
//                        PictureSelectUtil.showChooseImage(getBaseActivity(), PictureMimeType.ofImage(), SelectImageAdapter.MAX_NUMBER - mBreedPigeonEntryViewModel.images.size());
                    PictureSelectUtil.showChooseImage(getBaseActivity(), PictureMimeType.ofImage(), 1);
                } else if (Utils.getString(R.string.text_open_camera).equals(way)) {
                    PictureSelectUtil.openCamera(getBaseActivity(), false);
                }
            });
        });
    }

    public void initRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseActivity(), 3));

        mRecyclerView.addItemDecoration(new ImageItemDecoration(ScreenTool.dip2px(10), 3));

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            //跳转详情
            try {
                PigeonPhotoDetailsFragment.start(getBaseActivity(), mViewModel.mPigeonEntity, mAdapter.getData(), position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        setProgressVisible(true);

        mTypeViewModel.getSelectType_ImgType();

        mViewModel.getTXGP_PigeonPhoto_SelectData();

        initObserve();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mTypeViewModel.mSelectType_ImgType.observe(this, selectTypeEntities -> {
            mViewModel.mPhotoType.addAll(selectTypeEntities);
            setProgressVisible(false);
        });

        mViewModel.mPigeonPhotoData.observe(this, datas -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);

            mTvPhotoCount.setText(datas.size() + "张");
            setProgressVisible(false);
        });

        mViewModel.listEmptyMessage.observe(this, s -> {
            mTvPhotoCount.setText("0张");
            mAdapter.setEmptyText(s);
        });

        //统计数据
        mViewModel.mPigeonPhotoCount.observe(this, datas -> {

        });
    }

    private void initHead() {

    }


    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PIGEON_PHOTO_REFRESH)) {
            initData(true);
        }
    }

    private void initData(boolean isCount) {
        setProgressVisible(true);
        mAdapter.cleanList();
        mViewModel.getTXGP_PigeonPhoto_SelectData();
//        if (isCount) {
//            mViewModel.getTXGP_PigeonPhoto_CountPhotoData();
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PictureMimeType.ofImage()) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                BaseImgUploadFragment.start(getBaseActivity(),
                        SnapshotImgUploadFragment.class,
                        new ImgTypeEntity.Builder()
                                .imgPath(selectList.get(0).getCompressPath())
                                .pigeonId(mViewModel.mPigeonEntity.getPigeonID())
                                .foootId(mViewModel.mPigeonEntity.getFootRingID())
                                .build(),
                        ImgUploadFragment.CODE_SELECT_COUNTY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
}
